package com.kno10.java.cervidae.algorithms.sort;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;

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
  public static double[] generateRandomDoubles(int size, long seed) {
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
  public static double[] generateSemiSortedDoubles(int size, double randomness, long seed) {
    Random r = new Random(seed);
    double[] ret = new double[size];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = randomness * r.nextDouble() + i / (double) size;
    }
    return ret;
  }

  public static void testSorted(double[] data) {
    double current = data[0];
    for(int i = 1; i < data.length; i++) {
      assertTrue("Array not sorted at position " + i + " " + current + " !<= " + data[i], current <= data[i]);
      current = data[i];
    }
  }

  public static void testSortingAlgorithm(ArraySortAlgorithm alg, int size, long seed) {
    double[] data = generateRandomDoubles(size, seed);
    Double[] dobj = new Double[data.length];
    Object[] objs = new Object[data.length];
    for(int i = 0; i < data.length; i++) {
      dobj[i] = data[i];
      objs[i] = dobj[i];
    }
    // Test native doubles:
    alg.sort(DoubleArrayAdapter.STATIC, data);
    testSorted(data);
    // Test object Doubles in a Double[] array:
    alg.sort(new ComparableArrayAdapter<Double, Double>(), dobj);
    for(int i = 1; i < dobj.length; i++) {
      assertTrue("Array not sorted.", dobj[i - 1] <= dobj[i]);
    }
    // Test object Doubles in an Object[] array:
    alg.sort(new ComparableArrayAdapter<Double, Object>(), objs);
    for(int i = 1; i < objs.length; i++) {
      assertTrue("Array not sorted.", (Double) objs[i - 1] <= (Double) objs[i]);
    }
  }
}
