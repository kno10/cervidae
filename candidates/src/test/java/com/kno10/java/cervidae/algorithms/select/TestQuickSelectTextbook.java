package com.kno10.java.cervidae.algorithms.select;

import org.junit.Test;

/**
 * Unit test for textbook QuickSort.
 * 
 * @author Erich Schubert
 */
public class TestQuickSelectTextbook extends TestSelectionAlgorithm {
  @Test
  public void testSort() {
    testSelectionAlgorithm(QuickSelectTextbook.STATIC, 100000, 0L);
  }
}
