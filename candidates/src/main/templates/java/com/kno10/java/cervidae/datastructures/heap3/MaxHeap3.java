package com.kno10.java.cervidae.datastructures.heap3;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

import com.kno10.java.cervidae.datastructures.${parent-Type}Heap;

/**
 * Priority queue, based on a 3-ary heap.
 * 
 * This code was automatically instantiated for the type: ${Type}
 * 
 * @author Erich Schubert
 * ${generics-documentation}
 */
public class ${Type}MaxHeap3${def-generics} implements ${parent-Type}Heap${use-generics} {
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
   * Initial size of 3-ary heap when initialized.
   * 
   * 13 = 3-ary heap of height 2: 1 + 3 + 3*3
   * 
   * 40 = 3-ary heap of height 3: 13 + 3*3*3
   * 
   * 221 = 3-ary heap of height 4: 40 + 3*3*3*3
   * 
   * Without further hints, start with 40 elements
   */
  private final static int THREE_HEAP_INITIAL_SIZE = 40;

  ${extra-fields}

  /**
   * Constructor, with default size.
   *
   * ${extra-constructor-documentation}
   */
  ${unchecked}
  public ${Type}MaxHeap3(${extra-constructor}) {
    super();
    ${extra-constructor-init}
    ${rawtype}[] heap = ${newarray,THREE_HEAP_INITIAL_SIZE};
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
  public ${Type}MaxHeap3(int minsize, ${extra-constructor}) {
    super();
    ${extra-constructor-init}
    // TODO: upscale to the next "optimal" size?
    ${rawtype}[] heap = ${newarray,size};
    this.heap = heap;
    this.size = 0;
    this.modCount = 0;
  }

  /**
   * Fast integer division by 3 operator. INCORRECT for negative values!
   * 
   * @param v Input value
   * @return v / 3, rounded down.
   */
  public static final int fastDiv3(int v) {
    return (int) ((v * 0xAAAAAAABL) >>> 33);
  }

  /**
   * Fast multiplicaton with 3 operator.
   * 
   * @param v Input value
   * @return v * 3
   */
  public static final int fastTimes3(int v) {
    return (v << 1) + v;
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
      // Grow by 50%
      heap = Arrays.copyOf(heap, heap.length + (heap.length >>> 1));
    }
    final int pos = size;
    ++size;
    heapifyUp3(pos, co);
    ++modCount;
  }

  @Override
  public void add(${api-type} key, int max) {
    if (size < max) {
      add(key);
    } else if (${compare,>=,heap[0],key}) {
      replaceTopElement(key);
    }
  }

  @Override
  ${unchecked}
  public ${api-type} replaceTopElement(${api-type} reinsert) {
    final ${rawtype} ret = heap[0];
    heapifyDown3(0, ${rawcast} reinsert);
    ++modCount;
    return ${api-cast}ret;
  }

  /**
   * Heapify-Up method for 3-ary heap.
   * 
   * @param pos Position in 3-ary heap.
   * @param cur Current object
   */
  private void heapifyUp3(int pos, ${rawtype} cur) {
    while (pos > 0) {
      final int parent = fastDiv3(pos - 1);
      ${rawtype} par = heap[parent];
      if (${compare,<=,cur,par}) {
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
      heapifyDown3(0, reinsert);
    } else {
      heap[0] = ${null};
    }
    ++modCount;
    return ${api-cast}ret;
  }

  /**
   * Heapify-Down for 3-ary heap.
   * 
   * @param pos Position in 3-ary heap.
   * @param cur Current object
   */
  private void heapifyDown3(int pos, ${rawtype} cur) {
    final int stop = fastDiv3(size + 1); // size - 2 + 3
    while (pos < stop) {
      int bestchild = fastTimes3(pos) + 1;
      ${rawtype} best = heap[bestchild];
      int candidate = bestchild + 1;
      if (candidate < size) {
        ${rawtype} nextchild = heap[candidate];
        if (${compare,<,best,nextchild}) {
          bestchild = candidate;
          best = nextchild;
        }

        if (candidate <= size) {
          candidate++;
          nextchild = heap[candidate];
          if (${compare,<,best,nextchild}) {
            bestchild = candidate;
            best = nextchild;
          }
        }
      }
      if (${compare,>=,cur,best}) {
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
    buf.append(${Type}MaxHeap3.class.getSimpleName()).append(" [");
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
      final int parent = fastDiv3(i - 1);
      if (${compare,<,heap[parent],heap[i]}) {
        return "@" + parent + ": " + heap[parent] + " < @" + i + ": " + heap[i];
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
