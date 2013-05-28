package com.kno10.java.cervidae.algorithms.select;

import org.junit.Test;

/**
 * Unit test for QuickSort with best of 3 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestQuickSelectBo3 extends TestSelectionAlgorithm {
  @Test
  public void testSort() {
    testSelectionAlgorithm(QuickSelectBo3.STATIC, 100000, 0L);
  }
}
