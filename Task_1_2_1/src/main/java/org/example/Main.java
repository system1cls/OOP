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
        ListAdj graph1 = new ListAdj();
        graph1.readFromFile("src\\main\\resources\\graph.txt");
        System.out.print(Arrays.toString(graph1.sort()));
    }
}