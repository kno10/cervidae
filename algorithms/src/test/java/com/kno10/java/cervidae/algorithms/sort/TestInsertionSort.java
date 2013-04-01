package com.kno10.java.cervidae.algorithms.sort;

import org.junit.Test;

import com.kno10.java.cervidae.DoubleArrayUtil;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;

/**
 * Unit test for QuickSort with best of 3 heuristic.
 * 
 * @author Erich Schubert
 */
public class TestInsertionSort extends TestSortingAlgorithm {
  @Test
  public void testSort() {
    long seed = 0L;
    int size = 1000; // insertion sort is really slow...
    double[] data = generateRandomDoubles(size, seed);
    InsertionSort.sort(DoubleArrayAdapter.STATIC, data);    
    testSorted(data);
  }
}
