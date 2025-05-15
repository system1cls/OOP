package org.example.test2.Logger;

public class SimpleLogger implements ILogger{
    @Override
    public void print(String str) {
        System.out.println(str);
    }
}
