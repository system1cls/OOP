package org.example;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.util.Scanner;


class StreamTypeTest {
    int[] getArr() {
        int arr[] = new int[100000];
        try(FileReader f = new FileReader("src\\test\\resources\\test")) {
            Scanner sc = new Scanner(f);
            for (int i = 0; i < 100000; i++) {
                arr[i] = sc.nextInt();
            }
        } catch (Exception e) {
            System.out.println("wrong file path");
        }
        return arr;
    }


    @Test
    void testBig() {
        StreamType streamType = new StreamType();
        int arr[] = getArr();
        long t1 = System.currentTimeMillis();
        assertFalse(streamType.findOut(arr));
        long t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
    }

    @Test
    void test1() {
        StreamType streamType = new StreamType();
        int arr[] = {6, 8, 7, 13, 5, 9, 4};
        assertTrue(streamType.findOut(arr));
    }

    @Test
    void test2() {
        StreamType streamType = new StreamType();
        int arr[] = {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053};
        assertFalse(streamType.findOut(arr));
    }
}