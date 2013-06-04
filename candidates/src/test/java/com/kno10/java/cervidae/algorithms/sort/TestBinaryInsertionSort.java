package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for (slow) insertion sort.
 * 
 * @author Erich Schubert
 */
public class TestBinaryInsertionSort extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(BinaryInsertionSort.STATIC, 1000, 0L);
  }
}
