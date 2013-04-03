package com.kno10.java.cervidae.algorithms.search;

import com.kno10.java.cervidae.adapter.arraylike.ArraySearchAdapter;

/**
 * Binary search is a O(log n) search strategy for sorted arrays. It starts at
 * the middle element, then divides the remaining array into two parts and only
 * continues to search in the appropriate half.
 * 
 * It's a fairly simple algorithm, but there are actually some corner cases to
 * handle incorrectly, such as handling duplicate keys, empty arrays, NaNs and
 * misses.
 * 
 * Not all of these corner cases are necessarily solved here, please contribute
 * appropriate unit tests!
 * 
 * Duplicate keys: this code will return <em>any</em> of the matching keys, not
 * necessarily the first!
 * 
 * @author Erich Schubert
 */
public class BinarySearch {
  /**
   * Perform binary search.
   * 
   * If the return value is negative, it indicates that the key was not found.
   * The return value then gives the insertion position, i.e. {@code -(pos + 1)}
   * is the position where the element should be inserted, the position of the
   * next largest element.
   * 
   * @param control Data controller
   * @param data Data structure
   * @param start Interval start
   * @param end Interval end
   * @return Position, where negative values indicate that the key was not
   *         found.
   */
  public static <T> int search(ArraySearchAdapter<? super T> control, T data, int start, int end) {
    while(start < end) {
      // We assume this is safe, as start and end are considered to be signed.
      // Then at most we overflow into the sign bit, which is safe with the
      // triple shift operator.
      final int mid = (start + end) >>> 1;

      int cmp = control.compareWithKey(data, mid);

      if(cmp < 0) {
        start = mid + 1;
      }
      else if(cmp > 0) {
        end = mid;
      }
      else {
        return mid;
      }
    }
    return -(start + 1); // Insertion position.
  }
}
