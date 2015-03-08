package com.kno10.java.cervidae.math;

import java.util.Arrays;
import java.util.Random;
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

/**
 * Benchmark to measure the performance of different algorithms to compute the
 * mean of an array. This may sound trivial, but if you want both a fast and
 * accurate algorithm, you have to be careful.
 * 
 * From the initial analysis, Kahan summation will do a good job for sum and
 * mean. Welford suffers more from cancellation; but it may pay off when you
 * need to compute variance and higher order moments. Plus, the two can be
 * combined.
 * 
 * @author Erich Schubert
 */
@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MeanBenchmark {
  @Param({ "100", "10000", "1000000" })
  int size; // set automatically by framework

  private double[] array; // set by us, in setUp()

  public enum Variant {//
    NAIVE_SUM { // The naive, but lowest precision method
      @Override
      double mean(double[] array) {
        double sum = 0.;
        int c = 0;
        for(int j = 0; j < array.length; j++) {
          sum += array[j];
          c++;
        }
        return sum / c;
      }
    },
    KAHAN { // Kahan summation, more robust against certain patterns
      // (repeated
      // small increments lost due to rounding.)
      @Override
      double mean(double[] array) {
        double sum = 0., err = 0.;
        int c = 0;
        for(int j = 0; j < array.length; j++) {
          final double v = array[j] + err;
          final double tmp = sum + v; // may lose some precision
          err = v - (tmp - sum); // Compute loss
          sum = tmp;
          c++;
        }
        return sum / c;
      }
    },
    WELFORD_I { // Incremental, as discussed by knuth-welford
      @Override
      double mean(double[] array) {
        double mean = 0.;
        int c = 0;
        for(int j = 0; j < array.length; j++) {
          mean += (array[j] - mean) / ++c;
        }
        return mean;
      }
    },
    WELFORD_D { // Incremental, as discussed by knuth-welford; supports
      // weighted observations.
      @Override
      double mean(double[] array) {
        double mean = 0.;
        double c = 0.;
        for(int j = 0; j < array.length; j++) {
          c += 1.;
          mean += (array[j] - mean) / c;
        }
        return mean;
      }
    },
    WEL_KAH { // Combination of welford and kahan summation.
      @Override
      double mean(double[] array) {
        double mean = 0., err = 0.;
        double c = 0.;
        for(int j = 0; j < array.length; j++) {
          c += 1.;
          {
            final double delta = (array[j] - mean) / c + err;
            final double tmp = mean + delta; // may lose some
            // precision
            err = delta - (tmp - mean); // Compute loss
            mean = tmp;
          }
        }
        return mean;
      }
    },
    WEL_KAH2 { // Combination of welford and double kahan summation.
      @Override
      double mean(double[] array) {
        double mean = 0., err = 0.;
        double c = 0., cerr = 0.;
        for(int j = 0; j < array.length; j++) {
          {
            final double inc = 1. + cerr;
            final double tmp = c + inc;
            cerr = inc - (tmp - c);
            c = tmp;
          }
          {
            final double delta = (array[j] - mean) / c + err;
            final double tmp = mean + delta; // may lose some
            // precision
            err = delta - (tmp - mean); // Compute loss
            mean = tmp;
          }
        }
        return mean;
      }
    },
    STREAM {
      @Override
      double mean(double[] array) {
        return Arrays.stream(array).average().getAsDouble();
      }
    };
    abstract double mean(double[] array);
  };

  public enum Pattern {
    CONSTANT {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        for(int i = 0; i < size; i++) {
          array[i] = 1.23456789012345;
        }
        return array;
      }

      @Override
      double getMean() {
        return 1.23456789012345;
      }

      @Override
      double getErrorScale(int size) {
        return 1e-15 * size;
      }
    },
    ASCENDING {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        for(int i = 0; i < size; i++) {
          array[i] = (1e15 * (i + 1)) / (size + 1);
        }
        return array;
      }

      @Override
      double getMean() {
        return .5e15;
      }

      @Override
      double getErrorScale(int size) {
        return 1. * size;
      }
    },
    DESCENDING {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        for(int i = 0; i < size; i++) {
          array[i] = (1e15 * (size - i)) / (size + 1);
        }
        return array;
      }

      @Override
      double getMean() {
        return .5e15;
      }

      @Override
      double getErrorScale(int size) {
        return 1. * size;
      }
    },
    DOWNUP {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        int halfsize = size >> 1;
        for(int i = 0; i < halfsize; i++) {
          array[i] = (1e15 * (halfsize - i)) / (halfsize + 1);
          array[size - 1 - i] = 1e15 - array[i];
        }
        return array;
      }

      @Override
      double getMean() {
        return .5e15;
      }

      @Override
      double getErrorScale(int size) {
        return 1. * size;
      }
    },
    UPDOWN {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        int halfsize = size >> 1;
        for(int i = 0; i < halfsize; i++) {
          array[i] = (1e15 * (i + 1)) / (halfsize + 1);
          array[size - 1 - i] = 1e15 - array[i];
        }
        return array;
      }

      @Override
      double getMean() {
        return .5e15;
      }

      @Override
      double getErrorScale(int size) {
        return 1. * size;
      }
    },
    GAUSSIAN {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        Random r = new Random(0);
        double stddev = 1e10;
        for(int i = 0; i < size; i++) {
          double v = r.nextGaussian() * stddev;
          array[i] = v;
        }
        return array;
      }

      @Override
      double getMean() {
        return 0;
      }

      @Override
      double getErrorScale(int size) {
        // Expected variance decreases with size.
        return 1e10 * Math.sqrt(size);
      }
    },
    GAUSSIAN_BIASED_E15 {
      @Override
      double[] generate(int size) {
        double[] array = new double[size];
        Random r = new Random(0);
        double mean = 1e15, stddev = 1e5;
        for(int i = 0; i < size; i++) {
          double v = r.nextGaussian() * stddev;
          array[i] = mean + v;
        }
        return array;
      }

      @Override
      double getMean() {
        return 1e15;
      }

      @Override
      double getErrorScale(int size) {
        // Expected variance decreases with size.
        return 1e5 * Math.sqrt(size);
      }
    },
    ;
    /** Generate the data set */
    abstract double[] generate(int size);

    /** Expected mean value; approximate! */
    abstract double getMean();

    /** Expected scale of errors, for normalization */
    abstract double getErrorScale(int size);
  }

  @Param
  Variant variant;

  @Param
  Pattern pattern;

  @Setup
  public void setUp() {
    array = pattern.generate(size);
  }

  @Benchmark
  public double benchmarkDoubleMean() {
    return variant.mean(array);
  }

  public double meanError() {
    return Math.abs(variant.mean(array) - pattern.getMean()) * size / pattern.getErrorScale(size);
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder() //
    .include(MeanBenchmark.class.getSimpleName()) //
    .build();
    new Runner(opt).run();
  }
}
