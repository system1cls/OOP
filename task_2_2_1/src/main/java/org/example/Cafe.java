package org.example;


import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class of Cafe.
 */
public class Cafe {
    int cntCookers;
    int cntDel;
    int maxCap;
    Object passToWrite;
    Object passToAddStorage;
    Object passToGetFromStorage;
    Object passToGetOrder;
    ArrayList<WaitThread> waiters = new ArrayList<>();
    ArrayList<Order> futureOrders = new ArrayList<>();
    ArrayList<Order> ordersToCompl = new ArrayList<>();
    ArrayList<Order> ordersToDel = new ArrayList<>();
    Cooker cookersFunc[];
    Deliver deliversFunc[];
    Thread cookers[];
    Thread delivers[];
    boolean cookersWait[];
    boolean deliversWait[];
    Timer timer;
    int time;
    CntWorking cntWorking;
    Counter counter;


    /**
     * Method for initialising parameters.
     *
     * @param cntCookers count of cookers
     * @param cntDel count of delivers
     * @param maxCap max capacity of storage
     */
    private void init(int cntCookers, int cntDel, int maxCap) {
        this.cntCookers = cntCookers;
        this.cntDel = cntDel;
        this.maxCap = maxCap;
        this.passToWrite = new Object();
        this.passToAddStorage = new Object();
        this.passToGetFromStorage = new Object();
        this.passToGetOrder = new Object();
        this.waiters = new ArrayList<>();
        this.futureOrders = new ArrayList<>();
        this.ordersToCompl = new ArrayList<>();
        this.ordersToDel = new ArrayList<>();
        this.cntWorking = new CntWorking();
        this.time = 8*60;
        this.counter = new Counter();

        this.timer = new Timer();

        this.cookers = new Thread[cntCookers];
        this.cookersFunc = new Cooker[cntCookers];
        this.cookersWait = new boolean[cntCookers];
        for (int i = 0; i < cntCookers; i++) {
            cookersWait[i] = true;
            cookersFunc[i] = new Cooker(passToAddStorage, ordersToDel, maxCap,
                    passToGetFromStorage, waiters, ordersToCompl, i, timer,
                    passToGetOrder, cntWorking, counter);
            this.cookers[i] = new Thread(cookersFunc[i]);
        }

        this.delivers = new Thread[cntDel];
        this.deliversWait = new boolean[cntDel];
        this.deliversFunc = new Deliver[cntDel];
        for (int i = 0; i < cntDel; i++) {
            this.deliversWait[i] = true;
            this.deliversFunc[i] = new Deliver(passToGetFromStorage, waiters, ordersToDel,
                    i, timer, passToAddStorage, cntWorking, counter);
            this.delivers[i] = new Thread(deliversFunc[i]);
        }
    }

    /**
     * Set random orders.
     *
     * @param cnt count of orders
     */
    private void setRandomOrders(int cnt) {
        for (int i = 0; i < cnt; i++) {
            futureOrders.add(new Order(i));
        }
    }

    /**
     * Start threads.
     */
     private void start() {
        for (int i = 0; i < cntCookers; i++) {
            this.cookers[i].start();
        }

        for (int i = 0; i < cntDel; i++) {
            this.delivers[i].start();
        }
    }

    /**
     * Add orders to list of gotten orders.
     */
    private void addNewOrders() {
        int len = futureOrders.size();
        for (int i = 0; i < len; i++) {
            Order order = futureOrders.get(i);
            if (order.timeOfOrder <= time) {
                futureOrders.remove(i);
                i--;
                len--;

                synchronized (ordersToCompl) {
                    ordersToCompl.add(order);
                }
                synchronized (passToGetOrder) {
                    passToGetOrder.notify();
                }
            }
        }
    }

    /**
     * Wake threads which waited for exact time.
     */
    private void wakeWaiters() {
        for (int i = 0; i < cntCookers; i++) {
            cookersWait[i] = true;
        }
        for (int i = 0; i < cntDel; i++) {
            deliversWait[i] = true;
        }


        synchronized (waiters) {
            int len = waiters.size();
            for (int i = 0; i < len; i++) {
                WaitThread th = waiters.get(i);
                if (th.timeToWake > time) {
                    if (th.isCook) {
                        cookersWait[th.num] = false;
                    } else {
                        deliversWait[th.num] = false;
                    }
                } else {
                    waiters.remove(i);
                    i--;
                    len--;
                }
            }

            for(int i = 0; i < cntCookers; i++) {
                if (cookersWait[i]) {
                    synchronized (cookersFunc[i]) {
                        cookersFunc[i].notify();
                    }
                    
                }
            }
            for (int i = 0; i < cntDel; i++) {
                if (deliversWait[i]) {
                    synchronized (deliversFunc[i]) {
                        deliversFunc[i].notify();
                    }
                }
            }
        }
    }

    /**
     * Main loop.
     */
    private void loop() {
        while(time < 18 * 60) {

            addNewOrders();

            wakeWaiters();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Main interrupted");
                System.exit(1);
            }

            counter.lock.lock();
            try {
                while(counter.get() > 0) {
                    counter.cond.await();
                }
            }   catch (InterruptedException ex) {
                System.out.println("Main interrupted");
                System.exit(1);
            } finally {
                counter.lock.unlock();
            }


            synchronized (timer) {
                timer.incTime();
            }

            time++;
        }
    }

    /**
     * End the day with interrupting threads.
     */
    private void killThemAll() {
        while (!ordersToCompl.isEmpty() || !ordersToDel.isEmpty() || !waiters.isEmpty()
                || counter.get() > 0) {

            wakeWaiters();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Main interrupted");
                System.exit(1);
            }

            counter.lock.lock();
            try {
                while(counter.get() > 0) {
                    counter.cond.await();
                }
            }   catch (InterruptedException ex) {
                System.out.println("Main interrupted");
                System.exit(1);
            } finally {
                counter.lock.unlock();
            }

            synchronized (timer) {
                timer.incTime();
            }
            time++;
        }

        for (int i = 0; i < cntCookers; i++) {
            cookers[i].interrupt();
        }

        for (int i = 0; i < cntDel; i++) {
            delivers[i].interrupt();
        }
    }

    /**
     * Method for getting random orders.
     *
     * @param cntCookers count of cookers
     * @param cntDel count of delivers
     * @param maxCap max capacity of storage
     * @param cntOrders count of orders
     */
    public void cafeStart(int cntCookers, int cntDel, int maxCap, int cntOrders) {
        init(cntCookers, cntDel, maxCap);
        setRandomOrders(cntOrders);

        for (Order futureOrder : futureOrders) {
            System.out.println(futureOrder.toString());
        }

        start();

        loop();

        killThemAll();
    }


    /**
     * Method to start with exact configuration.
     *
     * @param fileName name of the file with configuration
     */
    public void cafeStart(String fileName) {
        Parser.JsonClass jsonClass = Parser.getConf(fileName);
        init(jsonClass.cntCookers, jsonClass.cntDel, jsonClass.maxCap);
        if (jsonClass.cntOrders != jsonClass.orders.length) {
            throw new RuntimeException("Wrong cnt of orders");
        }
        for (int i = 0; i < jsonClass.cntOrders; i++) {
            jsonClass.orders[i].update(i);
        }
        this.futureOrders = new ArrayList<>(Arrays.asList(jsonClass.orders));




        for (Order futureOrder : futureOrders) {
            System.out.println(futureOrder.toString());
        }

        start();

        loop();

        killThemAll();
    }
}
