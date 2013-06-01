package com.kno10.java.cervidae.datastructures.heap4;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

import com.kno10.java.cervidae.datastructures.${parent-Type}Heap;

/**
 * Priority queue, based on a 4-ary heap.
 * 
 * This code was automatically instantiated for the type: ${Type}
 * 
 * @author Erich Schubert
 * ${generics-documentation}
 */
public class ${Type}MinHeap4${def-generics} implements ${parent-Type}Heap${use-generics} {
  /**
   * Heap storage.
   */
  protected ${rawtype}[] heap;

  /**
   * Current size of heap.
   */
  protected int size;

  /**
   * (Structural) modification counter. Used to invalidate iterators.
   */
  protected int modCount = 0;

  /**
   * Initial size of 4-ary heap when initialized.
   * 
   * 21 = 4-ary heap of height 2: 1 + 4 + 4*4
   * 
   * 85 = 4-ary heap of height 3: 21 + 4*4*4
   * 
   * 341 = 4-ary heap of height 4: 85 + 4*4*4*4
   * 
   * Without further hints, start with the smallest, 21 elements.
   */
  private final static int FOUR_HEAP_INITIAL_SIZE = 21;

  ${extra-fields}

  /**
   * Constructor, with default size.
   *
   * ${extra-constructor-documentation}
   */
  ${unchecked}
  public ${Type}MinHeap4(${extra-constructor}) {
    super();
    ${extra-constructor-init}
    ${rawtype}[] heap = ${newarray,FOUR_HEAP_INITIAL_SIZE};
    this.heap = heap;
    this.size = 0;
    this.modCount = 0;
  }

  /**
   * Constructor, with given minimum size.
   * 
   * @param minsize Minimum size
   * ${extra-constructor-documentation}
   */
  ${unchecked}
  public ${Type}MinHeap4(int minsize, ${extra-constructor}) {
    super();
    ${extra-constructor-init}
    // TODO: upscale to the next "optimal" size?
    ${rawtype}[] heap = ${newarray,size};
    this.heap = heap;
    this.size = 0;
    this.modCount = 0;
  }

  @Override
  public void clear() {
    size = 0;
    ++modCount;
    Arrays.fill(heap, ${null});
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return (size == 0);
  }

  @Override
  ${unchecked}
  public void add(${api-type} o) {
    final ${rawtype} co = ${rawcast}o;
    if (size >= heap.length) {
      // Almost double, but remain an odd number
      heap = Arrays.copyOf(heap, heap.length + heap.length - 1);
    }
    final int pos = size;
    ++size;
    heapifyUp4(pos, co);
    ++modCount;
  }

  @Override
  public void add(${api-type} key, int max) {
    if (size < max) {
      add(key);
    } else if (${compare,<=,heap[0],key}) {
      replaceTopElement(key);
    }
  }

  @Override
  ${unchecked}
  public ${api-type} replaceTopElement(${api-type} reinsert) {
    final ${rawtype} ret = heap[0];
    heapifyDown4(0, ${rawcast} reinsert);
    ++modCount;
    return ${api-cast}ret;
  }

  /**
   * Heapify-Up method for 4-ary heap.
   * 
   * @param pos Position in 4-ary heap.
   * @param cur Current object
   */
  private void heapifyUp4(int pos, ${rawtype} cur) {
    while (pos > 0) {
      final int parent = (pos - 1) >>> 2;
      ${rawtype} par = heap[parent];
      if (${compare,>=,cur,par}) {
        break;
      }
      heap[pos] = par;
      pos = parent;
    }
    heap[pos] = cur;
  }

  @Override
  ${unchecked}
  public ${api-type} poll() {
    final ${rawtype} ret = heap[0];
    --size;
    // Replacement object:
    if (size > 0) {
      final ${rawtype} reinsert = heap[size];
      heap[size] = ${null};
      heapifyDown4(0, reinsert);
    } else {
      heap[0] = ${null};
    }
    ++modCount;
    return ${api-cast}ret;
  }

  /**
   * Heapify-Down for 4-ary heap.
   * 
   * @param pos Position in 4-ary heap.
   * @param cur Current object
   */
  private void heapifyDown4(int pos, ${rawtype} cur) {
    final int stop = (size + 2) >>> 2;
    while (pos < stop) {
      int bestchild = (pos << 2) + 1;
      ${rawtype} best = heap[bestchild];
      int candidate = bestchild + 1;
      for (int i = 0; i < 3 && candidate < size; i++, candidate++) {
        ${rawtype} nextchild = heap[candidate];
        if (${compare,>,best,nextchild}) {
          bestchild = candidate;
          best = nextchild;
        }
      }
      if (${compare,<=,cur,best}) {
        break;
      }
      heap[pos] = best;
      pos = bestchild;
    }
    heap[pos] = cur;
  }

  @Override
  ${unchecked}
  public ${api-type} peek() {
    return ${api-cast}heap[0];
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(${Type}MinHeap4.class.getSimpleName()).append(" [");
    for (UnsortedIter iter = new UnsortedIter(); iter.valid(); iter.advance()) {
      buf.append(iter.get()).append(',');
    }
    buf.append(']');
    return buf.toString();
  }

  @Override
  public UnsortedIter unsortedIter() {
    return new UnsortedIter();
  }

  /**
   * Validate the heap.
   * 
   * @return {@code null} when there were no errors, an error message otherwise.
   */
  protected String checkHeap() {
    for (int i = 1; i < size; i++) {
      final int parent = (i - 1) >>> 2;
      if (${compare,>,heap[parent],heap[i]}) {
        return "@" + parent + ": " + heap[parent] + " > @" + i + ": " + heap[i];
      }
    }
    return null;
  }

  /**
   * Unsorted iterator - in heap order. Does not poll the heap.
   * 
   * Use this class as follows:
   * 
   * <pre>
   * {@code
   * for (${parent-Type}Heap.UnsortedIter${use-generics} iter = heap.unsortedIter(); iter.valid(); iter.next()) {
   *   doSomething(iter.get());
   * }
   * }
   * </pre>
   * 
   * @author Erich Schubert
   */
  private class UnsortedIter implements ${parent-Type}Heap.UnsortedIter${use-generics} {
    /**
     * Iterator position.
     */
    protected int pos = 0;

    /**
     * Modification counter we were initialized at.
     */
    protected final int myModCount = modCount;

    @Override
    public boolean valid() {
      if (modCount != myModCount) {
        throw new ConcurrentModificationException();
      }
      return pos < size;
    }

    @Override
    public void advance() {
      pos++;
    }

    ${unchecked}
    @Override
    public ${api-type} get() {
      return ${api-cast}(heap[pos]);
    }
  }
}
