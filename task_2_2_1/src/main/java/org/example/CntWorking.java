package org.example;

/**
 * Class for debugging with count of working threads.
 */
public class CntWorking {
    volatile int  cooks;
    volatile int delivs;

    /**
     * Simple constructor.
     */
    CntWorking() {
        cooks = 0;
        delivs = 0;
    }

}
