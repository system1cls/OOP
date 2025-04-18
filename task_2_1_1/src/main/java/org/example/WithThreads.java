package org.example;

/**
 * Class for task by using threads.
 */
public class WithThreads implements Check{
    int cntThr;
    Thread threads[];
    Func funcs[];

    /**
     * Runnable func for determining class of a number.
     */
    class Func implements Runnable {
        private volatile boolean value;
        int num;

        /**
         * Get isSimple.
         *
         * @return bool value
         */
        boolean getValue() {
            return value;
        }

        /**
         * Get number of checking.
         *
         * @return num
         */
        int getNum() {
            return num;
        }

        /**
         * Set number.
         *
         * @param num number to check
         */
        void set(int num) {
            value = false;
            this.num = num;
        }

        /**
         *
         */
        @Override
        public void run() {
            for (int i = 2; i <= num/2; i++) {
                if (num%i == 0) {
                    value = true;
                    return;
                }
            }

            if (num < 2) {
                value = true;
            }
        }
    }

    /**
     * Constructor for class.
     *
     * @param num cnt_of_threads
     */
    WithThreads(int num) {
        cntThr = num;
        threads = new Thread[num];
        funcs = new Func[num];
        for (int i = 0; i < num; i++) {
            funcs[i] = new Func();
            funcs[i].set(0);
            threads[i] = new Thread(funcs[i]);
        }
    }

    /**
     * Main method for task.
     *
     * @param arr arr to seek
     * @return isThereAComposite
     */
    public boolean findOut(int []arr) {
        boolean res = false;
        for (int j = 0; j < arr.length && !res; j++) {
            boolean isWork = false;
            while(!isWork) {
                for (int i = 0; i < cntThr && !isWork; i++) {
                    if (!threads[i].isAlive()) {
                        if (funcs[i].getNum() != 0) {
                            res = funcs[i].getValue();
                            try {
                                threads[i].join();
                            } catch (InterruptedException ex) {
                                System.out.print(ex.getMessage());
                            }

                            if (!res) {
                                funcs[i].set(arr[j]);
                                threads[i] = new Thread(funcs[i]);
                                threads[i].start();
                            }
                        } else {
                            funcs[i].set(arr[j]);
                            threads[i].start();
                        }
                        isWork = true;
                    }
                }
            }
        }
        for (int j = 0; j < cntThr; j++) {
            try {
                threads[j].join();
                res = res  | funcs[j].getValue();
            } catch (InterruptedException ex) {
                System.out.print(ex.getMessage());
            }
        }
        return res;
    }

}
