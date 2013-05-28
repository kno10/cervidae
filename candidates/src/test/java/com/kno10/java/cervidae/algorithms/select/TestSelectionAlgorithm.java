package com.kno10.java.cervidae.algorithms.select;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;

/**
 * Base class for testing selection algorithms.
 * 
 * @author Erich Schubert
 */
public abstract class TestSelectionAlgorithm {
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

  public static void testPivot(double[] data, int rank) {
    final double pivot = data[rank];
    for(int i = 0; i < rank; i++) {
      if(pivot < data[i]) {
        StringBuilder buf = new StringBuilder();
        buf.append("Array not pivotized at position ").append(i).append('\n');
        buf.append(pivot).append(" !>= ").append(data[i]).append('\n');
        fail(buf.toString());
      }
    }
    for(int i = rank; i < data.length; i++) {
      if(data[i] < pivot) {
        StringBuilder buf = new StringBuilder();
        buf.append("Array not pivotized at position ").append(i).append('\n');
        buf.append(pivot).append(" !<= ").append(data[i]).append('\n');
        fail(buf.toString());
      }
    }
  }

  public static void testSelectionAlgorithm(ArraySelectionAlgorithm alg, int size, long seed) {
    double[] data = generateRandomDoubles(size, seed);
    Random r = new Random(seed + 1);
    Double[] dobj = new Double[data.length];
    Object[] objs = new Object[data.length];
    for(int i = 0; i < data.length; i++) {
      dobj[i] = data[i];
      objs[i] = dobj[i];
    }
    // Test native doubles:
    int rank = r.nextInt(size);
    alg.select(DoubleArrayAdapter.STATIC, data, rank);
    testPivot(data, rank);
    // Test object Doubles in a Double[] array:
    alg.select(new ComparableArrayAdapter<Double, Double>(), dobj, rank);
    for(int i = 0; i < rank; i++) {
      assertTrue("Array not sorted.", dobj[i] <= dobj[rank]);
    }
    for(int i = rank + 1; i < dobj.length; i++) {
      assertTrue("Array not sorted.", dobj[rank] <= dobj[i]);
    }
    // Test object Doubles in an Object[] array:
    alg.select(new ComparableArrayAdapter<Double, Object>(), objs, rank);
    for(int i = 0; i < rank; i++) {
      assertTrue("Array not sorted.", (Double) objs[i] <= (Double) objs[rank]);
    }
    for(int i = rank + 1; i < objs.length; i++) {
      assertTrue("Array not sorted.", (Double) objs[rank] <= (Double) objs[i]);
    }
  }
}
