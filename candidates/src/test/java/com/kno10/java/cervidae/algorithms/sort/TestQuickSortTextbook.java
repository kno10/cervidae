package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for textbook QuickSort.
 * 
 * @author Erich Schubert
 */
public class TestQuickSortTextbook extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(QuickSortTextbook.STATIC, 100000, 0L);
  }
}
