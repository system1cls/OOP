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

        int[] arr2 = new int [] {23913, 23579, 14017, 26828, 31268, 18270, 13533, 8716, 23015, 4815, 27295, 12231,
                15858, 28733, 8681, 22121, 13007, 27800, 23616, 22639, 23514, 7518, 6773, 12154, 6145, 16983, 18308,
                23458, 25545, 12162, 19132, 15628, 7313, 4943, 1312, 11199, 2331, 27918, 15886, 16819, 19272, 18344,
                17182, 2859, 7971, 3213, 16205, 4421, 2849, 749, 3419, 29392, 26024, 20267, 2417, 32605, 21453, 1679,
                9735, 2201, 9570, 11067, 18437, 24026, 12848, 9312, 11816, 9719, 7297, 1713, 26536, 2909, 7394, 16664,
                7704, 28255, 15142, 424, 13965, 4558, 6318, 26324, 3586, 30154, 12264, 20702, 21755, 21719, 11542,
                26514, 20073, 30516, 17489, 10291, 30158, 20584, 10445, 4022, 20641, 6941};

        int max = 0, min = 100000000;
        for (int i = 0; i < arr2.length; i++) {
            if (arr2[i] >= max) max = arr2[i];
            if (arr2[i] <= min) min = arr2[i];
        }
        srt.heapsort(arr2);
        assertEquals(arr2[0], min);
        assertEquals(arr2[arr2.length - 1], max);
    }
}