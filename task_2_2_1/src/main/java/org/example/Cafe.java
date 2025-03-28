package org.example;


import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class of Cafe.
 */
public class Cafe implements Runnable {
    private int cntCookers;
    private int cntDel;
    private ArrayList<WaitThread> waiters = new ArrayList<>();
    private ArrayList<Order> futureOrders = new ArrayList<>();
    private ArrayList<Order> completedOrders;
    private MyBlockQueue ordersToCompl;
    private MyBlockQueue ordersToDel;
    private Object []cookersFunc;
    private Object []deliversFunc;
    private Thread []cookers;
    private Thread []delivers;
    private boolean []cookersWait;
    private boolean []deliversWait;
    private Timer timer;
    private int time;
    private Counter counter;
    private Logger logger;
    private boolean isSet = false;
    private int timeOfMin;
    private int cntOrders;
    /**
     * Method for getting random orders.
     *
     * @param cntCookers count of cookers
     * @param cntDel count of delivers
     * @param maxCap max capacity of storage
     * @param cntOrders count of orders
     */
    public void cafeStart(int cntCookers, int cntDel, int maxCap, int cntOrders,
                          Logger logger, int timeOfMin, ArrayList<Order> completedOrders) {
        this.logger = logger;
        this.completedOrders = completedOrders;
        this.cntOrders = cntOrders;
        this.timeOfMin = timeOfMin;
        init(cntCookers, cntDel, maxCap);
        setRandomOrders(cntOrders);

        isSet = true;
    }


    /**
     * Method to start with exact configuration.
     *
     * @param fileName name of the file with configuration
     */
    public void cafeStart(String fileName, Logger logger, int timeOfMin, ArrayList<Order> completedOrders) {
        this.logger = logger;
        this.completedOrders = completedOrders;
        this.timeOfMin = timeOfMin;
        Parser.JsonClass jsonClass = Parser.getConf(fileName);
        init(jsonClass.cntCookers, jsonClass.cntDel, jsonClass.maxCap);
        if (jsonClass.cntOrders != jsonClass.orders.length) {
            throw new RuntimeException("Wrong cnt of orders");
        }
        for (int i = 0; i < jsonClass.cntOrders; i++) {
            jsonClass.orders[i].update(i);
        }
        this.cntOrders = jsonClass.cntOrders;
        this.futureOrders = new ArrayList<>(Arrays.asList(jsonClass.orders));

        isSet = true;
    }

    @Override
    public void run() {
        if (isSet) _cafeStart();
        else {
            throw new RuntimeException("Cafe is not set");
        }
    }

    private void _cafeStart() {
        for (Order futureOrder : futureOrders) {
            System.out.println(futureOrder.toString());
        }

        start();

        loop();

        killThemAll();
    }

    public int addOrder(Order order, boolean setCurTime) {
        if (setCurTime) {
            order.timeOfOrder = this.time;
        }


        synchronized (futureOrders){
            order.update(cntOrders++);
            futureOrders.add(order);
        }

        return cntOrders - 1;
    }


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
        this.counter = new Counter();
        this.waiters = new ArrayList<>();
        this.futureOrders = new ArrayList<>();
        this.ordersToCompl = new MyBlockQueue(new Object(), new Object(), counter, -1);
        this.ordersToDel = new MyBlockQueue(new Object(), new Object(), counter, maxCap);
        this.time = 8*60;

        this.timer = new Timer();

        this.cookers = new Thread[cntCookers];
        this.cookersFunc = new Cooker[cntCookers];
        this.cookersWait = new boolean[cntCookers];
        for (int i = 0; i < cntCookers; i++) {
            cookersWait[i] = true;
            cookersFunc[i] = new Cooker(ordersToDel, waiters, logger, ordersToCompl, i, timer, counter);
            this.cookers[i] = new Thread((Cooker)cookersFunc[i]);
        }

        this.delivers = new Thread[cntDel];
        this.deliversWait = new boolean[cntDel];
        this.deliversFunc = new Deliver[cntDel];
        for (int i = 0; i < cntDel; i++) {
            this.deliversWait[i] = true;
            this.deliversFunc[i] = new Deliver(waiters, ordersToDel, i, timer, counter, logger, completedOrders);
            this.delivers[i] = new Thread((Deliver)deliversFunc[i]);
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


    private void timePar(int difTime) {
        if (timeOfMin - difTime > 0) {
            try {
                Thread.sleep(timeOfMin - difTime);
            } catch (InterruptedException ex) {
                System.out.println("Cafe interrupted");
            }
        }
    }

    /**
     * Main loop.
     */
    private void loop() {
        while(time < 18 * 60) {

            long time1 = System.nanoTime();

            addNewOrders();

            wakeWaiters();

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println("Cafe interrupted");
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

            long time2 = System.nanoTime();

            timePar((int)(time2 - time1));

            synchronized (timer) {
                timer.incTime();
            }

            time++;
        }
    }

    /**
     * Add orders to list of gotten orders.
     */
    private void addNewOrders() {
        synchronized (futureOrders) {
            int len = futureOrders.size();
            for (int i = 0; i < len; i++) {
                Order order = futureOrders.get(i);
                if (order.timeOfOrder <= time) {
                    futureOrders.remove(i);
                    i--;
                    len--;

                    ordersToCompl.add(order);
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
     * End the day with interrupting threads.
     */
    private void killThemAll() {

        while (!waiters.isEmpty() || counter.get() > 0) {

            wakeWaiters();

            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println("Cafe interrupted");
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



}
