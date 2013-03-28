package com.kno10.java.cervidae.arrays;

public class ComparableArrayController<T extends Comparable<? super T>> implements ArrayController<T[]> {
  @Override
  public int length(T[] data) {
    return data.length;
  }

  @Override
  public void swap(T[] data, int i, int j) {
    T tmp = data[i];
    data[i] = data[j];
    data[j] = tmp;
  }

  @Override
  public boolean greaterThan(T[] data, int i, int j) {
    return data[i].compareTo(data[j]) > 0;
  }

  @Override
  public boolean equals(T[] data, int i, int j) {
    return data[i].compareTo(data[j]) == 0;
  }
}
