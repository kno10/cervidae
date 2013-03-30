package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.controller.arraylike.ArrayController;

/**
 * A modified QuickSort to sort an array-like data structure.
 * 
 * This is a class QuickSort, with a median-of-3 heuristic (first, central and
 * last element are used as candidates).
 * 
 * @author Erich Schubert
 */
public class QuickSortBo5 {
  /**
   * Threshold for using insertion sort. Value taken from Javas QuickSort,
   * assuming that it will be similar for our data sets.
   */
  private static final int INSERTION_THRESHOLD = 47;

  /**
   * Sort the full array using the given comparator.
   * 
   * @param data Data to sort
   * @param comp Comparator
   */
  public static <T> void sort(ArrayController<? super T, ?> control, T data) {
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
  public static <T> void sort(ArrayController<? super T, ?> control, T data, int start, int end) {
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
  private static <T> void quickSort(ArrayController<? super T, ?> control, T data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if(len < INSERTION_THRESHOLD) {
      InsertionSort.sort(control, data, start, end);
      return;
    }

    // Best of 5 pivot picking:
    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int m3 = (start + end) >> 1; // middle
    final int m2 = m3 - seventh;
    final int m1 = m2 - seventh;
    final int m4 = m3 + seventh;
    final int m5 = m4 + seventh;

    SortingNetworks.sort5(control, data, m1, m2, m3, m4, m5);
    // Move middle element (pivot) out of the way.
    control.swap(data, m3, last);

    // Pivot is at last position. Setup interval:
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
