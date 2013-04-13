package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * A textbook version of HeapSort to sort an array-like data structure.
 * 
 * Note: while heap sort is optimal in theory, it is known to be slower on real
 * data, due to effects such as caching, but also the need to actually
 * reorganize the data completely: presorted or nearly presorted data is almost
 * a worst case for heap sort.
 * 
 * @author Erich Schubert
 */
public class HeapSortTextbook extends AbstractArraySortAlgorithm {
  /**
   * Static instance of algorithm.
   */
  public static final HeapSortTextbook STATIC = new HeapSortTextbook();

  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, final int start, final int end) {
    final int len = end - start;
    // Phase 1: heap construction - Bottom-up.
    // Start at parent of last element.
    for(int i = (len - 2) >>> 1; i >= 0; i--) {
      fixDown(adapter, data, start, len, i);
    }
    // Phase 2: extract the top element until heap is empty.
    for(int i = len - 1; i > 0; i--) {
      adapter.swap(data, start, start + i);
      fixDown(adapter, data, start, i, 0);
    }
  }

  /**
   * Fix heap downwards.
   * 
   * @param adapter Array adapter
   * @param data Data structure
   * @param start array offset
   * @param len Interval length
   * @param cur Current position
   */
  private <T> void fixDown(ArraySortAdapter<? super T> adapter, T data, final int start, final int len, final int cur) {
    int left = (cur << 1) + 1, righ = left + 1;
    int best = cur;
    if(left < len && adapter.greaterThan(data, start + left, start + best)) {
      best = left;
    }
    if(righ < len && adapter.greaterThan(data, start + righ, start + best)) {
      best = righ;
    }
    if(best != cur) {
      adapter.swap(data, start + best, start + cur);
      fixDown(adapter, data, start, len, best);
    }
  }
}
