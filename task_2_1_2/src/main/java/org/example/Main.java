package org.example;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        int []nums = new int[] {2, 3, 5, 7, 11, 13, 17, 23, 31};

        Server server = new Server(5005, nums, 2, new Logger());
        Thread serverT = new Thread(server);
        serverT.start();

        Worker worker = new Worker("localhost", 5005, new Logger());
        Thread workerT = new Thread(worker);
        workerT.start();

        try {
            System.out.println(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try {
            serverT.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(server.answer);
    }
}