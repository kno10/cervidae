package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for QuickSort with best of 5 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestQuickSortBo5 extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(QuickSortBo5.STATIC, 100000, 0L);
  }
}
