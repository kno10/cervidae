package com.kno10.java.cervidae.controller.arraylike;

/**
 * Adapter class to control array-like data structures.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Data structure type
 */
public interface ArrayController<T, O> {
  /**
   * Get the object at the given position.
   * 
   * @param data Data structure
   * @param pos Position
   * @return Value at this position (as object)
   */
  O get(T data, int pos);

  /**
   * Get the length of the data structure.
   * 
   * @param data Data structure
   * @return Length
   */
  int length(T data);

  /**
   * Swap the two elements at positions i and j.
   * 
   * @param data Data structure
   * @param i Position i
   * @param j Position j
   */
  void swap(T data, int i, int j);

  /**
   * Compare two elements.
   * 
   * @param data Data structure
   * @param i Position i
   * @param j Position j
   * @return {@code true} when the element at position i is greater than that at
   *         position j.
   */
  boolean greaterThan(T data, int i, int j);

  /**
   * Compare two elements for equality
   * 
   * @param data Data structure
   * @param i Position i
   * @param j Position j
   * @return {@code true} when the two elements are equal.
   */
  boolean equals(T data, int i, int j);
}
