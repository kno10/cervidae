package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

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
  public static <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void sort(A control, T data) {
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
  public static <T> void sort(ArraySortAdapter<? super T> control, T data, int start, int end) {
    for(int i = start + 1; i < end; i++) {
      for(int j = i; j > start && control.greaterThan(data, j - 1, j); j--) {
        control.swap(data, j, j - 1);
      }
    }
  }
}
