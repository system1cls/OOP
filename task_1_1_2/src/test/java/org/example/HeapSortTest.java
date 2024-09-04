package org.example;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void heapsort() {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort srt = new HeapSort();
        srt.heapsort(arr);
        assertEquals(1, arr[0]);
        assertEquals(5, arr[4]);

        int[] arr1 = {};
        srt.heapsort(arr1);
        assertEquals(arr1.length, 0);


        Random random = new Random();
        int length = 1000;
        int [] arr2 = new int[length];
        for (int i = 0; i < length; i++) {
            arr2[i] = random.nextInt(100000) - 50000;
        }
        srt.heapsort(arr2);
        for (int i = 1; i < length; i++) {
            assertTrue(arr2[i] >= arr2[i - 1]);
        }
    }
}