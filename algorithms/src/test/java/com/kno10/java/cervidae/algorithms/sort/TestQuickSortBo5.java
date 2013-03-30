package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

import com.kno10.java.cervidae.controller.arraylike.DoubleArrayController;

/**
 * Unit test for QuickSort with best of 3 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestQuickSortBo5 extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    long seed = 0L;
    int size = 100000;
    double[] data = generateRandomDoubles(size, seed);
    QuickSortBo5.sort(DoubleArrayController.STATIC, data);    
    testSorted(data);
  }
}
