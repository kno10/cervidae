package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Interface for array sorting algorithms.
 * 
 * @author Erich Schubert
 */
public interface ArraySortAlgorithm {
  /**
   * Sort the full array using the given comparator.
   * 
   * @param adapter Array data structure adapter
   * @param data Data structure to sort
   */
  <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void sort(A adapter, T data);

  /**
   * Sort the array using the given comparator.
   * 
   * @param adapter Array data structure adapter
   * @param data Data structure to sort
   * @param start First index
   * @param end Last index (exclusive)
   */
  <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end);
}
