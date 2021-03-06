package com.kno10.java.cervidae.algorithms.select;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;
import com.kno10.java.cervidae.algorithms.sort.InsertionSort;
import com.kno10.java.cervidae.algorithms.sort.SortingNetworks;

/**
 * QuickSelect computes ("selects") the element at a given rank and can be used
 * to compute Medians and arbitrary quantiles by computing the appropriate rank.
 * 
 * This implementation uses a best-of-5 heuristic to choose the pivot element.
 * 
 * This algorithm is essentially an incomplete QuickSort that only descends into
 * that part of the data that we are interested in, and also attributed to
 * Charles Antony Richard Hoare.
 * 
 * @author Erich Schubert
 */
public class QuickSelectBo5 extends AbstractArraySelectionAlgorithm {
  /**
   * For array parts smaller than this, switch to insertion sort.
   * 
   * TODO: benchmark for an appropriate value!
   */
  private static final int INSERTION_THRESHOLD = 35;

  /**
   * Insertion threshold.
   */
  private final int threshold;

  /**
   * Static instance of algorithm.
   */
  public static final QuickSelectBo5 STATIC = new QuickSelectBo5(INSERTION_THRESHOLD);

  /**
   * Constructor with parameterizable threshold.
   * 
   * @param threshold Threshold to switch to insertion sort.
   */
  public QuickSelectBo5(int threshold) {
    super();
    this.threshold = threshold;
  }

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public QuickSelectBo5() {
    this(INSERTION_THRESHOLD);
  }

  /**
   * Sort the array using the given comparator.
   * 
   * @param adapter Array data structure adapter
   * @param data Data structure to sort
   * @param start First index
   * @param end Last index (exclusive)
   * @param rank Rank to select
   */
  @Override
  public <T> void select(ArraySortAdapter<? super T> adapter, T data, int start, int end, int rank) {
    while(true) {
      // Optimization for small arrays,
      // This also ensures a minimum size below
      if(start + threshold > end) {
        InsertionSort.STATIC.sort(adapter, data, start, end);
        return;
      }

      // Best of 5 pivot picking:
      // Choose pivots by looking at five candidates.
      final int len = end - start;
      final int seventh = (len >> 3) + (len >> 6) + 1;
      final int m3 = (start + end) >> 1; // middle
      final int m2 = m3 - seventh;
      final int m1 = m2 - seventh;
      final int m4 = m3 + seventh;
      final int m5 = m4 + seventh;

      SortingNetworks.sort5(adapter, data, m1, m2, m3, m4, m5);

      final int best = bestPivot(rank, m1, m2, m3, m4, m5);
      final int last = end - 1;
      // Move middle element (pivot) out of the way.
      adapter.swap(data, best, last);

      // Begin partitioning
      int i = start, j = end - 2;
      // This is classic QuickSort stuff
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

      // In contrast to QuickSort, we only need to recurse into the half
      // we are interested in. Instead of recursion we now use iteration.
      if(rank < i) {
        end = i;
      }
      else if(rank > i) {
        start = i + 1;
      }
      else {
        break;
      }
    } // Loop until rank==i
  }

  /**
   * Choose the best pivot for the given rank.
   * 
   * @param rank Rank
   * @param m1 Pivot candidate
   * @param m2 Pivot candidate
   * @param m3 Pivot candidate
   * @param m4 Pivot candidate
   * @param m5 Pivot candidate
   * @return Best pivot candidate
   */
  private static final int bestPivot(int rank, int m1, int m2, int m3, int m4, int m5) {
    if(rank < m1) {
      return m1;
    }
    if(rank > m5) {
      return m5;
    }
    if(rank < m2) {
      return m2;
    }
    if(rank > m4) {
      return m4;
    }
    return m3;
  }
}
