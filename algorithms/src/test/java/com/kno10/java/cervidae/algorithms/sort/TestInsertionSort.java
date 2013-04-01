package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for (slow) insertion sort.
 * 
 * @author Erich Schubert
 */
public class TestInsertionSort extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(InsertionSort.STATIC, 1000, 0L);
  }
}
