package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Class for task by Streams.
 */
public class StreamType implements Check {

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
        return false;
    }

    /**
     * Method to find out.
     *
     * @param arr arr to check
     * @return isThereAComposite
     */
    public boolean findOut(int []arr) {
        Integer[] newArr = IntStream.of(arr).boxed().toArray(Integer[]::new);
        List<Integer> list = Arrays.asList(newArr);
        return list.parallelStream().anyMatch(StreamType::check);
    }
}
