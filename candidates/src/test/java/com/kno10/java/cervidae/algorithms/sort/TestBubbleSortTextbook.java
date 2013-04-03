package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for textbook BubbleSort.
 * 
 * @author Erich Schubert
 */
public class TestBubbleSortTextbook extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(BubbleSortTextbook.STATIC, 10000, 0L);
  }
}
