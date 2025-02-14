package org.example;

import java.util.Arrays;
import java.util.List;

/**
 * Class for task by Streams.
 */
public class StreamType {

    /**
     * Method to check.
     *
     * @param num num to check
     * @return isAComposite
     */
    private static boolean check(Integer num) {
        for (int i = 2; i <= num/2; i++) {
            if (num%i == 0) return true;
        }
        if (num < 2) return true;
        else return false;
    }

    /**
     * Method to find out.
     *
     * @param arr arr to check
     * @return isThereAComposite
     */
    public static boolean findOut(Integer []arr) {
        List<Integer> list = Arrays.asList(arr);
        return list.parallelStream().anyMatch(StreamType::check);
    }
}
