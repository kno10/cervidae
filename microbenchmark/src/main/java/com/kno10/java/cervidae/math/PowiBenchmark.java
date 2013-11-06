package com.kno10.java.cervidae.math;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

/**
 * Performance of pow(x,a) for integer a
 * 
 * @author Erich Schubert
 */
public class PowiBenchmark extends Benchmark {
  @Param({ "100" })
  int size; // set automatically by framework

  @Param({ "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15" })
  int a; // set automatically by framework

  private double[] array; // set by us, in setUp()

  @Override
  protected void setUp() {
    array = SortBenchmarkUtil.generateRandomDoubleData(size, MacroPattern.NORMAL10D, 0, 0);
  }

  /**
   * Fast loop for computing {@code Math.pow(x, p)} for p >= 0 integer.
   * 
   * @param x Base
   * @param p Exponent
   * @return {@code Math.pow(x, p)}
   */
  public static double powi(double x, int p) {
    if (p < 0) {
      return Math.pow(x, p);
    }
    double tmp = x, ret = 1.;
    for (int i = p; i > 0; i >>= 1) {
      if ((i & 1) == 1) {
        ret *= tmp;
      }
      tmp *= tmp;
    }
    return ret;
  }

  public double timeMathPow(int reps) {
    double ret = 0;
    for (int i = 0; i < reps; i++) {
      for (int j = 0; j < array.length; j++) {
        ret += Math.pow(array[j], a);
      }
    }
    return ret;
  }

  public double timeMathUtilPowi(int reps) {
    double ret = 0;
    for (int i = 0; i < reps; i++) {
      for (int j = 0; j < array.length; j++) {
        ret += powi(array[j], a);
      }
    }
    return ret;
  }

  public static void main(String[] args) {
    CaliperMain.main(PowiBenchmark.class, args);
  }
}
