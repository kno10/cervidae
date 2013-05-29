package com.kno10.java.cervidae.adapter.arraylike;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Class to process an array of comparable objects, with (re-)allocation support
 * (this however needs the base class for the array).
 * 
 * @author Erich Schubert
 * 
 * @param <T> Actual data type
 * @param <S> Array data type (often: Object)
 */
public class ComparableArrayAllocationAdapter<T extends Comparable<? super T>, S> extends ComparableArrayAdapter<T, S> implements ArrayAllocationAdapter<S[]> {
  /**
   * Data type class of the actual array.
   */
  final protected Class<S> clz;

  /**
   * Constructor.
   * 
   * @param clz Class for underlying arrays.
   */
  public ComparableArrayAllocationAdapter(Class<S> clz) {
    super();
    this.clz = clz;
  }

  @SuppressWarnings("unchecked")
  @Override
  public S[] newArray(int capacity) {
    return (S[]) Array.newInstance(clz, capacity);
  }

  @Override
  public S[] ensureCapacity(S[] existing, int capacity) {
    return Arrays.copyOf(existing, capacity);
  }
}
