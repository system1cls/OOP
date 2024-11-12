package org.example;

import java.util.Arrays;

/**
 * Main class.
 */
public class Main {
    /**
     * Main method.
     *
     * @param args args
     */
    public static void main(String[] args) {
        System.out.print(Arrays.toString(Finding.find("input2.txt", "aabbaa")));
    }
}
