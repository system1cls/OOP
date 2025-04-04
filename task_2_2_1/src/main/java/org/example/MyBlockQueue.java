package org.example;

import java.util.ArrayList;

public class MyBlockQueue {
    private final Object passToGet;
    private final Object passToAdd;
    private final ArrayList<Order> list;
    private final Counter counter;
    private final int max;

    MyBlockQueue(Object passToGet, Object passToAdd, Counter counter, int max) {
        list = new ArrayList<>();
        this.passToAdd = passToAdd;
        this.counter = counter;
        this.passToGet = passToGet;
        this.max = max;
    }

    public Order get() {
        boolean isExit = false;
        Order order = null;

        synchronized (passToGet) {
            if (list.isEmpty()) {
                counter.wakeMain();
                try {
                    passToGet.wait();
                    counter.inc();
                } catch (InterruptedException ex) {
                    isExit = true;
                }
            }

            if (!isExit) {
                synchronized (list) {
                    order = list.remove(0);
                }

                synchronized (passToAdd) {
                    passToAdd.notify();
                }
            }
        }

        return order;
    }

    public boolean add(Order order) {
        boolean isExit = false;

        synchronized (passToAdd) {
            if (max != -1 && list.size() >= max) {
                counter.wakeMain();
                try{
                    passToAdd.wait();
                    counter.inc();
                } catch (InterruptedException ex) {
                    isExit = true;
                }
            }

            if (!isExit) {
                synchronized (list) {
                    list.add(order);
                }

                synchronized (passToGet) {
                    passToGet.notify();
                }
            }
        }

        return isExit;
    }

}
