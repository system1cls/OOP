package org.example;

import java.util.ArrayList;

/**
 * Class for delivers.
 */
public class Deliver implements Runnable{
    final Object passToGetStorage;
    final Object passToAddStorage;
    final ArrayList<WaitThread> waiters;
    final ArrayList<Order> ordersToDel;
    int seqNum;
    final Timer timer;
    final CntWorking cntWorking;
    Order curOrder;
    Thread curThread;
    Counter counter;

    /**
     * Common constructor.
     *
     * @param passToGetStorage - object for getting order from storage
     * @param waiters - list of waiting threads
     * @param ordersToDel - list of cooked pizzas
     * @param seqNum - number of deliver
     * @param timer - timer
     * @param passToAddStorage - object for adding order to storage
     * @param cntWorking - object for detecting cnt of working threads
     * @param counter - lock to make main thread wait
     */
    Deliver(Object passToGetStorage, ArrayList<WaitThread> waiters,
            ArrayList<Order> ordersToDel, int seqNum, Timer timer,
            Object passToAddStorage, CntWorking cntWorking, Counter counter) {
        this.passToGetStorage = passToGetStorage;
        this.seqNum = seqNum;
        this.timer = timer;
        this.ordersToDel = ordersToDel;
        this.waiters = waiters;
        this.passToAddStorage = passToAddStorage;
        this.curThread = Thread.currentThread();
        this.cntWorking = cntWorking;
        this.counter = counter;
        this.counter.inc();
    }

    /**
     * Wake main thread with counter.lock;
     */
    private void wakeMain() {
        counter.lock.lock();
        try {
            counter.dec();
            counter.cond.signal();
        } finally {
            counter.lock.unlock();
        }
    }

    /**
     * Method for getting/delivering order.
     *
     * @return should we end thread
     */
    private boolean deliverOrder() {
        StringBuilder builder = new StringBuilder();
        int timeInt;
        String timeStr;
        boolean isNotFull = false;
        boolean exit = false;

        synchronized (passToGetStorage) {
            if (ordersToDel.isEmpty()) {
                wakeMain();
                try {
                    passToGetStorage.wait();
                    counter.inc();
                } catch (InterruptedException ex) {
                    exit = true;
                }
            }

            if (!exit) {
                synchronized (ordersToDel) {
                    if (ordersToDel.get(0).cntPizza > 4) {
                        isNotFull = true;
                    } else {
                        isNotFull = false;
                    }

                    if (isNotFull) {
                        ordersToDel.get(0).cntPizza -= 4;
                        curOrder = ordersToDel.get(0);
                        curOrder = new Order(curOrder.cntPizza, curOrder.timeOfOrder, curOrder.timeToDel,
                                curOrder.number);
                    } else {
                        curOrder = ordersToDel.remove(0);
                    }
                }
            }
        }

        if (isNotFull) {
            synchronized (passToAddStorage) {
                passToAddStorage.notify();
            }
        }

        if (exit) {
            wakeMain();
            return exit;
        }


        synchronized (timer) {
            timeInt = timer.getTimeInt();
            timeStr = timer.getTimeString();
        }

        builder.setLength(0);
        builder.append(timeStr).append("Deliver #").append(seqNum)
                .append(" starts delivering ");
        if (isNotFull) {
            builder.append("part of order #");
        }
        else {
            builder.append("full order #");
        }
        builder.append(curOrder.number).append("\n");

        synchronized (System.out) {
            System.out.println(builder);
        }

        synchronized (cntWorking) {
            cntWorking.delivs++;
        }

        synchronized(waiters) {
            waiters.add(new WaitThread(this, timeInt + curOrder.timeToDel, false, seqNum));
        }

        synchronized (this) {
            wakeMain();
            try {
                this.wait();
                counter.inc();
            } catch (InterruptedException ex) {
                exit = true;
            }
        }

        synchronized (cntWorking) {
            cntWorking.delivs--;
        }

        if (exit) {
            wakeMain();
            return exit;
        }

        synchronized (timer) {
            timeStr = timer.getTimeString();
        }

        builder.setLength(0);
        builder.append(timeStr).append("Deliver #").append(seqNum)
                .append(" ended delivering ");
        if (isNotFull) {
            builder.append("part of order #");
        }
        else {
            builder.append("full order #");
        }
        builder.append(curOrder.number).append("\n");

        synchronized (System.out) {
            System.out.println(builder);
        }


        return false;
    }


    /**
     * Run method with while in it.
     */
    @Override
    public void run() {


        while(true) {

            if (curThread.isInterrupted()) {
                wakeMain();
                return;
            }

            if (deliverOrder()) {
                return;
            }
        }
    }
}
