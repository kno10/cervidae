package com.kno10.java.cervidae.algorithms.sort;

import java.util.Comparator;

import com.kno10.java.cervidae.comparator.${Type}Comparator;

/**
 * Class to sort an ${type} array, using a modified quicksort.
 * 
 * The implementation is closely based on:
 * <p>
 * Dual-Pivot Quicksort<br />
 * Vladimir Yaroslavskiy
 * </p>
 *
 * and differs mostly in that we sort different kinds of arrays,
 * and allow the use of comparators - useful in particular when
 * the array references external objects.
 * 
 * For default comparators, see {@link com.kno10.java.cervidae.comparator.PrimitiveComparators}
 * 
 * @author Erich Schubert
 */
public class ${Type}ArrayQuickSort {
  /**
   * Threshold for using insertion sort. Value taken from Javas QuickSort,
   * assuming that it will be similar for our data sets.
   */
  private static final int INSERTION_THRESHOLD = 35;

  /**
   * Static instance.
   */
  public static final ${Type}ArrayQuickSort STATIC = new ${Type}ArrayQuickSort(INSERTION_THRESHOLD);
  
  /**
   * Insertion threshold.
   */
  private final int threshold;

  /**
   * Constructor with parameterizable threshold.
   * 
   * @param threshold Threshold to switch to insertion sort.
   */
  public ${Type}ArrayQuickSort(int threshold) {
    super();
    this.threshold = threshold;
  }

  /**
   * Default constructor.
   * 
   * @deprecated For default options, use {@link #STATIC} instead.
   */
  @Deprecated
  public ${Type}ArrayQuickSort() {
    this(INSERTION_THRESHOLD);
  }

  /**
   * Sort the full array using the given comparator.
   * 
   * @param data Data to sort
   * @param comp Comparator
   */
  public void sort(${atype}[] data, ${Type}Comparator comp) {
    sort(data, 0, data.length, comp);
  }
  
  /**
   * Sort the array using the given comparator.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive)
   * @param comp Comparator
   */
  public void sort(${atype}[] data, final int start, final int end, ${Type}Comparator comp) {
    final int len = end - start;
    final int last = end - 1;
    if (len < threshold) {
      insertionSort(data, start, end, comp);
      return;
    }

    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int d3 = start + (len >> 1); // middle
    final int d2 = d3 - seventh;
    final int d1 = d2 - seventh;
    final int d4 = d3 + seventh;
    final int d5 = d4 + seventh;

    // Sort the five candidates:
    // Explicit (and optimal) sorting network for 5 elements
    // See Knuth for details.
    sort5(data, d1, d2, d3, d4, d5, comp);

    // Choose the 2 and 4th as pivots, as we want to get three parts
    final ${type} lpivot = data[d2];
    final ${type} rpivot = data[d4];
    data[d2] = data[start];
    data[d4] = data[last];

    // A tie is when the two chosen pivots are the same
    final boolean tied = comp.compare(lpivot, rpivot) == 0;

    // Insertion points for pivot areas.
    int left = start + 1;
    int right = last - 1;

    // Note: we merged the ties and no ties cases.
    // This likely is marginally slower, but not at a macro level
    // And you never know with hotspot.
    for (int k = left; k <= right; k++) {
      final ${type} tmp = data[k];
      final int c = comp.compare(tmp, lpivot);
      if (c == 0) {
        continue;
      } else if (c < 0) {
        // Traditional quicksort
        data[k] = data[left];
        data[left] = tmp;
        left++;
      } else if (tied || comp.compare(tmp, rpivot) > 0) {
        // Now look at the right. First skip correct entries there, too
        while (k < right && comp.compare(data[right], rpivot) > 0) {
          right--;
        }
        // Now move tmp from k to the right.
        if (k < right) {
          data[k] = data[right];
          data[right] = tmp;
          // Test the element we just inserted: left or center?
          if (comp.compare(data[k], lpivot) < 0) {
            final ${type} tmp2 = data[k];
            data[k] = data[left];
            data[left] = tmp2;
            left++;
          } // else: center. cannot be on right.
        }
        right--;
      }
    }
    // Put the pivot elements back in.
    // Remember: we must not modify v1 and v3 above.
    data[start] = data[left - 1];
    data[left - 1] = lpivot;
    data[end - 1] = data[right + 1];
    data[right + 1] = rpivot;
    // v1 and v3 are now safe to modify again. Perform recursion:
    if (start < left - 2) {
      sort(data, start, left - 1, comp);
    }
    // Handle the middle part - if necessary:
    if (!tied && left < right) {
      // TODO: the original publication had a special tie handling here.
      // It shouldn't affect correctness, but probably improves situations
      // with a lot of tied elements.
      sort(data, left, right + 1, comp);
    }
    if (right + 3 < end) {
      sort(data, right + 2, end, comp);
    }
  }

