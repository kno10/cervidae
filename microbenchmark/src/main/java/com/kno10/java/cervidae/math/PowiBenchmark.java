package com.kno10.java.cervidae.math;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

/**
 * Performance of pow(x,a) for integer a
 * 
 * @author Erich Schubert
 */
@Fork(10)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class PowiBenchmark {
  @Param({ "1000" })
  int size; // set automatically by framework

  @Param({ "2", "3", "4", "5", "7", "11", "13", "17" })
  int exponent; // set automatically by framework

  private double[] array; // set by us, in setUp()

  @Setup
  public void setUp() {
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
    if(p < 0) {
      return Math.pow(x, p);
    }
    double tmp = x, ret = 1.;
    for(int i = p; i > 0; i >>= 1) {
      if((i & 1) != 0) {
        ret *= tmp;
      }
      tmp *= tmp;
    }
    return ret;
  }

  /**
   * Fast loop for computing {@code Math.pow(x, p)} for p >= 0 integer.
   * 
   * @param x Base
   * @param p Exponent
   * @return {@code Math.pow(x, p)}
   */
  public static double powi2(double x, int p) {
    if(p < 2) {
      return (p == 1) ? x : (p == 2) ? x * x : Math.pow(x, p);
    }
    double tmp = x, ret = 1.;
    for(int i = p; i > 0; i >>= 1) {
      if((i & 1) != 0) {
        ret *= tmp;
      }
      tmp *= tmp;
    }
    return ret;
  }

  /**
   * Fast loop for computing {@code Math.pow(x, p)} for p >= 0 integer.
   * 
   * @param x Base
   * @param p Exponent
   * @return {@code Math.pow(x, p)}
   */
  public static double powi3(double x, int p) {
    if(p < 2) {
      return Math.pow(x, p);
    }
    double tmp = x, ret = 1.;
    for(int i = p; i > 0; i >>= 1) {
      if((i & 1) != 0) {
        ret *= tmp;
      }
      tmp *= tmp;
    }
    return ret;
  }

  /**
   * Fast loop for computing {@code Math.pow(x, p)} for p >= 0 integer.
   * 
   * @param x Base
   * @param p Exponent
   * @return {@code Math.pow(x, p)}
   */
  public static double powi4(double x, int p) {
    if(p < 2) {
      return Math.pow(x, p);
    }
    double tmp = x, ret = 1.;
    int i = p;
    while(true) {
      if(i == 1) {
        return ret * tmp;
      }
      if((i & 1) != 0) {
        ret *= tmp;
      }
      tmp *= tmp;
      i >>= 1;
    }
  }

  @Benchmark
  public double pow() {
    double ret = 0;
    for(int j = 0; j < array.length; j++) {
      ret += Math.pow(array[j], exponent);
    }
    return ret;
  }

  @Benchmark
  public double powi() {
    double ret = 0;
    for(int j = 0; j < array.length; j++) {
      ret += powi(array[j], exponent);
    }
    return ret;
  }

  @Benchmark
  public double powi2() {
    double ret = 0;
    for(int j = 0; j < array.length; j++) {
      ret += powi2(array[j], exponent);
    }
    return ret;
  }

  @Benchmark
  public double powi3() {
    double ret = 0;
    for(int j = 0; j < array.length; j++) {
      ret += powi3(array[j], exponent);
    }
    return ret;
  }

  @Benchmark
  public double powi4() {
    double ret = 0;
    for(int j = 0; j < array.length; j++) {
      ret += powi4(array[j], exponent);
    }
    return ret;
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder() //
    .include(PowiBenchmark.class.getSimpleName()) //
    .build();
    new Runner(opt).run();
  }
}
