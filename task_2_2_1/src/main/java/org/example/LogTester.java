package org.example;

public class LogTester implements Logger {
    int it = 0;
    String strs[] = new String[256];


    @Override
    public void print(String str) {
        strs[it++] = str;
    }

    public void printCurLines() {
        for (int i = 0; i < it; i++) {
            System.out.println(i + ": " + strs[i]);
        }
    }
}
