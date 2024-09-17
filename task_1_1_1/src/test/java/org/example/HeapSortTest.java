package org.example;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    void heapsot_base() {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort srt = new HeapSort();
        arr = srt.heapsort(arr);
        assertEquals(1, arr[0]);
        assertEquals(5, arr[4]);
    }

    @Test
    void heapsort_empty() {
        HeapSort srt = new HeapSort();
        int[] arr1 = {};
        arr1 = srt.heapsort(arr1);
        assertEquals(arr1.length, 0);
    }

    @Test
    void heapsort_random1() {
        HeapSort srt = new HeapSort();
        Random random = new Random();
        int length1 = 10000;
        int [] arr2 = new int[length1];
        for (int i = 0; i < length1; i++) {
            arr2[i] = random.nextInt(100000) - 50000;
        }
        long start = System.currentTimeMillis();
        arr2 = srt.heapsort(arr2);
        long finish = System.currentTimeMillis();
        System.out.println("time1: " + (finish - start));
        for (int i = 1; i < length1; i++) {
            assertTrue(arr2[i] >= arr2[i - 1]);
        }
    }

    @Test
    void heapsort_random2() {
        HeapSort srt = new HeapSort();
        Random random = new Random();
        int length3 = 100000;
        int [] arr3 = new int[length3];
        for (int i = 0; i < length3; i++) {
            arr3[i] = random.nextInt(100000) - 50000;
        }
        long start3 = System.currentTimeMillis();
        arr3 = srt.heapsort(arr3);
        long finish3 = System.currentTimeMillis();
        System.out.println("time2: " + (finish3 - start3));
        for (int i = 1; i < length3; i++) {
            assertTrue(arr3[i] >= arr3[i - 1]);
        }
    }

    @Test
    void heapsort_random3() {
        HeapSort srt = new HeapSort();
        Random random = new Random();
        int length4 = 1000000;
        int [] arr4 = new int[length4];
        for (int i = 0; i < length4; i++) {
            arr4[i] = random.nextInt(100000) - 50000;
        }
        long start4 = System.currentTimeMillis();
        arr4 = srt.heapsort(arr4);
        long finish4 = System.currentTimeMillis();
        System.out.println("time3: " + (finish4 - start4));
        for (int i = 1; i < length4; i++) {
            assertTrue(arr4[i] >= arr4[i - 1]);
        }
    }



}