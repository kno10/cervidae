package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * A modified QuickSort to partially sort an array.
 * 
 * The sorting strategy is a dual pivot QuickSort using a best-of-5 pivot
 * heuristic, and falling back to insertion sort for tiny sub-arrays.
 * 
 * The implementation is closely based on:
 * <p>
 * Dual-Pivot Quicksort<br />
 * Vladimir Yaroslavskiy
 * </p>
 * 
 * and differs mostly in that we sort different kinds of arrays, and allow the
 * use of comparators - useful in particular when the array references external
 * objects.
 * 
 * @author Erich Schubert
 */
public class DualPivotQuickSortBo5 {
  /**
   * Threshold for using insertion sort. Value taken from Javas QuickSort,
   * assuming that it will be similar for our data sets.
   */
  private static final int INSERTION_THRESHOLD = 47;

  /**
   * Sort the full array using the given comparator.
   * 
   * @param control Controller
   * @param data Data to sort
   */
  public static <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void sort(A control, T data) {
    sort(control, data, 0, control.length(data));
  }

  /**
   * Sort the array using the given comparator.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive)
   * @param comp Comparator
   */
  public static <T> void sort(ArraySortAdapter<? super T> control, T data, int start, int end) {
    quickSort(control, data, start, end);
  }

  /**
   * Actual recursive sort function.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive!)
   * @param comp Comparator
   */
  private static <T> void quickSort(ArraySortAdapter<? super T> control, T data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if(len < INSERTION_THRESHOLD) {
      InsertionSort.sort(control, data, start, end);
      return;
    }

    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int m3 = start + (len >> 1); // middle
    final int m2 = m3 - seventh;
    final int m1 = m2 - seventh;
    final int m4 = m3 + seventh;
    final int m5 = m4 + seventh;

    // Explicit (and optimal) sorting network for 5 elements
    // See Knuth for details.
    SortingNetworks.sort5(control, data, m1, m2, m3, m4, m5);

    // Choose the 2 and 4th as pivots, as we want to get three parts
    control.swap(data, m2, start);
    control.swap(data, m4, last);

    // A tie is when the two chosen pivots are the same
    final boolean tied = control.equals(data, start, last);

    // Insertion points for pivot areas.
    int left = start + 1;
    int right = last - 1;

    // Note: we merged the ties and no ties cases.
    // This likely is marginally slower, but not at a macro level
    // And you never know with hotspot.
    for(int k = left; k <= right; k++) {
      if(control.equals(data, k, start)) {
        continue;
      }
      else if(control.greaterThan(data, start, k)) {
        // Traditional QuickSort swap:
        if(k != left) {
          control.swap(data, k, left);
        }
        left++;
      }
      else if(tied || control.greaterThan(data, k, last)) {
        // Now look at the right. First skip correct entries there, too
        while(k < right && control.greaterThan(data, right, last)) {
          right--;
        }
        // Now move current element to the right.
        if(k < right) {
          control.swap(data, k, right);
          // Test the element we just moved: left or center?
          if(control.greaterThan(data, start, k)) {
            control.swap(data, left, k);
            left++;
          } // else: center. cannot be on right.
        }
        right--;
      }
    }
    // Put the pivot elements in their appropriate positions.
    control.swap(data, start, left - 1);
    control.swap(data, last, right + 1);
    if(control.greaterThan(data, right, right + 1)) {
      throw new RuntimeException("Sorting error.");
    }
    // Perform recursion:
    if(start < left - 2) {
      quickSort(control, data, start, left - 1);
    }
    // Handle the middle part - if necessary:
    if(!tied && left < right) {
      // TODO: the original publication had a special tie handling here.
      // It shouldn't affect correctness, but probably improves situations
      // with a lot of tied elements.
      quickSort(control, data, left, right + 1);
    }
    if(right + 3 < end) {
      quickSort(control, data, right + 2, end);
    }
  }
}
