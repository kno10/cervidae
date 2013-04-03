package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for slightly optimized bidirectional BubbleSort.
 * 
 * @author Erich Schubert
 */
public class TestBidirectionalBubbleSort extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(BidirectionalBubbleSort.STATIC, 100, 0L);
  }
}
