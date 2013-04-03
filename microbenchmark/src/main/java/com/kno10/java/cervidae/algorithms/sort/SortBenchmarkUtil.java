package com.kno10.java.cervidae.algorithms.sort;

import java.util.Random;

public final class SortBenchmarkUtil {
  /**
   * Data generation patterns.
   * 
   * @author Erich Schubert
   */
  public static enum MacroPattern {
    INCREASING {
      @Override
      public double val(int pos, int length) {
        return pos / (double) length;
      }
    },
    DECREASING {
      @Override
      public double val(int pos, int length) {
        return (length - pos) / (double) length;
      }
    },
    DECINC {
      // Decreasing first, increasing then.
      @Override
      public double val(int pos, int length) {
        return Math.abs((length >> 1) - pos) / (double) (length >> 1);
      }
    },
    SAW8 {
      @Override
      public double val(int pos, int length) {
        return (pos % (length >> 3)) / (double) (length >> 3);
      }
    },
    ;
    abstract public double val(int pos, int length);
  }

  public static double[] generateRandomData(int size, MacroPattern pattern, double randomness, long seed) {
    double[] array = new double[size];
    Random rnd = new Random(seed);
    for (int i = 0; i < size; i++) {
      array[i] = rnd.nextDouble() * randomness + (1. - randomness) * pattern.val(i, size);
    }
    return array;
  }
}
