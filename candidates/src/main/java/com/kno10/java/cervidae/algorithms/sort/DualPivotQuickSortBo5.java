package com.kno10.java.cervidae.algorithms.sort;

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
public class DualPivotQuickSortBo5 extends AbstractArraySortAlgorithm {
  /**
   * Threshold for using insertion sort.
   */
  private static final int INSERTION_THRESHOLD = 35;

  /**
   * Static instance.
   */
  public static final DualPivotQuickSortBo5 STATIC = new DualPivotQuickSortBo5(INSERTION_THRESHOLD);

  /**
   * Insertion threshold.
   */
  private final int threshold;

  /**
   * Constructor with parameterizable threshold.
   * 
   * @param threshold Threshold to switch to insertion sort.
   */
  public DualPivotQuickSortBo5(int threshold) {
    super();
    this.threshold = threshold;
  }

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public DualPivotQuickSortBo5() {
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

    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int m3 = start + (len >> 1); // middle
    final int m2 = m3 - seventh;
    final int m1 = m2 - seventh;
    final int m4 = m3 + seventh;
    final int m5 = m4 + seventh;

    // Explicit (and optimal) sorting network for 5 elements
    // See Knuth for details.
    SortingNetworks.sort5(adapter, data, m1, m2, m3, m4, m5);

    // Choose the 2 and 4th as pivots, as we want to get three parts
    adapter.swap(data, m2, start);
    adapter.swap(data, m4, last);

    // A tie is when the two chosen pivots are the same
    final boolean tied = adapter.equals(data, start, last);

    // Insertion points for pivot areas.
    int left = start + 1;
    int right = last - 1;

    // Note: we merged the ties and no ties cases.
    // This likely is marginally slower, but not at a macro level
    // And you never know with hotspot.
    for (int k = left; k <= right; k++) {
      if (adapter.equals(data, k, start)) {
        continue;
      } else if (adapter.greaterThan(data, start, k)) {
        // Traditional QuickSort swap:
        if (k != left) {
          adapter.swap(data, k, left);
        }
        left++;
      } else if (tied || adapter.greaterThan(data, k, last)) {
        // Now look at the right. First skip correct entries there, too
        while (k < right && adapter.greaterThan(data, right, last)) {
          right--;
        }
        // Now move current element to the right.
        if (k < right) {
          adapter.swap(data, k, right);
          // Test the element we just moved: left or center?
          if (adapter.greaterThan(data, start, k)) {
            adapter.swap(data, left, k);
            left++;
          } // else: center. cannot be on right.
        }
        right--;
      }
    }
    // Put the pivot elements in their appropriate positions.
    adapter.swap(data, start, left - 1);
    adapter.swap(data, last, right + 1);
    if (adapter.greaterThan(data, right, right + 1)) {
      throw new RuntimeException("Sorting error.");
    }
    // Perform recursion:
    if (start < left - 2) {
      sort(adapter, data, start, left - 1);
    }
    // Handle the middle part - if necessary:
    if (!tied && left < right) {
      // TODO: the original publication had a special tie handling here.
      // It shouldn't affect correctness, but probably improves situations
      // with a lot of tied elements.
      sort(adapter, data, left, right + 1);
    }
    if (right + 3 < end) {
      sort(adapter, data, right + 2, end);
    }
  }
}
