package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

import com.kno10.java.cervidae.controller.arraylike.DoubleArrayController;

/**
 * Unit test for textbook QuickSort.
 * 
 * @author Erich Schubert
 */
public class TestQuickSortTextbook extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    long seed = 0L;
    int size = 100000;
    double[] data = generateRandomDoubles(size, seed);
    QuickSortTextbook.sort(DoubleArrayController.STATIC, data);    
    testSorted(data);
  }
}
