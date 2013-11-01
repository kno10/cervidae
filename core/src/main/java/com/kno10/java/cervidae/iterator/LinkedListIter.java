package com.kno10.java.cervidae.iterator;

import java.util.List;
import java.util.ListIterator;

/**
 * ELKI style iterator for linked lists.
 * 
 * @author Erich Schubert
 * 
 * @param <O> contained object type.
 */
public class LinkedListIter<O> implements MIter {
  /**
   * The array list to iterate over.
   */
  ListIterator<O> iter;

  /**
   * Current object.
   */
  O current;

  /**
   * Constructor
   * 
   * @param data List to iterate over.
   */
  public LinkedListIter(List<O> data) {
    super();
    this.iter = data.listIterator();
  }

  @Override
  public boolean valid() {
    return (iter != null);
  }

  @Override
  public void advance() {
    if(iter.hasNext()) {
      current = iter.next();
    }
    else {
      iter = null;
    }
  }

  @Override
  public void remove() {
    iter.remove();
  }
}
