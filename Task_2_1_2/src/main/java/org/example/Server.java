package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class Server implements Runnable{
    boolean answer = false;
    private int maxToTranslate = 100;
    private final ILogger logger;
    private final int port;
    final ArrayList<Integer> list;
    final ArrayList<Pair<Thread, Listener>> threads;

    Server(int port, int []nums, ILogger logger) {
        this(port, nums, 128, logger);
    }

    Server(int port, int []nums, int maxToTranslate, ILogger logger) {
        this.logger = logger;
        this.port = port;
        this.list = new ArrayList<>();
        this.threads = new ArrayList<>();
        this.maxToTranslate = Math.min(maxToTranslate, 255);
        if (maxToTranslate < 0) this.maxToTranslate = 128;

        for (int num : nums) {
            list.add(num);
        }
    }


    @Override
    public void run() {
        try (ServerSocket socketServer = new ServerSocket(port)) {
            int counter = 0;
            while(true) {
                Socket socket = socketServer.accept();
                Listener listener = new Listener(this, socket, socketServer, maxToTranslate, counter++);
                Thread thread = new Thread(listener);
                thread.start();

                synchronized (this) {
                    threads.add(new Pair<>(thread, listener));
                }
            }

        } catch (IOException e) {
            logger.print("Server socket closed");
        }
    }

    private class Listener implements Runnable {
        private final Server server;
        private final Socket socket;
        private final ServerSocket serverSocket;
        private final InputStream in;
        private final OutputStream out;
        private final int maxToTranslate;
        private int id;
        public boolean ans = false;


        Listener(Server server, Socket socket, ServerSocket serverSocket, int maxToTranslate, int id) {
            this.socket = socket;
            this.id = id;
            this.maxToTranslate = maxToTranslate;
            this.serverSocket = serverSocket;
            this.server = server;
            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                logger.print(id + " streams getting error");
                deleteMySelfFromList();
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {

            boolean shouldCont = true;
            while(shouldCont) {
                int []arr = null;
                synchronized (server) {
                    if (server.answer || server.list.isEmpty()) {
                        break;
                    }
                }

                synchronized (server) {
                    arr = new int[Math.min(server.list.size(), maxToTranslate)];
                    for (int i = 0; i < arr.length; i++) {
                        arr[i] = server.list.remove(0);

                    }
                }

                if (arr != null) {
                    try {
                        out.write(arr.length);
                        for (int i = 0; i < arr.length; i++) out.write(ByteBuffer.allocate(4).putInt(arr[i]).array());
                        byte []buffer = new byte[2];
                        int cnt = in.read();

                        if (cnt == -1) {
                            synchronized (server) {
                                for (int j : arr) {
                                    server.list.add(j);
                                }
                            }
                            deleteMySelfFromList();
                            logger.print(id + " connection error");
                            return;
                        }

                        if (cnt == 2) {
                            ans = true;
                            shouldCont = false;
                        }
                    } catch (IOException e) {

                        synchronized (server) {
                            for (int j : arr) {
                                server.list.add(j);
                            }
                        }
                        deleteMySelfFromList();
                        logger.print(id + " connection error");
                        throw new RuntimeException(e);
                    }
                }


            }

            try {
                in.close();
                out.close();
            } catch (IOException e) {
                logger.print("streams closing exception: " + e.getMessage());
            }

            deleteMySelfFromList();

            synchronized (server) {
                server.answer |= ans;
                if (server.answer || (server.list.isEmpty() && server.threads.isEmpty())) {

                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        logger.print("serverSocket already closed");
                    }
                }
            }
        }


        void deleteMySelfFromList() {
            try {
                socket.close();
            } catch (IOException e) {
                logger.print("Already closed");
            }

            synchronized (server) {
                for (int i = 0; i < server.threads.size(); i++) {
                    if (server.threads.get(i).value2.id == id) {
                        server.threads.remove(i);
                        break;
                    }
                }
            }
        }
    }
}