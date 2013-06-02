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
    CONSTANT {
      @Override
      public double val(int pos, int length) {
        return 0;
      }
    },
    ;
    abstract public double val(int pos, int length);
  }

  public static double[] generateRandomDoubleData(int size, MacroPattern pattern, double randomness, long seed) {
    double[] array = new double[size];
    Random rnd = new Random(seed);
    for(int i = 0; i < size; i++) {
      array[i] = rnd.nextDouble() * randomness + (1. - randomness) * pattern.val(i, size);
    }
    return array;
  }

  public static int[] generateRandomIntegerData(int size, MacroPattern pattern, double randomness, long seed) {
    int[] array = new int[size];
    Random rnd = new Random(seed);
    for(int i = 0; i < size; i++) {
      array[i] = (int) (Integer.MAX_VALUE * (rnd.nextDouble() * randomness + (1. - randomness) * pattern.val(i, size)));
    }
    return array;
  }

  // Character range. Safe ASCII 7-bit is enough.
  private final static int FIRST_CHAR = 33, LAST_CHAR = 128;

  public static String[] generateRandomStringData(int size, MacroPattern pattern, double randomness, long seed) {
    String[] array = new String[size];
    Random rnd = new Random(seed);
    for(int i = 0; i < size; i++) {
      final int len = 5 + rnd.nextInt(11); // 5-15 chars.
      final char[] buf = new char[len];
      for(int j = 0; j < len; j++) {
        buf[j] = (char) (FIRST_CHAR + (LAST_CHAR - FIRST_CHAR) * (rnd.nextDouble() * randomness + (1. - randomness) * pattern.val(i, size)));
      }
      array[i] = new String(buf);
    }
    return array;
  }
}
