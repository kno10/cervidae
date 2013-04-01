package com.kno10.java.cervidae.adapter.arraylike;

/**
 * Adapter class to control array-like data structures.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Data structure type
 * @param <O> Value type
 */
public interface ArrayWriteAdapter<T, O> {
  /**
   * Set the value at the given position.
   * 
   * @param data Data structure
   * @param pos Position
   * @param val Value
   */
  void set(T data, int pos, O val);
}
