package com.kno10.java.cervidae.datastructures.heap2;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

import com.kno10.java.cervidae.datastructures.${parent-Type}Heap;

/**
 * Basic binary heap implementation.
 * 
 * @author Erich Schubert
 * ${generics-documentation}
 */
public class ${Type}MinHeap2${def-generics} implements ${parent-Type}Heap${use-generics} {
  /**
   * Base heap.
   */
  protected ${rawtype}[] twoheap;

  /**
   * Current size of heap.
   */
  protected int size;

  /**
   * (Structural) modification counter. Used to invalidate iterators.
   */
  protected int modCount = 0;

  /**
   * Initial size of the 2-ary heap.
   */
  private final static int TWO_HEAP_INITIAL_SIZE = (1 << 5) - 1;

  ${extra-fields}

  /**
   * Constructor, with default size.
   *
   * ${extra-constructor-documentation}
   */
  ${unchecked}
  public ${Type}MinHeap2(${extra-constructor}) {
    super();
    ${extra-constructor-init}
    ${rawtype}[] twoheap = ${newarray,TWO_HEAP_INITIAL_SIZE};

    this.twoheap = twoheap;
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
  public ${Type}MinHeap2(int minsize, ${extra-constructor}) {
    super();
    ${extra-constructor-init}
    final int size = goodHeapSize(minsize);
    ${rawtype}[] twoheap = ${newarray,size};
    this.twoheap = twoheap;
    this.size = 0;
    this.modCount = 0;
  }

  /**
   * Find the next power of 2 - 1 heap size.
   * 
   * @param x original integer
   * @return Next power of 2 - 1
   */
  private static int goodHeapSize(int x) {
    x |= x >>> 1;
    x |= x >>> 2;
    x |= x >>> 4;
    x |= x >>> 8;
    x |= x >>> 16;
    return x;
  }

  @Override
  public void clear() {
    size = 0;
    ++modCount;
    Arrays.fill(twoheap, ${null});
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
    // System.err.println("Add: " + o);
    if (size >= twoheap.length) {
      // Grow by one layer.
      twoheap = Arrays.copyOf(twoheap, twoheap.length + twoheap.length + 1);
    }
    final int twopos = size;
    twoheap[twopos] = co;
    ++size;
    heapifyUp2(twopos, co);
    ++modCount;
  }

  @Override
  public void add(${api-type} key, int max) {
    if (size < max) {
      add(key);
    } else if (${compare,<=,twoheap[0],key}) {
      replaceTopElement(key);
    }
  }

  @Override
  ${unchecked}
  public ${api-type} replaceTopElement(${api-type} reinsert) {
    final ${rawtype} ret = twoheap[0];
    heapifyDown2(0, ${rawcast} reinsert);
    ++modCount;
    return ${api-cast}ret;
  }

  /**
   * Heapify-Up method for 2-ary heap.
   * 
   * @param twopos Position in 2-ary heap.
   * @param cur Current object
   */
  private void heapifyUp2(int twopos, ${rawtype} cur) {
    while (twopos > 0) {
      final int parent = (twopos - 1) >>> 1;
      ${rawtype} par = twoheap[parent];
      if (${compare,>=,cur,par}) {
        break;
      }
      twoheap[twopos] = par;
      twopos = parent;
    }
    twoheap[twopos] = cur;
  }

  @Override
  ${unchecked}
  public ${api-type} poll() {
    final ${rawtype} ret = twoheap[0];
    --size;
    // Replacement object:
    if (size > 0) {
      final ${rawtype} reinsert = twoheap[size];
      twoheap[size] = ${null};
      heapifyDown2(0, reinsert);
    } else {
      twoheap[0] = ${null};
    }
    ++modCount;
    return ${api-cast}ret;
  }

  /**
   * Heapify-Down for 2-ary heap.
   * 
   * @param twopos Position in 2-ary heap.
   * @param cur Current object
   */
  private void heapifyDown2(int twopos, ${rawtype} cur) {
    final int stop = size >>> 1;
    while (twopos < stop) {
      int bestchild = (twopos << 1) + 1;
      ${rawtype} best = twoheap[bestchild];
      final int right = bestchild + 1;
      if (right < size && ${compare,>,best,twoheap[right]}) {
        bestchild = right;
        best = twoheap[right];
      }
      if (${compare,<=,cur,best}) {
        break;
      }
      twoheap[twopos] = best;
      twopos = bestchild;
    }
    twoheap[twopos] = cur;
  }

  @Override
  ${unchecked}
  public ${api-type} peek() {
    return ${api-cast}twoheap[0];
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder();
    buf.append(${Type}MinHeap2.class.getSimpleName()).append(" [");
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
      return ${api-cast}(twoheap[pos]);
    }
  }
}