  /**
   * Sort the array ascending with natural order.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive)
   */
  public void ascending(${atype}[] data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if (len < threshold) {
      // Classic insertion sort.
      insertionSortAscending(data, start, end);
      return;
    }

    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int d3 = start + (len >> 1); // middle
    final int d2 = d3 - seventh;
    final int d1 = d2 - seventh;
    final int d4 = d3 + seventh;
    final int d5 = d4 + seventh;

    // Explicit (and optimal) sorting network for 5 elements
    // See Knuth for details.
    sort5(data, d1, d2, d3, d4, d5);

    // Choose the 2 and 4th as pivots, as we want to get three parts
    final ${type} lpivot = data[d2];
    final ${type} rpivot = data[d4];
    data[d2] = data[start];
    data[d4] = data[last];

    // A tie is when the two chosen pivots are the same
    final boolean tied = lpivot == rpivot;

    // Insertion points for pivot areas.
    int left = start + 1;
    int right = last - 1;

    // Note: we merged the ties and no ties cases.
    // This likely is marginally slower, but not at a macro level
    // And you never know with hotspot.
    for (int k = left; k <= right; k++) {
      final ${type} tmp = data[k];
      if (tmp < lpivot) {
        // Traditional quicksort
        data[k] = data[left];
        data[left] = tmp;
        left++;
      } else if (tmp > lpivot && (tied || tmp > rpivot)) {
        // Now look at the right. First skip correct entries there, too
        while (k < right && data[right] > rpivot) {
          right--;
        }
        // Now move tmp from k to the right.
        if (k < right) {
          data[k] = data[right];
          data[right] = tmp;
          // Test the element we just inserted: left or center?
          if (data[k] < lpivot) {
            final ${type} tmp2 = data[k];
            data[k] = data[left];
            data[left] = tmp2;
            left++;
          } // else: center. cannot be on right.
        }
        right--;
      }
    }
    // Put the pivot elements back in.
    // Remember: we must not modify v1 and v3 above.
    data[start] = data[left - 1];
    data[left - 1] = lpivot;
    data[end - 1] = data[right + 1];
    data[right + 1] = rpivot;
    // v1 and v3 are now safe to modify again. Perform recursion:
    if (start < left - 2) {
      ascending(data, start, left - 1);
    }
    // Handle the middle part - if necessary:
    if (!tied && left < right) {
      // TODO: the original publication had a special tie handling here.
      // It shouldn't affect correctness, but probably improves situations
      // with a lot of tied elements.
      ascending(data, left, right + 1);
    }
    if (right + 3 < end) {
      ascending(data, right + 2, end);
    }
  }

  /**
   * Sort the array descending with natural order.
   * 
   * @param data Data to sort
   * @param start First index
   * @param end Last index (exclusive)
   */
  public void descending(${atype}[] data, final int start, final int end) {
    final int len = end - start;
    final int last = end - 1;
    if (len < threshold) {
      // Classic insertion sort.
      insertionSortDescending(data, start, end);
      return;
    }

    // Choose pivots by looking at five candidates.
    final int seventh = (len >> 3) + (len >> 6) + 1;
    final int d3 = start + (len >> 1); // middle
    final int d2 = d3 - seventh;
    final int d1 = d2 - seventh;
    final int d4 = d3 + seventh;
    final int d5 = d4 + seventh;

    // Reverse sorting by using the positions in reverse!
    sort5(data, d5, d4, d3, d2, d1);

    // Choose the 2 and 4th as pivots, as we want to get three parts
    final ${type} lpivot = data[d2];
    final ${type} rpivot = data[d4];
    data[d2] = data[start];
    data[d4] = data[last];

    // A tie is when the two chosen pivots are the same
    final boolean tied = lpivot == rpivot;

    // Insertion points for pivot areas.
    int left = start + 1;
    int right = last - 1;

    // Note: we merged the ties and no ties cases.
    // This likely is marginally slower, but not at a macro level
    // And you never know with hotspot.
    for (int k = left; k <= right; k++) {
      final ${type} tmp = data[k];
      if (tmp > lpivot) {
        // Traditional quicksort
        data[k] = data[left];
        data[left] = tmp;
        left++;
      } else if (tmp < lpivot && (tied || tmp < rpivot)) {
        // Now look at the right. First skip correct entries there, too
        while (k < right && data[right] < rpivot) {
          right--;
        }
        // Now move tmp from k to the right.
        if (k < right) {
          data[k] = data[right];
          data[right] = tmp;
          // Test the element we just inserted: left or center?
          if (data[k] > lpivot) {
            final ${type} tmp2 = data[k];
            data[k] = data[left];
            data[left] = tmp2;
            left++;
          } // else: center. cannot be on right.
        }
        right--;
      }
    }
    // Put the pivot elements back in.
    // Remember: we must not modify v1 and v3 above.
    data[start] = data[left - 1];
    data[left - 1] = lpivot;
    data[end - 1] = data[right + 1];
    data[right + 1] = rpivot;
    // v1 and v3 are now safe to modify again. Perform recursion:
    if (start < left - 2) {
      descending(data, start, left - 1);
    }
    // Handle the middle part - if necessary:
    if (!tied && left < right) {
      // TODO: the original publication had a special tie handling here.
      // It shouldn't affect correctness, but probably improves situations
      // with a lot of tied elements.
      descending(data, left, right + 1);
    }
    if (right + 3 < end) {
      descending(data, right + 2, end);
    }
  }
  
