package com.kno10.java.cervidae.controller.arraylike;

import java.util.ArrayList;

public class ComparableArrayListController<T extends Comparable<? super T>> implements ArrayWriteController<ArrayList<T>, T> {
  @Override
  public T get(ArrayList<T> data, int pos) {
    return data.get(pos);
  }

  @Override
  public void set(ArrayList<T> data, int pos, T val) {
    data.set(pos, val);
  }

  @Override
  public int length(ArrayList<T> data) {
    return data.size();
  }

  @Override
  public void swap(ArrayList<T> data, int i, int j) {
    T tmp = data.get(i);
    data.set(i, data.get(j));
    data.set(j, tmp);
  }

  @Override
  public boolean greaterThan(ArrayList<T> data, int i, int j) {
    return data.get(i).compareTo(data.get(j)) > 0;
  }

  @Override
  public boolean equals(ArrayList<T> data, int i, int j) {
    return data.get(i).compareTo(data.get(j)) == 0;
  }
}