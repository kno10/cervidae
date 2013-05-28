package com.kno10.java.cervidae.algorithms.select;

import com.kno10.java.cervidae.adapter.arraylike.ArrayReadAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Abstract base class for array selection algorithms.
 * 
 * @author Erich Schubert
 */
public abstract class AbstractArraySelectionAlgorithm implements ArraySelectionAlgorithm {
  @Override
  public <A extends ArrayReadAdapter<? super T, ?> & ArraySortAdapter<? super T>, T> void select(A adapter, T data, int rank) {
    select(adapter, data, 0, adapter.length(data), rank);
  }
}