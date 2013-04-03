package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for slightly optimized BubbleSort.
 * 
 * @author Erich Schubert
 */
public class TestBubbleSort extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(BubbleSort.STATIC, 10000, 0L);
  }
}
