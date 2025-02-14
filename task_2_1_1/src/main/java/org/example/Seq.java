package org.example;

/**
 * Class for simple solvation.
 */
public class Seq {

    /**
     * Method to find out.
     *
     * @param arr arr to check
     * @return isThereAComposite
     */
    public static boolean findOut(int[] arr) {
        boolean res = false;
        for (int num : arr) {
            res = check(num);
            if (res) return res;
        }
        return false;
    }

    /**
     * Method to check num.
     *
     * @param num num to check
     * @return isAComposite
     */
    private static boolean check(int num) {
        for (int i = 2; i <= num/2; i++) {
            if (num%i == 0) return true;
        }
        if (num < 2) return true;
        else return false;
    }
}
