package org.example;

import java.util.Arrays;

/**
 * управляющий класс.
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

  /**
   * управляющий метод.
   *
   * @param args - параметры командной строки
   */
  public static void main(String[] args) {

    int[] arr = new int[]{5, 4, 3, 2, 1};
    HeapSort srt = new HeapSort();
    srt.heapsort(arr);
    System.out.println(Arrays.toString(arr));
  }
}


