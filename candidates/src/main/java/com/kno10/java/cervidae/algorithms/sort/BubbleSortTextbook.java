package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Textbook implementation of bubble sort.
 * 
 * Note: this class is mostly included for completeness. It is a known fact that
 * bubble sort is much slower than QuickSort.
 * 
 * @author Erich Schubert
 */
public class BubbleSortTextbook extends AbstractArraySortAlgorithm {
  /**
   * Static class instance.
   */
  public static final BubbleSortTextbook STATIC = new BubbleSortTextbook();

  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end) {
    boolean alive = true;
    while (alive) {
      alive = false;
      for (int i = start + 1; i < end; i++) {
        if (adapter.greaterThan(data, i - 1, i)) {
          adapter.swap(data, i - 1, i);
          alive = true;
        }
      }
    }
  }
}
