package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Worker implements Runnable{

    protected String host;
    protected int port;
    protected final ILogger logger;
    int id;

    Worker(String host, int port, ILogger logger, int id) {
        this.host = host;
        this.port = port;
        this.logger = logger;
        this.id = id;
    }

    @Override
    public void run() {


        try (var socket = new Socket(host, port)){
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();


            boolean shouldCont = true;
            while(true) {
                if (socket.isClosed()) return;
                int cnt = in.read();
                int []nums = new int[cnt];
                logger.print(id + " cnt == " + cnt);
                byte[] buffer = new byte[4];
                int numsIt = 0;
                int cntGetted = 0;
                while(numsIt < cnt) {
                    int getted = in.read(buffer, cntGetted, 4 - cntGetted);
                    cntGetted += getted;
                    if (cntGetted / 4 > 0) {
                        nums[numsIt] = ByteBuffer.wrap(buffer).getInt();
                        logger.print(id + " nums[" + numsIt + "] = " + nums[numsIt++]);
                        cntGetted %= 4;
                    }
                }



                for (int i = 0; i < cnt; i++) {
                    if (check(nums[i])) {
                        out.write(2);
                        socket.close();
                        return;
                    }
                }

                out.write(1);

            }

        } catch (RuntimeException | IOException e) {
            logger.print(id + " socket closed");
            return;
        }


    }

    /*private byte[] reverseByteArray(byte []arr) {
        for (int i = 0; i < arr.length/2; i++) {
            byte temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
        return arr;
    }*/

    protected boolean check(int num) {
        for (int i = 2; i <= num/2; i++) {
            if (num%i == 0) return true;
        }
        if (num < 2) return true;
        else return false;
    }
}