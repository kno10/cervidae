package com.kno10.java.cervidae.iterator;

import java.util.List;

/**
 * ELKI style Iterator for array lists.
 * 
 * Note: this implementation is only efficient for lists with efficient random
 * access and seeking (i.e. ArrayLists, but not Linked Lists!)
 * 
 * @author Erich Schubert
 * 
 * @param <O> contained object type.
 */
public class ArrayListIter<O> implements ArrayIter {
  /**
   * The array list to iterate over.
   */
  final List<O> data;

  /**
   * Current position.
   */
  int pos = 0;

  /**
   * Constructor.
   * 
   * @param data Data array.
   */
  public ArrayListIter(List<O> data) {
    super();
    this.data = data;
  }

  @Override
  public boolean valid() {
    return pos < data.size();
  }

  @Override
  public void advance() {
    pos++;
  }

  @Override
  public int getOffset() {
    return pos;
  }

  @Override
  public void advance(int count) {
    pos += count;
  }

  @Override
  public void retract() {
    pos--;
  }

  @Override
  public void seek(int off) {
    pos = off;
  }

  /**
   * Get the current element.
   * 
   * @return current element
   */
  public O get() {
    return data.get(pos);
  }
}
