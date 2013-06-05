package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Insertion sort. Only recommended for small arrays.
 * 
 * Note: this class is mostly included for completeness, but it will be used by
 * QuickSort implementations for small parts.
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
    // TODO: use an adapter that can cache an element temporarily?
    for (int i = start + 1; i < end; i++) {
      for (int j = i; j > start && adapter.greaterThan(data, j - 1, j); j--) {
        adapter.swap(data, j, j - 1);
      }
    }
  }

  /**
   * Sort 5 elements (given positions {@code m1 < m2 < m3 < m4 < m5}).
   * 
   * @param adapter Adapter
   * @param data Data store
   * @param m1 First position
   * @param m2 Second position
   * @param m3 Third position
   * @param m4 Fourth position
   * @param m5 Fifth position
   */
  public static <T> void sort5(ArraySortAdapter<? super T> adapter, T data, int m1, int m2, int m3, int m4, int m5) {
    // Insert second
    if (adapter.greaterThan(data, m1, m2)) {
      adapter.swap(data, m1, m2);
    }
    // Sort third and fourth.
    if (adapter.greaterThan(data, m3, m4)) {
      adapter.swap(data, m3, m4);
    }
    // Insert fourth and third
    if (adapter.greaterThan(data, m2, m4)) {
      adapter.swap(data, m2, m4);
    }
    if (adapter.greaterThan(data, m1, m3)) {
      adapter.swap(data, m1, m3);
    }
    if (adapter.greaterThan(data, m2, m3)) {
      adapter.swap(data, m2, m3);
    }
    // Insert fifth
    if (adapter.greaterThan(data, m4, m5)) {
      adapter.swap(data, m4, m5);
      if (adapter.greaterThan(data, m3, m4)) {
        adapter.swap(data, m3, m4);
        if (adapter.greaterThan(data, m2, m4)) {
          adapter.swap(data, m2, m3);
          if (adapter.greaterThan(data, m1, m2)) {
            adapter.swap(data, m1, m2);
          }
        }
      }
    }
  }
}
