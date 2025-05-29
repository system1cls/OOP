package org.example;

public class NewWiththreads implements Check {
    Thread threads[];
    Func funcs[];
    int cntThr;

    class Func implements Runnable {
        private volatile boolean value;
        int[] nums;
        int cnt = 0;

        /**
         * Get isSimple.
         *
         * @return bool value
         */
        boolean getValue() {
            return value;
        }
        Func(int arr[], int start, int end) {
            nums = new int[end - start];
            for (int i = start; i < end; i++) {
                nums[i-start] = arr[i];
            }
            cnt = end - start;
        }

        /**
         *
         */
        @Override
        public void run() {
            for (int num : nums) {
                for (int i = 2; i <= num / 2; i++) {
                    if (num % i == 0) {
                        value = true;
                        return;
                    }

                    if (num < 2) {
                        value = true;
                        return;
                    }
                }
            }
        }
    }

    NewWiththreads(int num) {
        cntThr = num;
        threads = new Thread[num];
        funcs = new NewWiththreads.Func[num];
    }

    public boolean findOut(int []arr) {
        boolean res = false;
        int len = arr.length;
        int step = len / cntThr;
        for (int i = 0; i < cntThr; i++) {
            if (i == cntThr - 1) {
                funcs[i] = new Func(arr, step * i, len);
            }
            else {
                funcs[i] = new Func(arr, step * i, step * (i+1));
            }
        }

        for (int i = 0; i < cntThr; i++) {
            threads[i] = new Thread(funcs[i]);
            threads[i].start();
        }

        for (int i = 0; i < cntThr; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            res |= funcs[i].getValue();
        }
        return res;
    }
}
