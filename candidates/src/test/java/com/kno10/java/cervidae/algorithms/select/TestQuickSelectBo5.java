package com.kno10.java.cervidae.algorithms.select;

import org.junit.Test;

/**
 * Unit test for QuickSort with best of 5 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestQuickSelectBo5 extends TestSelectionAlgorithm {
  @Test
  public void testSort() {
    testSelectionAlgorithm(QuickSelectBo5.STATIC, 100000, 0L);
  }
}
