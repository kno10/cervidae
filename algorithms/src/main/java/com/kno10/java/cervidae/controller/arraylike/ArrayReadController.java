package com.kno10.java.cervidae.controller.arraylike;

/**
 * Adapter class to control array-like data structures.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Data structure type
 */
public interface ArrayReadController<T, O> {
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
}
