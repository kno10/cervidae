package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for QuickSort with best of 3 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestQuickSortBo3 extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(QuickSortBo3.STATIC, 100000, 0L);
  }
}
