package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.controller.arraylike.ArrayController;

/**
 * A textbook version of QuickSort to sort an array-like data structure.
 * 
 * The last element in the array is used as pivot.
 * 
 * Note: on presorted data, this is known to be slow!
 * 
 * @author Erich Schubert
 */
public class QuickSortTextbook {
  /**
   * Sort the full array using the given comparator.
   * 
   * @param data Data to sort
   * @param comp Comparator
   */
  public static <T> void sort(ArrayController<T> control, T data) {
    sort(control, data, 0, control.length(data));
  }

  /**
   * Sort the array using the given comparator.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive)
   * @param comp Comparator
   */
  public static <T> void sort(ArrayController<T> control, T data, int start, int end) {
    quickSort(control, data, start, end);
  }

  /**
   * Actual recursive QuickSort function.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive!)
   * @param comp Comparator
   */
  private static <T> void quickSort(ArrayController<T> control, T data, final int start, final int end) {
    final int last = end - 1;

    // We use the last element as pivot:
    int i = start;
    int j = last - 1;

    // This is the classic quicksort loop:
    while(true) {
      while(i <= j && control.greaterThan(data, last, i)) {
        i++;
      }
      while(j >= i && !control.greaterThan(data, last, j)) {
        j--;
      }
      if(i >= j) {
        break;
      }
      control.swap(data, i, j);
    }

    // Move pivot back into the appropriate place
    control.swap(data, i, last);

    // Recursion:
    if(start < i) {
      sort(control, data, start, i);
    }
    if(i < last) {
      sort(control, data, i + 1, end);
    }
  }
}
