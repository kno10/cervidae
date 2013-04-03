package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Abstract base class for array sorting algorithms.
 * 
 * @author Erich Schubert
 */
abstract public class AbstractArraySortAlgorithm implements ArraySortAlgorithm {
  @Override
  public <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void sort(A adapter, T data) {
    sort(adapter, data, 0, adapter.length(data));
  }

  @Override
  abstract public <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end);
}
