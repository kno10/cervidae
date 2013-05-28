package com.kno10.java.cervidae.algorithms.select;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;
import com.kno10.java.cervidae.algorithms.sort.InsertionSort;

/**
 * QuickSelect computes ("selects") the element at a given rank and can be used
 * to compute Medians and arbitrary quantiles by computing the appropriate rank.
 * 
 * This implementation uses a best-of-3 heuristic to choose the pivot element.
 * 
 * This algorithm is essentially an incomplete QuickSort that only descends into
 * that part of the data that we are interested in, and also attributed to
 * Charles Antony Richard Hoare.
 * 
 * @author Erich Schubert
 */
public class QuickSelectBo3 extends AbstractArraySelectionAlgorithm {
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
  public static final QuickSelectBo3 STATIC = new QuickSelectBo3(INSERTION_THRESHOLD);

  /**
   * Constructor with parameterizable threshold.
   * 
   * @param threshold Threshold to switch to insertion sort.
   */
  public QuickSelectBo3(int threshold) {
    super();
    this.threshold = threshold;
  }

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public QuickSelectBo3() {
    this(INSERTION_THRESHOLD);
  }

  @Override
  public <T> void select(ArraySortAdapter<? super T> adapter, T data, int start, int end, int rank) {
    while(true) {
      // Optimization for small arrays
      // This also ensures a minimum size below
      if(start + threshold > end) {
        InsertionSort.STATIC.sort(adapter, data, start, end);
        return;
      }

      // Best of 3 pivot picking:
      final int last = end - 1;
      final int mid = (start + end) >> 1; // middle

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
}
