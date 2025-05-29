package org.example;


import java.util.ArrayList;

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
        Logger logger = new MyLogger();
        ArrayList<Order> completedOrders = new ArrayList<>();
        try {
            cafe.cafeStart("src\\main\\resources\\Test2.txt", logger, 0, completedOrders);
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Main Interrupted");
        }
    }
}