package org.example;

import java.lang.reflect.Array;
import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        int[] arr = new int[] {5, 4, 3, 2, 1};
        HeapSort srt = new HeapSort();
        srt.heapsort(arr);
        System.out.println(Arrays.toString(arr));
    }
}

class HeapSort{

    void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;

    }


    void heapify(int[] arr, int length, int source) {
        int left = source * 2 + 1, right = source * 2 + 2;

        if (left >= length) return;
        else if (right >= length) {
            if (arr[source] < arr[left]) swap(arr, source, left);
        }
        else {
            if (arr[left] >= arr[right]) {
                if(arr[source] < arr[left]) swap(arr, source, left);
            }
            else {
                if (arr[source] < arr[right]) swap(arr, source, right);
            }
        }
    }

    void heapify_up(int[] arr, int length, int i){
        int source = (i - 1) /2;
        heapify(arr, length, source);
        if (source != 0) heapify_up(arr, length, source);

    }

    void heapify_all(int[] arr, int length) {
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(arr, length, i);
        }
    }



    public void heapsort(int[] arr){
        int length = arr.length;
        int n = length;
        int source;

        heapify_all(arr, length);

        for (int i = 0; i < length - 1; i++) {
            swap(arr, 0, n - 1);
            n--;
            heapify_up(arr, n, n);
        }


    }

}