package org.example;

public class MyLogger implements Logger{

    @Override
    public synchronized void print(String str) {
        System.out.println(str);
    }
}
