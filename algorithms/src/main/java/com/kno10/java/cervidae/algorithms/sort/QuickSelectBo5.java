package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.controller.arraylike.ArrayController;
import com.kno10.java.cervidae.sort.InsertionSort;
import com.kno10.java.cervidae.sort.SortingNetworks;

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
public class QuickSelectBo5 {
  /**
   * For array parts smaller than this, switch to insertion sort.
   * 
   * TODO: benchmark for an appropriate value!
   */
  private static final int INSERTION_THRESHOLD = 17;

  /**
   * QuickSelect is essentially quicksort, except that we only "sort" that half
   * of the array that we are interested in.
   * 
   * Note: <b>the array is partially sorted by this!</b>
   * 
   * @param control Array controller
   * @param data Data to process
   * @param start Interval start
   * @param end Interval end (exclusive)
   * @param rank rank position we are interested in (starting at 0)
   */
  public static <T> void quickSelect(ArrayController<T> control, T data, int start, int end, int rank) {
    while (true) {
      // Optimization for small arrays
      // This also ensures a minimum size below
      if (start + INSERTION_THRESHOLD > end) {
        InsertionSort.insertionSort(control, data, start, end);
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

      SortingNetworks.sort5(control, data, m1, m2, m3, m4, m5);

      int best = bestPivot(rank, m1, m2, m3, m4, m5);
      // Move middle element (pivot) out of the way.
      control.swap(data, best, end - 1);

      // Begin partitioning
      int i = start, j = end - 2;
      // This is classic quicksort stuff
      while (true) {
        while (i <= j && control.greaterThan(data, end - 1, i)) {
          i++;
        }
        while (j >= i && !control.greaterThan(data, end - 1, j)) {
          j--;
        }
        if (i >= j) {
          break;
        }
        control.swap(data, i, j);
      }

      // Move pivot (former middle element) back into the appropriate
      // place
      control.swap(data, i, end - 1);

      // In contrast to quicksort, we only need to recurse into the half
      // we are
      // interested in. Instead of recursion we now use iteration.
      if (rank < i) {
        end = i;
      } else if (rank > i) {
        start = i + 1;
      } else {
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
    if (rank < m1) {
      return m1;
    }
    if (rank > m5) {
      return m5;
    }
    if (rank < m2) {
      return m2;
    }
    if (rank > m4) {
      return m4;
    }
    return m3;
  }

}
