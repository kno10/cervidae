package com.kno10.java.cervidae.controller.arraylike;

/**
 * Adapter class to control array-like data structures.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Data structure type
 */
public interface ArrayWriteController<T, O> extends ArrayReadController<T, O> {
  /**
   * Set the value at the given position.
   * 
   * @param data Data structure
   * @param pos Position
   * @param val Value
   */
  void set(T data, int pos, O val);

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
