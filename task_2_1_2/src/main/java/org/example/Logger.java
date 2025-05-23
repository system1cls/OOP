package org.example;

public class Logger implements ILogger {

    @Override
    public void print(String string) {
        System.out.println(string);
    }
}
