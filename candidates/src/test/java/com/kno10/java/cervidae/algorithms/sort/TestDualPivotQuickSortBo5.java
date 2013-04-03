package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for QuickSort with best of 3 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestDualPivotQuickSortBo5 extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(DualPivotQuickSortBo5.STATIC, 100000, 0L);
  }
}