  /**
   * Insertion sort, use for small arrays only!
   * 
   * @param data Data array
   * @param start Starting position
   * @param end End position (exclusive!)
   * @param comp Comparator
   */
  public void insertionSort(${atype}[] data, final int start, final int end, ${Type}Comparator comp) {
    for (int i = start, j = i++; i < end; j = i++) {
      final ${atype} tmp = data[i];
      while (comp.compare(tmp, data[j]) < 0) {
        data[j + 1] = data[j];
        if (j-- == start) {
          break;
        }
      }
      data[j + 1] = tmp;
    }
  }

  /**
   * Insertion sort, use for small arrays only!
   * 
   * @param data Data array
   * @param start Starting position
   * @param end End position (exclusive!)
   * @param comp Comparator
   */
  public void dualInsertionSort(${atype}[] data, final int start, final int end, ${Type}Comparator comp) {
    int i = start + 2;
    for (; i < end; i += 2) {
      ${atype} tmp1 = data[i - 1], tmp2 = data[i];
      if (comp.compare(tmp2, tmp1) < 0) {
        tmp1 = tmp2; tmp2 = data[i - 1];
      }
      int j = i - 2; 
      for (; j >= start && comp.compare(tmp2, data[j]) < 0; j--) {
        data[j + 2] = data[j];
      }
      data[j + 2] = tmp2;
      for (; j >= start && comp.compare(tmp1, data[j]) < 0; j--) {
        data[j + 1] = data[j];
      }
      data[j + 1] = tmp1;
    }
    if (i == end) {
      final ${atype} tmp1 = data[i - 1];
      int j = i - 2;
      for (; j >= start && comp.compare(tmp1, data[j]) < 0; j--) {
        data[j + 1] = data[j];
      }
      data[j + 1] = tmp1;
    }
  }

  /**
   * Insertion sort, use for small arrays only!
   * 
   * @param data Data array
   * @param start Starting position
   * @param end End position (exclusive!)
   */
  public void insertionSortAscending(${atype}[] data, final int start, final int end) {
    for (int i = start, j = i++; i < end; j = i++) {
      final ${atype} tmp = data[i];
      while (tmp < data[j]) {
        data[j + 1] = data[j];
        if (j-- == start) {
          break;
        }
      }
      data[j + 1] = tmp;
    }
  }

  /**
   * Insertion sort, use for small arrays only!
   * 
   * @param data Data array
   * @param start Starting position
   * @param end End position (exclusive!)
   */
  public void dualInsertionSort(${atype}[] data, final int start, final int end) {
    int i = start + 2;
    for (; i < end; i += 2) {
      ${atype} tmp1 = data[i - 1], tmp2 = data[i];
      if (tmp2 < tmp1) {
        tmp1 = tmp2; tmp2 = data[i - 1];
      }
      int j = i - 2; 
      for (; j >= start && tmp2 < data[j]; j--) {
        data[j + 2] = data[j];
      }
      data[j + 2] = tmp2;
      for (; j >= start && tmp1 < data[j]; j--) {
        data[j + 1] = data[j];
      }
      data[j + 1] = tmp1;
    }
    if (i == end) {
      final ${atype} tmp1 = data[i - 1];
      int j = i - 2;
      for (; j >= start && tmp1 < data[j]; j--) {
        data[j + 1] = data[j];
      }
      data[j + 1] = tmp1;
    }
  }

