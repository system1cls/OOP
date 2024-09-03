package org.example;

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

        
    }
}