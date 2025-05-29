package org.example;


/**
 * Отдельный класс для сортировки.
 */
public class HeapSort {


    /**
     * Метод сортирует массив.
     *
     * @param arr - массив
     */
    public int[] heapsort(int[] arr) {
        int[] new_arr = arr.clone();
        int length = arr.length;
        int curr_len = length;

        heapify_all(new_arr, length);

        for (int i = 0; i < length - 1; i++) {
            swap(new_arr, 0, curr_len - 1);
            curr_len--;
            heapify_down(new_arr, curr_len, 0);
        }

        return new_arr;
    }


    /**
     * Выполняет просеивание по одной ветке вниз.
     *
     * @param arr    - массив
     * @param length - длина массива
     * @param source - номер родителя для просеивания
     */
    private void heapify_down(int[] arr, int length, int source) {
        int id;
        id = heapify(arr, length, source);
        if (id != 0) {
            heapify_down(arr, length, source * 2 + id);
        }
    }

    /**
     * Метод выполняет просеивание.
     *
     * @param arr    - массив
     * @param length - его длина
     */
    private void heapify_all(int[] arr, int length) {
        int id;
        for (int i = length / 2 - 1; i >= 0; i--) {
            id = heapify(arr, length, i);

            if (id >= 0) {
                heapify_down(arr, length, i * 2 + id);
            }
        }
    }

    /**
     * Метод выполняет просеивание на родителе и двух детях.
     *
     * @param arr    - массив
     * @param length - длина массива
     * @param source - номер элемента для просеивания
     */
    private int heapify(int[] arr, int length, int source) {
        int left = source * 2 + 1;
        int right = source * 2 + 2;

        if (left >= length) {
            return 0;
        } else if (right >= length) {
            if (arr[source] < arr[left]) {
                swap(arr, source, left);
                return 1;
            }
        } else {
            if (arr[left] >= arr[right]) {
                if (arr[source] < arr[left]) {
                    swap(arr, source, left);
                    return 1;
                }
            } else {
                if (arr[source] < arr[right]) {
                    swap(arr, source, right);
                    return 2;
                }
            }
        }
        return 0;
    }

    /**
     * Метод меняет местами элементы в массиве.
     *
     * @param arr   - массив
     * @param left  - номер первого элемента в массиве
     * @param right - номер второго элемента в массиве
     */
    private void swap(int[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

}