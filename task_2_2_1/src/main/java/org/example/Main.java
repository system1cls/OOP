package org.example;


/**
 * Main method.
 */
public class Main {

    /**
     * Main method.
     *
     * @param args args
     */
    public static void main(String[] args) {

        Cafe cafe = new Cafe();
        try {
            cafe.cafeStart("src\\main\\resources\\Test2.txt");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}