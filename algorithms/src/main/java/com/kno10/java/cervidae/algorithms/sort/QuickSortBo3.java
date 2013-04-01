package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * A modified QuickSort to sort an array-like data structure.
 * 
 * This is a class QuickSort, with a median-of-3 heuristic (first, central and
 * last element are used as candidates).
 * 
 * @author Erich Schubert
 */
public class QuickSortBo3 {
  /**
   * Threshold for using insertion sort. Value taken from Javas QuickSort,
   * assuming that it will be similar for our data sets.
   */
  private static final int INSERTION_THRESHOLD = 47;

  /**
   * Sort the full array using the given comparator.
   * 
   * @param control Array controller
   * @param data Data structure
   */
  public static <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void sort(A control, T data) {
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
  public static <T> void sort(ArraySortAdapter<? super T> control, T data, int start, int end) {
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
  private static <T> void quickSort(ArraySortAdapter<? super T> control, T data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if(len < INSERTION_THRESHOLD) {
      InsertionSort.sort(control, data, start, end);
      return;
    }

    // Choose pivots by looking at five candidates.
    final int mid = start + (len >> 1);

    // Ensure that the last element is the median:
    if(control.greaterThan(data, start, mid)) {
      if(control.greaterThan(data, start, last)) {
        if(control.greaterThan(data, mid, last)) {
          // start > mid > last:
          control.swap(data, last, mid);
        }
        // else: start > last > mid
      }
      else {
        // last > start > mid
        control.swap(data, last, start);
      }
    }
    else {
      if(control.greaterThan(data, start, last)) {
        // mid > start > last
        control.swap(data, last, start);
      }
      else {
        if(control.greaterThan(data, last, mid)) {
          // last > mid > start
          control.swap(data, last, mid);
        }
        // else: mid > last > start
      }
    }

    // We placed our pivot at the last position.
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
