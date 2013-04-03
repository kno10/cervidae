package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Trivially optimized implementation of bubble sort: after n passes, at least n
 * elements must be in their correct position. This is the bidirectional
 * version, while allows the smallest and the largest elements in each round to
 * move to their final position.
 * 
 * Note: this class is mostly included for completeness. It is a known fact that
 * bubble sort is much slower than QuickSort, and there is <em>nothing</em> to
 * recommend using bubblesort.
 * 
 * @author Erich Schubert
 */
public class BidirectionalBubbleSort extends AbstractArraySortAlgorithm {
  /**
   * Static class instance.
   */
  public static final BidirectionalBubbleSort STATIC = new BidirectionalBubbleSort();

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
      end--;
      if (!alive) {
        break;
      }
      // Reverse pass:
      for (int i = end - 2; i >= start; i--) {
        if (adapter.greaterThan(data, i, i + 1)) {
          adapter.swap(data, i, i + 1);
          alive = true;
        }
      }
      start++;
      if (!alive) {
        break;
      }
    }
  }
}
