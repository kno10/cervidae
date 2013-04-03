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
public class QuickSortBo5 extends AbstractArraySortAlgorithm {
  /**
   * Threshold for using insertion sort. Value taken from Javas QuickSort,
   * assuming that it will be similar for our data sets.
   */
  private static final int INSERTION_THRESHOLD = 23;

  /**
   * Static instance of algorithm.
   */
  public static final QuickSortBo5 STATIC = new QuickSortBo5(INSERTION_THRESHOLD);

  /**
   * Insertion threshold.
   */
  private final int threshold;

  /**
   * Constructor with parameterizable threshold.
   * 
   * @param threshold Threshold to switch to insertion sort.
   */
  public QuickSortBo5(int threshold) {
    super();
    this.threshold = threshold;
  }

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public QuickSortBo5() {
    this(INSERTION_THRESHOLD);
  }

  @Override
  public <T> void sort(ArraySortAdapter<? super T> adapter, T data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if (len < threshold) {
      InsertionSort.STATIC.sort(adapter, data, start, end);
      return;
    }

    // Best of 5 pivot picking:
    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int m3 = (start + end) >> 1; // middle
    final int m2 = m3 - seventh;
    final int m1 = m2 - seventh;
    final int m4 = m3 + seventh;
    final int m5 = m4 + seventh;

    SortingNetworks.sort5(adapter, data, m1, m2, m3, m4, m5);
    // Move middle element (pivot) out of the way.
    adapter.swap(data, m3, last);

    // Pivot is at last position. Setup interval:
    int i = start;
    int j = last - 1;

    // This is the classic quicksort loop:
    while (true) {
      while (i <= j && adapter.greaterThan(data, last, i)) {
        i++;
      }
      while (j >= i && !adapter.greaterThan(data, last, j)) {
        j--;
      }
      if (i >= j) {
        break;
      }
      adapter.swap(data, i, j);
    }

    // Move pivot back into the appropriate place
    adapter.swap(data, i, last);

    // Recursion:
    if (start < i) {
      sort(adapter, data, start, i);
    }
    if (i < last) {
      sort(adapter, data, i + 1, end);
    }
  }
}
