package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.controller.arraylike.ArrayController;

/**
 * Insertion sort. Only recommended for small arrays.
 * 
 * @author Erich Schubert
 */
public class InsertionSort {
  /**
   * Sort a small array part using repetitive insertion sort.
   * 
   * @param control Array controller
   * @param data Data to sort
   */
  public static <T> void sort(ArrayController<? super T, ?> control, T data) {
    sort(control, data, 0, control.length(data));
  }

  /**
   * Sort a small array part using repetitive insertion sort.
   * 
   * @param control Array controller
   * @param data Data to sort
   * @param start Interval start
   * @param end Interval end
   */
  public static <T> void sort(ArrayController<? super T, ?> control, T data, int start, int end) {
    for(int i = start + 1; i < end; i++) {
      for(int j = i; j > start && control.greaterThan(data, j - 1, j); j--) {
        control.swap(data, j, j - 1);
      }
    }
  }
}
