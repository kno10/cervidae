package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Trivially optimized implementation of bubble sort: after n passes, at least n
 * elements must be in their correct position.
 * 
 * Note: this class is mostly included for completeness. It is a known fact that
 * bubble sort is much slower than QuickSort, and there is <em>nothing</em> to
 * recommend using bubblesort.
 * 
 * @author Erich Schubert
 */
public class BubbleSort extends AbstractArraySortAlgorithm {
  /**
   * Static class instance.
   */
  public static final BubbleSort STATIC = new BubbleSort();

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
      end--; // This is the "optimization"...
    }
  }
}
