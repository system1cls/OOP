package org.example;

/**
 * Info class for threads that are waiting for exact time.
 */
class WaitThread {
    Object lock;
    boolean isCook;
    int timeToWake;
    int num;

    /**
     * Simple constructor.
     *
     * @param lock mutex (Runnable func).
     * @param timeToWake time to notify thread
     * @param isCook is it a cooker or a deliver
     * @param num number of waiter
     */
    WaitThread(Object lock, int timeToWake, boolean isCook, int num) {
        this.lock = lock;
        this.timeToWake = timeToWake;
        this.isCook = isCook;
        this.num = num;
    }
}
