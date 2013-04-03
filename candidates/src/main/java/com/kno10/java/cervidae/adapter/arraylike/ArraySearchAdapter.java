package com.kno10.java.cervidae.adapter.arraylike;

/**
 * Adapter class to control array-like data structures.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Data structure type
 */
public interface ArraySearchAdapter<T> {
  /**
   * Compare two elements for equality
   * 
   * @param data Data structure
   * @param pos Search position.
   * @return Comparison result of the search key and the element at position
   *         {@code pos}.
   */
  int compareWithKey(T data, int pos);
}
