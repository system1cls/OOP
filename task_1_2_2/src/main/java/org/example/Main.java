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
        HashTable<String, Number> hashTable = new HashTable<>();
        hashTable.add("one", 1);
        hashTable.update("one", 2.0);
        System.out.println(hashTable.getVal("one"));
    }
}