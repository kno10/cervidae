package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Insertion sort. Only recommended for small arrays.
 * 
 * Note: this class is mostly included for completeness. It is a known fact that
 * bubble sort is much slower than QuickSort.
 * 
 * @author Erich Schubert
 */
public class InsertionSort extends AbstractArraySortAlgorithm {
  /**
   * Static instance of algorithm.
   */
  public static final InsertionSort STATIC = new InsertionSort();

  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end) {
    for(int i = start + 1; i < end; i++) {
      for(int j = i; j > start && adapter.greaterThan(data, j - 1, j); j--) {
        adapter.swap(data, j, j - 1);
      }
    }
  }
}
