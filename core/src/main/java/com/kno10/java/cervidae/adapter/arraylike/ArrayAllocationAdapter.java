package com.kno10.java.cervidae.adapter.arraylike;

/**
 * Adapter class to control array-like data structures.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Data structure type
 */
public interface ArrayAllocationAdapter<T> {
  /**
   * Allocate a new array of the given initial size.
   * 
   * @param capacity Initial capacity
   * @return New array
   */
  T newArray(int capacity);

  /**
   * Resize to the new capacity.
   * 
   * @param existing Existing array
   * @param capacity New capacity
   * @return New array
   */
  T ensureCapacity(T existing, int capacity);
}
