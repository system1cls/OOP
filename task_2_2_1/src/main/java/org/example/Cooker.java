package org.example;

import java.util.ArrayList;

/**
 * Class for cookers.
 */
public class Cooker implements Runnable{
    private final ArrayList<WaitThread> waiters;
    private final MyBlockQueue ordersToCompl;
    private final MyBlockQueue ordersToDel;
    private final int seqNum;
    private final Timer timer;
    private Order curOrder;
    private final Thread curThread;
    private final Counter counter;
    private final Logger logger;

    /**
     * @param ordersToDel list of cooked orders
     * @param waiters list of waiting threads
     * @param ordersToCompl list of orders that must be cooked
     * @param seqNum number of cooker
     * @param timer timer
     * @param counter - lock to make main thread wait
     */
    Cooker(MyBlockQueue ordersToDel, ArrayList<WaitThread> waiters, Logger logger,
           MyBlockQueue ordersToCompl, int seqNum, Timer timer, Counter counter) {
        this.ordersToCompl = ordersToCompl;
        this.seqNum = seqNum;
        this.waiters = waiters;
        this.timer = timer;
        this.ordersToDel = ordersToDel;
        curThread = Thread.currentThread();
        this.counter = counter;
        this.logger = logger;
        counter.inc();
    }


    /**
     * Run method.
     */
    @Override
    public void run() {
        boolean exit;

        while(true) {

            if (curThread.isInterrupted()) {
                counter.wakeMain();
                return;
            }


            if (cookOrder()) {
                return;
            }

            exit = ordersToDel.add(curOrder);
            curOrder = null;

            if (exit) {
                return;
            }
        }
    }



    /**
     * Method for getting/cooking/(saving in storage) order.
     *
     * @return should end thread
     */
    private boolean cookOrder() {
        int timeInt;
        String timeStr;
        StringBuilder builder = new StringBuilder();
        boolean exit = false;


        curOrder = ordersToCompl.get();

        if (curOrder == null) {
            counter.wakeMain();
            return true;
        }


        synchronized (timer) {
            timeInt = timer.getTimeInt();
            timeStr = timer.getTimeString();
        }

        builder.append(timeStr).append("Cooker #").append(seqNum)
                .append(" starts cooking order #").append(curOrder.number).append("\n");

        logger.print(builder.toString());


        synchronized (waiters) {
            waiters.add(new WaitThread(this, timeInt + curOrder.timeCook, true, seqNum));
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

        builder.setLength(0);

        synchronized (timer) {
            timeStr = timer.getTimeString();
        }

        builder.append(timeStr).append("Cooker #").append(seqNum)
                .append(" ended cooking order #").append(curOrder.number).append("\n");

        logger.print(builder.toString());


        return false;
    }

}
