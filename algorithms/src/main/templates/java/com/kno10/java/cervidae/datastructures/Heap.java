package com.kno10.java.cervidae.datastructures;

import com.kno10.java.cervidae.iterator.Iter;

/**
 * Basic in-memory heap for ${api-type} values.
 * 
 * @author Erich Schubert
 * ${generics-documentation}
 */
public interface ${Type}Heap${def-generics} {
  /**
   * Add a key-value pair to the heap
   * 
   * @param key Key
   */
  void add(${api-type} key);

  /**
   * Add a key-value pair to the heap, except if the new element is larger than
   * the top, and we are at design size (overflow)
   * 
   * @param key Key
   * @param max Maximum size of heap
   */
  void add(${api-type} key, int max);

  /**
   * Combined operation that removes the top element, and inserts a new element
   * instead.
   * 
   * @param e New element to insert
   * @return Previous top element of the heap
   */
  ${api-type} replaceTopElement(${api-type} e);

  /**
   * Get the current top key
   * 
   * @return Top key
   */
  ${api-type} peek();

  /**
   * Remove the first element
   * 
   * @return Top element
   */
  ${api-type} poll();

  /**
   * Delete all elements from the heap.
   */
  void clear();

  /**
   * Query the size
   * 
   * @return Size
   */
  public int size();
  
  /**
   * Is the heap empty?
   * 
   * @return {@code true} when the size is 0.
   */
  public boolean isEmpty();

  /**
   * Get an unsorted iterator to inspect the heap.
   * 
   * @return Iterator
   */
  UnsortedIter${use-generics} unsortedIter();

  /**
   * Unsorted iterator - in heap order. Does not poll the heap.
   * 
   * <pre>
   * {@code
   * for (${Type}Heap.UnsortedIter${use-generics} iter = heap.unsortedIter(); iter.valid(); iter.next()) {
   *   doSomething(iter.get());
   * }
   * }
   * </pre>
   * 
   * @author Erich Schubert
   * ${generics-documentation}
   */
  public static interface UnsortedIter${def-generics} extends Iter {
    /**
     * Get the iterators current object.
     * 
     * @return Current object
     */
    ${api-type} get();
  }
}
