package com.kno10.java.cervidae.datastructures.heap24;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.kno10.java.cervidae.TestingUtils;

/**
 * Unit test the heap structures.
 * 
 * @author Erich Schubert
 */
public class TestHeap24 {
  /**
   * Size of testing data.
   */
  private static final int SIZE = 1000;

  /**
   * Random seed.
   */
  private static final long SEED = 0L;

  @Test
  public void testMinDoubles() {
    double[] data = TestingUtils.generateRandomDoubles(SIZE, SEED);
    DoubleMinHeap24 heap = new DoubleMinHeap24();

    for(int i = 0; i < SIZE; i++) {
      heap.add(data[i]);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during fill at pos " + i + ": " + check);
      }
    }

    double cur = Double.NEGATIVE_INFINITY;
    while(!heap.isEmpty()) {
      double next = heap.poll();
      assertTrue("Order inconsistent.", cur < next);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during poll: " + check);
      }
      cur = next;
    }
  }

  @Test
  public void testMaxDoubles() {
    double[] data = TestingUtils.generateRandomDoubles(SIZE, SEED);
    DoubleMaxHeap24 heap = new DoubleMaxHeap24();

    for(int i = 0; i < SIZE; i++) {
      heap.add(data[i]);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during fill at pos " + i + ": " + check);
      }
    }

    double cur = Double.POSITIVE_INFINITY;
    while(!heap.isEmpty()) {
      double next = heap.poll();
      assertTrue("Order inconsistent.", cur > next);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during poll: " + check);
      }
      cur = next;
    }
  }

  @Test
  public void testMinIntegers() {
    int[] data = TestingUtils.generateRandomIntegers(SIZE, SEED);
    IntegerMinHeap24 heap = new IntegerMinHeap24();

    for(int i = 0; i < SIZE; i++) {
      heap.add(data[i]);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during fill at pos " + i + ": " + check);
      }
    }

    int cur = Integer.MIN_VALUE;
    while(!heap.isEmpty()) {
      int next = heap.poll();
      assertTrue("Order inconsistent.", cur < next);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during poll: " + check);
      }
      cur = next;
    }
  }

  @Test
  public void testMaxIntegers() {
    int[] data = TestingUtils.generateRandomIntegers(SIZE, SEED);
    IntegerMaxHeap24 heap = new IntegerMaxHeap24();

    for(int i = 0; i < SIZE; i++) {
      heap.add(data[i]);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during fill at pos " + i + ": " + check);
      }
    }

    int cur = Integer.MAX_VALUE;
    while(!heap.isEmpty()) {
      int next = heap.poll();
      assertTrue("Order inconsistent.", cur > next);
      String check = heap.checkHeap();
      if(check != null) {
        fail("Inconsistent during poll: " + check);
      }
      cur = next;
    }
  }
}
