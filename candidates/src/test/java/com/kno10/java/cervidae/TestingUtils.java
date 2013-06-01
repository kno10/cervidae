package com.kno10.java.cervidae;

import java.util.Random;

/**
 * Class with utility functionality shared across classes.
 * 
 * @author Erich Schubert
 */
public final class TestingUtils {
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

  /**
   * Generate an array of random numbers.
   * 
   * @param size Desired size.
   * @param seed Random seed.
   * @return Array of random numbers.
   */
  public static int[] generateRandomIntegers(int size, long seed) {
    Random r = new Random(seed);
    int[] ret = new int[size];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = r.nextInt();
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
  public static int[] generateSemiSortedIntegers(int size, double randomness, long seed) {
    Random r = new Random(seed);
    int[] ret = new int[size];
    for(int i = 0; i < ret.length; i++) {
      ret[i] = (int) (Integer.MAX_VALUE * (randomness * r.nextDouble() + i / (double) size));
    }
    return ret;
  }

  /**
   * Constructor. DO NOT INSTANTIATE.
   */
  private TestingUtils() {
    // do not instantiate
  }
}
