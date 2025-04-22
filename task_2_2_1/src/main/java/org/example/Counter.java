package org.example;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class to lock main thread.
 */
public class Counter {
    private int cnt;
    public final Lock lock;
    public final Condition cond;

    /**
     * Simple constructor.
     */
    Counter() {
        cnt = 0;
        lock = new ReentrantLock();
        cond = lock.newCondition();
    }

    public void wakeMain() {
        this.lock.lock();
        try {
            this.dec();
            this.cond.signal();
        } finally {
            this.lock.unlock();
        }
    }

    /**
     * Increment cnt with synchronized method.
     *
     * @return new value of cnt;
     */
    public synchronized int inc() {
        cnt++;
        return cnt;
    }

    /**
     * Decrement cnt with synchronized method.
     *
     * @return new value of cnt;
     */
    public synchronized  int dec() {
        cnt--;
        return cnt;
    }

    /**
     * Get value of cnt.
     *
     * @return value of cnt;
     */
    public synchronized int get() {
        return cnt;
    }
}