  /**
   * Insertion sort, use for small arrays only!
   * 
   * @param data Data array
   * @param start Starting position
   * @param end End position (exclusive!)
   */
  void insertionSortDescending(${atype}[] data, final int start, final int end) {
    for (int i = start, j = i++; i < end; j = i++) {
      final ${atype} tmp = data[i];
      while (tmp > data[j]) {
        data[j + 1] = data[j];
        if (j-- == start) {
          break;
        }
      }
      data[j + 1] = tmp;
    }
  }

  /**
   * Five element explicit dual insertion sort, inserting 4 and 5 at the same time.
   * 
   * Note: for this to make sense, {@code d1 < d2 < d3 < d4 < d5} should hold!
   * 
   * This version avoids else cases with extra writes.
   * 
   * @param data Data array
   * @param d1 Position of first
   * @param d2 Position of second
   * @param d3 Position of third
   * @param d4 Position of fifth
   * @param d5 Position of fourth
   * @param comp Comparator
   */
  static void sort5(${atype}[] data, int d1, int d2, int d3, int d4, int d5, ${Type}Comparator comp) {
    // Insert second
    if (comp.compare(data[d1], data[d2]) > 0) {
      final ${type} tmp = data[d2];
      data[d2] = data[d1];
      data[d1] = tmp;
    }
    // Insert third
    if (comp.compare(data[d2], data[d3]) > 0) {
      final ${type} tmp = data[d3];
      data[d3] = data[d2];
      data[d2] = tmp;
      if (comp.compare(data[d1], tmp) > 0) {
        data[d2] = data[d1];
        data[d1] = tmp;
      }
    }
    // Insert fourth
    if (comp.compare(data[d3], data[d4]) > 0) {
      final ${type} tmp = data[d4];
      data[d4] = data[d3];
      data[d3] = tmp;
      if (comp.compare(data[d2], tmp) > 0) {
        data[d3] = data[d2];
        data[d2] = tmp;
        if (comp.compare(data[d1], tmp) > 0) {
          data[d2] = data[d1];
          data[d1] = tmp;
        }
      }
    }
    // Insert fifth
    if (comp.compare(data[d4], data[d5]) > 0) {
      final ${type} tmp = data[d5];
      data[d5] = data[d4];
      data[d4] = tmp;
      if (comp.compare(data[d3], tmp) > 0) {
        data[d4] = data[d3];
        data[d3] = tmp;
        if (comp.compare(data[d2], tmp) > 0) {
          data[d3] = data[d2];
          data[d2] = tmp;
          if (comp.compare(data[d1], tmp) > 0) {
            data[d2] = data[d1];
            data[d1] = tmp;
          }
        }
      }
    }
  }

  
  /**
   * Five element explicit dual insertion sort, inserting 4 and 5 at the same time.
   * 
   * Note: for this to make sense, {@code d1 < d2 < d3 < d4 < d5} should hold!
   * 
   * This version avoids else cases with extra writes.
   * 
   * @param data Data array
   * @param d1 Position of first
   * @param d2 Position of second
   * @param d3 Position of third
   * @param d4 Position of fifth
   * @param d5 Position of fourth
   */
  static void sort5(${atype}[] data, int d1, int d2, int d3, int d4, int d5) {
    // Insert second
    if (data[d1] > data[d2]) {
      final ${type} tmp = data[d2];
      data[d2] = data[d1];
      data[d1] = tmp;
    }
    // Insert third
    if (data[d2] > data[d3]) {
      final ${type} tmp = data[d3];
      data[d3] = data[d2];
      data[d2] = tmp;
      if (data[d1] > tmp) {
        data[d2] = data[d1];
        data[d1] = tmp;
      }
    }
    // Insert fourth
    if (data[d3] > data[d4]) {
      final ${type} tmp = data[d4];
      data[d4] = data[d3];
      data[d3] = tmp;
      if (data[d2] > tmp) {
        data[d3] = data[d2];
        data[d2] = tmp;
        if (data[d1] > tmp) {
          data[d2] = data[d1];
          data[d1] = tmp;
        }
      }
    }
    // Insert fifth
    if (data[d4] > data[d5]) {
      final ${type} tmp = data[d5];
      data[d5] = data[d4];
      data[d4] = tmp;
      if (data[d3] > tmp) {
        data[d4] = data[d3];
        data[d3] = tmp;
        if (data[d2] > tmp) {
          data[d3] = data[d2];
          data[d2] = tmp;
          if (data[d1] > tmp) {
            data[d2] = data[d1];
            data[d1] = tmp;
          }
        }
      }
    }
  }
}
