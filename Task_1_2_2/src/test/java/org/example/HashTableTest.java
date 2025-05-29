package org.example;

import org.junit.jupiter.api.Test;

import java.nio.charset.CoderMalfunctionError;
import java.util.ConcurrentModificationException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void createTable() {
        HashTable<String, Number> table = new HashTable<>();
        table.add("one", 1);
        table.update("one", 2.0);
        assertEquals(table.getVal("one").doubleValue(), 2);
        table.update("two", 5);
        assertEquals(table.getVal("two"), 5);
    }

    @Test
    void myConstructor() {
        HashTable<String, Number> table = new HashTable<>(50);
        assertEquals(table.size, 50);
    }

    @Test
    void addAndDelete() {
        HashTable<String, Number> table = new HashTable<>(50);
        table.add("one", 1);
        table.add("two", 2);
        table.add("three", 3);
        table.add("four", 4);
        table.add("five", 5);
        table.add("six", 6);
        table.add("seven", 7);
        table.add("eight", 8);
        table.add("nine", 9);
        table.add("ten", 10);
        table.delete("six");
        assertEquals(null, table.getVal("six"));
        table.delete("two");
        assertEquals(null, table.getVal("two"));
        table.delete("four");
        assertEquals(table.checkVal("four"), false);

        assertEquals(table.checkVal("five"), true);
    }

    @Test
    void equality() {
        HashTable<String, Number> table1 = new HashTable<>();
        table1.add("one", 5);

        HashTable<String, Number> table2 = new HashTable<>();
        table2.add("one", 5);
        table2.add("one", 5);
        table2.add("one", 5);
        table2.add("one", 5);

        assertTrue(table1.equals(table2));
        table1.add("two", 2);
        assertFalse(table1.equals(table2));
    }

    @Test
    void printSmall() {
        HashTable<Integer, Integer> table = new HashTable<>();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            table.add(random.nextInt(), random.nextInt());
        }
        table.print();
    }

    @Test
    void printBig() {
        HashTable<Integer, Integer> table = new HashTable<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            table.add(random.nextInt(), random.nextInt());
        }
        table.print();
    }

    @Test
    void iterTestRight() {
        HashTable<String, Number> table = new HashTable<>(50);
        table.add("one", 1);
        table.add("two", 2);
        table.add("three", 3);
        table.add("four", 4);
        table.add("five", 5);
        table.add("six", 6);
        table.add("seven", 7);
        table.add("eight", 8);
        table.add("nine", 9);
        table.add("ten", 10);

        StringBuilder stringBuilder = new StringBuilder();
        for(Pair<String, Number> p : table) {
            stringBuilder.append(p.key + ": " + p.value + "\n");
        }

        System.out.print(stringBuilder);
    }

    @Test
    void iterTestException() {
        HashTable<String, Number> table = new HashTable<>(50);
        table.add("one", 1);
        table.add("two", 2);
        table.add("three", 3);
        table.add("four", 4);
        table.add("five", 5);
        table.add("six", 6);
        table.add("seven", 7);
        table.add("eight", 8);
        table.add("nine", 9);
        table.add("ten", 10);

        StringBuilder stringBuilder = new StringBuilder();
        assertThrows(ConcurrentModificationException.class, () -> {
                    for (Pair<String, Number> p : table) {
                        stringBuilder.append(p.key + ": " + p.value + "\n");
                        table.delete("one");
                    }
                });

        System.out.print(stringBuilder);
    }
}