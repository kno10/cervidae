package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * A modified QuickSort to sort an array-like data structure.
 * 
 * This is a class QuickSort, with a median-of-3 heuristic (first, central and
 * last element are used as candidates).
 * 
 * @author Erich Schubert
 */
public class QuickSortBo3 extends AbstractArraySortAlgorithm {
  /**
   * Threshold for using insertion sort.
   */
  private static final int INSERTION_THRESHOLD = 35;

  /**
   * Static instance of algorithm.
   */
  public static final QuickSortBo3 STATIC = new QuickSortBo3(INSERTION_THRESHOLD);

  /**
   * Insertion threshold.
   */
  private final int threshold;

  /**
   * Constructor with parameterizable threshold.
   * 
   * @param threshold Threshold to switch to insertion sort.
   */
  public QuickSortBo3(int threshold) {
    super();
    this.threshold = threshold;
  }

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public QuickSortBo3() {
    this(INSERTION_THRESHOLD);
  }



  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if(len < threshold) {
      InsertionSort.STATIC.sort(adapter, data, start, end);
      return;
    }

    // Choose pivots by looking at five candidates.
    final int mid = start + (len >> 1);

    // Ensure that the last element is the median:
    if(adapter.greaterThan(data, start, mid)) {
      if(adapter.greaterThan(data, start, last)) {
        if(adapter.greaterThan(data, mid, last)) {
          // start > mid > last:
          adapter.swap(data, last, mid);
        }
        // else: start > last > mid
      }
      else {
        // last > start > mid
        adapter.swap(data, last, start);
      }
    }
    else {
      if(adapter.greaterThan(data, start, last)) {
        // mid > start > last
        adapter.swap(data, last, start);
      }
      else {
        if(adapter.greaterThan(data, last, mid)) {
          // last > mid > start
          adapter.swap(data, last, mid);
        }
        // else: mid > last > start
      }
    }

    // We placed our pivot at the last position.
    int i = start;
    int j = last - 1;

    // This is the classic quicksort loop:
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
