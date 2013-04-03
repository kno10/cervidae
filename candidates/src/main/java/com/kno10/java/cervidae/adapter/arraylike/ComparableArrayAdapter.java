package com.kno10.java.cervidae.adapter.arraylike;

/**
 * Class to process an array of comparable objects.
 * 
 * @author Erich Schubert
 * 
 * @param <T> Actual data type
 * @param <S> Array data type (often: Object)
 */
public class ComparableArrayAdapter<T extends Comparable<? super T>, S> implements ArrayReadAdapter<S[], T>, ArrayWriteAdapter<S[], T>, ArraySortAdapter<S[]> {
  @SuppressWarnings("unchecked")
  @Override
  public T get(S[] data, int pos) {
    return (T) data[pos];
  }

  @SuppressWarnings("unchecked")
  @Override
  public void set(S[] data, int pos, T val) {
    data[pos] = (S) val;
  }

  @Override
  public int length(S[] data) {
    return data.length;
  }

  @Override
  public void swap(S[] data, int i, int j) {
    S tmp = data[i];
    data[i] = data[j];
    data[j] = tmp;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean greaterThan(S[] data, int i, int j) {
    return ((T) data[i]).compareTo((T) data[j]) > 0;
  }

  @SuppressWarnings("unchecked")
  @Override
  public boolean equals(S[] data, int i, int j) {
    return ((T) data[i]).compareTo((T) data[j]) == 0;
  }
}
