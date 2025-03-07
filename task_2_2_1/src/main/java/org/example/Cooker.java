package org.example;

import java.util.ArrayList;

/**
 * Class for cookers.
 */
public class Cooker implements Runnable{
    final Object passToAddStorage;
    final Object passToGetOrder;
    final Object passToGetStorage;
    final ArrayList<WaitThread> waiters;
    final ArrayList<Order> ordersToCompl;
    final ArrayList<Order> ordersToDel;
    int seqNum;
    final Timer timer;
    final CntWorking cntWorking;
    Order curOrder;
    int maxCap;
    Thread curThread;
    Counter counter;


    /**
     * @param passToAddStorage object for passing to adding orders to storage
     * @param ordersToDel list of cooked orders
     * @param maxCap max capacity of storage
     * @param passToGetStorage object for passing to get order from storage
     * @param waiters list of waiting threads
     * @param ordersToCompl list of orders that must be cooked
     * @param seqNum number of cooker
     * @param timer timer
     * @param passToGetOrder object for getting order from list ordersToCompl
     * @param cntWorking - object for detecting cnt of working threads
     * @param counter - lock to make main thread wait
     */
    Cooker(Object passToAddStorage, ArrayList<Order> ordersToDel,
           int maxCap, Object passToGetStorage, ArrayList<WaitThread> waiters,
           ArrayList<Order> ordersToCompl, int seqNum, Timer timer, Object passToGetOrder,
           CntWorking cntWorking, Counter counter) {
        this.ordersToCompl = ordersToCompl;
        this.passToAddStorage = passToAddStorage;
        this.seqNum = seqNum;
        this.waiters = waiters;
        this.timer = timer;
        this.passToGetOrder = passToGetOrder;
        this.ordersToDel = ordersToDel;
        this.maxCap = maxCap;
        this.passToGetStorage = passToGetStorage;
        curThread = Thread.currentThread();
        this.cntWorking = cntWorking;
        this.counter = counter;
        counter.inc();
    }

    /**
     * Wake main thread with counter.lock.
     */
    void wakeMain() {
        counter.lock.lock();
        try {
            counter.dec();
            counter.cond.signal();
        } finally {
            counter.lock.unlock();
        }
    }

    /**
     * Method for getting/cooking/(saving in storage) order.
     *
     * @return should end thread
     */
    boolean cookOrder() {
        int timeInt;
        String timeStr;
        StringBuilder builder = new StringBuilder();
        boolean exit = false;


        synchronized (passToGetOrder) {
            if (ordersToCompl.isEmpty()) {
                wakeMain();
                try {
                    passToGetOrder.wait();
                    counter.inc();
                } catch (InterruptedException ex) {
                    exit = true;
                }
            }



            if (!exit) {
                synchronized (ordersToCompl) {
                    curOrder = ordersToCompl.removeFirst();
                }
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

        builder.append(timeStr).append("Cooker #").append(seqNum)
                .append(" starts cooking order #").append(curOrder.number).append("\n");

        synchronized (System.out) {
            System.out.println(builder.toString());
        }

        synchronized (cntWorking) {
            cntWorking.cooks++;
        }

        synchronized (waiters) {
            waiters.add(new WaitThread(this, timeInt + curOrder.timeCook, true, seqNum));
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
            cntWorking.cooks--;
        }

        if (exit) {
            wakeMain();
            return exit;
        }

        builder.setLength(0);

        synchronized (timer) {
            timeStr = timer.getTimeString();
        }

        builder.append(timeStr).append("Cooker #").append(seqNum)
                .append(" ended cooking order #").append(curOrder.number).append("\n");

        synchronized (System.out) {
            System.out.println(builder.toString());
        }


        return false;
    }


    /**
     * Run method.
     */
    @Override
    public void run() {
        boolean exit = false;

        while(true) {

            if (curThread.isInterrupted()) {
                wakeMain();
                return;
            }


            if (cookOrder()) {
                return;
            }

            synchronized (passToAddStorage) {
                if (ordersToDel.size() >= maxCap) {
                    wakeMain();
                    try {
                        passToAddStorage.wait();
                        counter.inc();
                    } catch (InterruptedException ex) {
                        exit = true;
                    }
                }


                if (!exit) {
                    synchronized (ordersToDel) {
                        ordersToDel.add(curOrder);
                    }
                    synchronized (passToGetStorage) {
                        passToGetStorage.notify();
                    }

                    curOrder = null;
                }
            }

            if (exit) {
                wakeMain();
                return;
            }
        }
    }
}
