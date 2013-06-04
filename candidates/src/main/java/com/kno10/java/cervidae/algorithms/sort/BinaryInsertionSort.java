package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Insertion sort with binary search. Still only recommended for small arrays.
 * 
 * Note: this class is mostly included for completeness, but it will be used by
 * QuickSort implementations for small parts.
 * 
 * @author Erich Schubert
 */
public class BinaryInsertionSort extends AbstractArraySortAlgorithm {
  /**
   * Static instance of algorithm.
   */
  public static final BinaryInsertionSort STATIC = new BinaryInsertionSort();

  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end) {
    for (int i = start + 1; i < end; i++) {
      // Find the insertion position first.
      int left = start, right = i;
      while (left < right) {
        int mid = left + ((right - left) >> 1);
        if (adapter.greaterThan(data, mid, i)) {
          right = mid;
        } else {
          left = mid + 1;
        }
      }
      assert(left == right);
      for (int j = i; j > left; j--) {
        adapter.swap(data, j, j - 1);
      }
    }
  }
}
