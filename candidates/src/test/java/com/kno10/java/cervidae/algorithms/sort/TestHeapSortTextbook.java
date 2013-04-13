package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

/**
 * Unit test for textbook HeapSort.
 * 
 * @author Erich Schubert
 */
public class TestHeapSortTextbook extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    testSortingAlgorithm(HeapSortTextbook.STATIC, 100000, 0L);
  }
}
