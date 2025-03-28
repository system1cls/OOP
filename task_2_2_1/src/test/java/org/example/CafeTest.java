package org.example;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CafeTest {
    Logger logger = new MyLogger();

    @Test
    void cafeStartRandom1() {
        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart(1, 1, 1, 1, logger, 0, orders);

        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }

        assertEquals(orders.size(), 1);
    }

    @Test
    void cafeStartRandom2() {
        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart(2, 2, 1, 2, logger, 0, orders);

        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }

        assertEquals(orders.size(), 2);
    }

    @Test
    void cafeStartRandom3() {
        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart(2, 2, 2, 4, logger, 0, orders);

        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }

        assertEquals(orders.size(), 4);
    }

    @Test
    void CafeStartFileWithLateOrder() {
        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart("src"+ File.separator+"test"
                + File.separator + "resources" + File.separator + "TestWithLateOrder.txt", logger, 0, orders);

        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }

        assertEquals(orders.size(), 1);
    }

    @Test
    void CafeStartFile2() {
        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart("src"+ File.separator+"test"
                + File.separator + "resources" + File.separator + "Test2.txt", logger, 0, orders);

        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }

        assertEquals(orders.size(), 2);
    }

    @Test
    void cafeStartFileWithManyPizza() {
        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart("src"+ File.separator+"test"
                + File.separator + "resources" + File.separator + "Test3.txt", logger, 0, orders);

        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }

        assertEquals(orders.size(), 1);
    }

    @Test
    void addingOrder() {

        Cafe cafe = new Cafe();
        ArrayList<Order> orders = new ArrayList<>();
        cafe.cafeStart(1, 1, 1, 0, logger, 50, orders);
        try {
            Thread thcafe = new Thread(cafe);
            thcafe.start();
            cafe.addOrder(new Order(0), true);
            thcafe.join();
        } catch (InterruptedException e) {
            System.out.println("interrupted ex");
        }
        assertEquals(orders.size(), 1);
    }
}