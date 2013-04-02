package com.kno10.java.cervidae.algorithms.sort;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Random;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;

/**
 * Benchmark class for sorting algorithms.
 * 
 * @author Erich Schubert
 */
public class SortThresholdBenchmark extends Benchmark {
  @Param({ "10000" })
  int size;

  @Param({ ".01", ".1", ".5", "1" })
  double randomness;

  static final String BASE = "com.kno10.java.cervidae.algorithms.sort.";
  
  @Param({"10", "20", "30", "40", "50"})
  int threshold;

  @Param({ //
  BASE + "DualPivotQuickSortBo5", //
  BASE + "QuickSortBo5", //
  BASE + "QuickSortBo3", //
  })
  String algname;

  ArraySortAlgorithm alg;

  /**
   * The unsorted data.
   */
  double[] array;

  @Override
  protected void setUp() {
    try {
      Constructor<?> con = Class.forName(algname).getConstructor(int.class);
      alg = (ArraySortAlgorithm) con.newInstance(threshold);
    } catch (Exception e) {
      System.err.println("Exception loading algorithm: " + algname + ": " + e);
      e.printStackTrace(System.err);
      throw new RuntimeException("Exception loading algorithm: " + algname, e);
    }
    // @Param values are guaranteed to have been injected by now
    array = new double[size];
    Random rnd = new Random(0);
    for (int i = 0; i < size; i++) {
      array[i] = rnd.nextDouble() * randomness + i * (1. - randomness) / size;
    }
  }

  public double timeSortPrimitiveDoubles(int reps) {
    double ret = 0.0;
    for (int i = 0; i < reps; i++) {
      double[] tmp = array.clone();
      alg.sort(DoubleArrayAdapter.STATIC, tmp);
      ret += tmp[0] + tmp[tmp.length - 1];
    }
    return ret;
  }

  public double timeSortObjects(int reps) {
    double ret = 0.0;
    for (int i = 0; i < reps; i++) {
      Double[] tmp = new Double[array.length];
      for (int j = 0; j < array.length; j++) {
        tmp[j] = array[j];
      }
      alg.sort(new ComparableArrayAdapter<>(), tmp);
      ret += tmp[0] + tmp[tmp.length - 1];
    }
    return ret;
  }

  public static class JavaSort extends AbstractArraySortAlgorithm {
    @Override
    public <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end) {
      if (adapter instanceof DoubleArrayAdapter) {
        Arrays.sort((double[]) data, start, end);
      } else if (adapter instanceof ComparableArrayAdapter) {
        Arrays.sort((Object[]) data, start, end);
      } else {
        throw new RuntimeException("Can't sort with official Java classes.");
      }
    }
  }

  public static void main(String[] args) {
    CaliperMain.main(SortThresholdBenchmark.class, args);
  }
}
