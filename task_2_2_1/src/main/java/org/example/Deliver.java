package org.example;

import java.util.ArrayList;

/**
 * Class for delivers.
 */
public class Deliver implements Runnable{
    private final ArrayList<WaitThread> waiters;
    private final MyBlockQueue ordersToDel;
    private final int seqNum;
    private final Timer timer;
    private Order curOrder;
    private final Thread curThread;
    private final Counter counter;
    private final Logger logger;
    private final ArrayList<Order> completedOrders;

    /**
     * Common constructor.
     * @param waiters - list of waiting threads
     * @param ordersToDel - list of cooked pizzas
     * @param seqNum - number of deliver
     * @param timer - timer
     * @param counter - lock to make main thread wait
     */
    Deliver(ArrayList<WaitThread> waiters, MyBlockQueue ordersToDel,
            int seqNum, Timer timer, Counter counter, Logger logger, ArrayList<Order> completedOrders) {
        this.seqNum = seqNum;
        this.completedOrders = completedOrders;
        this.timer = timer;
        this.ordersToDel = ordersToDel;
        this.waiters = waiters;
        this.curThread = Thread.currentThread();
        this.counter = counter;
        this.logger = logger;
        this.counter.inc();
    }



    /**
     * Run method with while in it.
     */
    @Override
    public void run() {


        while(true) {

            if (curThread.isInterrupted()) {
                counter.wakeMain();
                return;
            }

            if (deliverOrder()) {
                return;
            }
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
        boolean exit = false;

        curOrder = ordersToDel.get();

        if (curOrder == null) {
            counter.wakeMain();
            return true;
        }


        synchronized (timer) {
            timeInt = timer.getTimeInt();
            timeStr = timer.getTimeString();
        }

        builder.setLength(0);
        builder.append(timeStr).append("Deliver #").append(seqNum)
                .append(" starts delivering ").append("order #");

        builder.append(curOrder.number).append("\n");

        logger.print(builder.toString());


        synchronized(waiters) {
            waiters.add(new WaitThread(this, timeInt + curOrder.timeToDel, false, seqNum));
        }

        synchronized (this) {
            counter.wakeMain();
            try {
                this.wait();
                counter.inc();
            } catch (InterruptedException ex) {
                exit = true;
            }
        }

        if (exit) {
            counter.wakeMain();
            return exit;
        }

        synchronized (timer) {
            timeStr = timer.getTimeString();
        }

        builder.setLength(0);
        builder.append(timeStr).append("Deliver #").append(seqNum)
                .append(" ended delivering ").append("full order #");

        builder.append(curOrder.number).append("\n");

        logger.print(builder.toString());

        synchronized (completedOrders) {
            completedOrders.add(curOrder);
            curOrder = null;
        }

        return false;
    }
}
