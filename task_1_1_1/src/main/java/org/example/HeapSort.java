package org.example;


/**
 * Отдельный класс для сортировки.
 */
public class HeapSort {

    /**
     * Метод меняет местами элементы в массиве.
     *
     * @param arr   - массив
     * @param left  - номер первого элемента в массиве
     * @param right - номер второго элемента в массиве
     */
    void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;

    }


    /**
     * Метод выполняет просеивание на родителе и двух детях.
     *
     * @param arr    - массив
     * @param length - длина массива
     * @param source - номер элемента для просеивания
     */
    void heapify(int[] arr, int length, int source) {
        int left = source * 2 + 1;
        int right = source * 2 + 2;

        if (left >= length) {
            return;
        } else if (right >= length) {
            if (arr[source] < arr[left]) {
                swap(arr, source, left);
            }
        } else {
            if (arr[left] >= arr[right]) {
                if (arr[source] < arr[left]) {
                    swap(arr, source, left);
                }
            } else {
                if (arr[source] < arr[right]) {
                    swap(arr, source, right);
                }
            }
        }
    }

    /**
     * Метод выполняет просеивание.
     *
     * @param arr    - массив
     * @param length - его длина
     */
    void heapify_all(int[] arr, int length) {
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapify(arr, length, i);
        }
    }


    /**
     * Метод сортирует массив.
     *
     * @param arr - массив
     */
    public void heapsort(int[] arr) {
        int length = arr.length;
        int n = length;
        int source;

        heapify_all(arr, length);

        for (int i = 0; i < length - 1; i++) {
            swap(arr, 0, n - 1);
            n--;
            heapify_all(arr, n);
        }


    }

}