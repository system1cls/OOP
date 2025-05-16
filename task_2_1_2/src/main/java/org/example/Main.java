package org.example;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {


            try  {
                ServerSocket[] sockets = new ServerSocket[5];
                for (int i = 0; i < 5; i++) {
                    sockets[i] = new ServerSocket(5005);
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
    }

}