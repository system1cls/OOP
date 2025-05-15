package org.example;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tests {
    ArrayList<Test> tests = new ArrayList<>();

    Tests() {
        tests.add(new Test(new int[] {6, 8, 7, 13, 5, 9, 4}, true));
        tests.add(new Test(new int[] {20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053}, false));
        tests.add(new Test(getArr(), false));
    }

    private int[] getArr() {
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

    public class Test {
        int[] arr;
        boolean ans;


        Test(int []arr, boolean ans) {
            this.arr = arr;
            this.ans = ans;
        }
    }
}
