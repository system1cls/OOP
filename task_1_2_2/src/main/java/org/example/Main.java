package org.example;

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
        HashTable<String, Number> table = new HashTable<>();
        table.add("one", 1);
        System.out.print(table.getVal("one"));
    }
}