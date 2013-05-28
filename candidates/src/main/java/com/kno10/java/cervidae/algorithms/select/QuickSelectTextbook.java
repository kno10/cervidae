package com.kno10.java.cervidae.algorithms.select;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * QuickSelect computes ("selects") the element at a given rank and can be used
 * to compute Medians and arbitrary quantiles by computing the appropriate rank.
 * 
 * This implementation always uses the last element as the pivot element.
 * 
 * This algorithm is essentially an incomplete QuickSort that only descends into
 * that part of the data that we are interested in, and also attributed to
 * Charles Antony Richard Hoare.
 * 
 * Note: on presorted data, this is known to be slow!
 *
 * @author Erich Schubert
 */
public class QuickSelectTextbook extends AbstractArraySelectionAlgorithm {
  /**
   * Static instance of algorithm.
   */
  public static final QuickSelectTextbook STATIC = new QuickSelectTextbook();

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public QuickSelectTextbook() {
    super();
  }

  @Override
  public <T> void select(ArraySortAdapter<? super T> adapter, T data, int start, int end, int rank) {
    while(true) {
      final int last = end - 1;
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
