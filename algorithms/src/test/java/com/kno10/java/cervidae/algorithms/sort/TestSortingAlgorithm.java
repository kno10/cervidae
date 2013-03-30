package com.kno10.java.cervidae.algorithms.sort;

import java.util.Random;
import static org.junit.Assert.*;

/**
 * Base class for testing sorting algorithms.
 * 
 * @author Erich Schubert
 */
public abstract class TestSortingAlgorithm {
  /**
   * Generate an array of random numbers.
   * 
   * @param size Desired size.
   * @param seed Random seed.
   * @return Array of random numbers.
   */
  public double[] generateRandomDoubles(int size, long seed) {
    Random r = new Random(seed);
    double[] ret = new double[size];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = r.nextDouble();
    }
    return ret;
  }

  /**
   * Generate a mostly-sorted data array.
   * 
   * @param size Desired array size.
   * @param randomness Desired randomness.
   * @param seed Random seed.
   * @return Unsorted array.
   */
  public double[] generateSemiSortedDoubles(int size, double randomness, long seed) {
    Random r = new Random(seed);
    double[] ret = new double[size];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = randomness * r.nextDouble() + i / (double) size;
    }
    return ret;
  }

  public void testSorted(double[] data) {
    double current = data[0];
    for(int i = 1; i < data.length; i++) {
      assertTrue("Array not sorted at position " + i + " " + current + " !<= " + data[i], current <= data[i]);
      current = data[i];
    }
  }
}
