package com.kno10.java.cervidae.algorithms.select;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Interface for selecting an particular element in an array.
 * 
 * @author Erich Schubert
 */
public interface ArraySelectionAlgorithm {
  /**
   * Sort the full array using the given comparator.
   * 
   * @param adapter Array data structure adapter
   * @param data Data structure to select from
   * @param rank Rank to select
   */
  <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void select(A adapter, T data, int rank);

  /**
   * Sort the array using the given comparator.
   * 
   * @param adapter Array data structure adapter
   * @param data Data structure to select from
   * @param start First index
   * @param end Last index (exclusive)
   * @param rank Rank to select
   */
  <T> void select(ArraySortAdapter<? super T> adapter, T data, int start, int end, int rank);
}
