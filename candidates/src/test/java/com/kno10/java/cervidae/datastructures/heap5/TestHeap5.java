package com.kno10.java.cervidae.datastructures.heap5;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.kno10.java.cervidae.TestingUtils;

/**
 * Unit test the heap structures.
 * 
 * @author Erich Schubert
 */
public class TestHeap5 {
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
    DoubleMinHeap5 heap = new DoubleMinHeap5();

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
    DoubleMaxHeap5 heap = new DoubleMaxHeap5();

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
    IntegerMinHeap5 heap = new IntegerMinHeap5();

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
    IntegerMaxHeap5 heap = new IntegerMaxHeap5();

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
