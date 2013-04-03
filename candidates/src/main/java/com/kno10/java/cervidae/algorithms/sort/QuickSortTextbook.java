package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * A textbook version of QuickSort to sort an array-like data structure.
 * 
 * The last element in the array is used as pivot.
 * 
 * Note: on presorted data, this is known to be slow!
 * 
 * @author Erich Schubert
 */
public class QuickSortTextbook extends AbstractArraySortAlgorithm {
  /**
   * Static instance of algorithm.
   */
  public static final QuickSortTextbook STATIC = new QuickSortTextbook();

  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, final int start, final int end) {
    final int last = end - 1;

    // We use the last element as pivot:
    int i = start;
    int j = last - 1;

    // This is the classic QuickSort loop:
    while(true) {
      while(i <= j && adapter.greaterThan(data, last, i)) {
        i++;
      }
      while(j >= i && !adapter.greaterThan(data, last, j)) {
        j--;
      }
      if(i >= j) {
        break;
      }
      adapter.swap(data, i, j);
    }

    // Move pivot back into the appropriate place
    adapter.swap(data, i, last);

    // Recursion:
    if(start < i) {
      sort(adapter, data, start, i);
    }
    if(i < last) {
      sort(adapter, data, i + 1, end);
    }
  }
}
