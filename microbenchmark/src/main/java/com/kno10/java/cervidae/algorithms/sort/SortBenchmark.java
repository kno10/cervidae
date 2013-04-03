package com.kno10.java.cervidae.algorithms.sort;

import java.util.Arrays;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;
import com.kno10.java.cervidae.adapter.comparator.DoubleComparator;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

/**
 * Benchmark class for sorting algorithms.
 * 
 * @author Erich Schubert
 */
public class SortBenchmark extends Benchmark {
  @Param({ "10", "100", "1000", "10000" })
  int size;

  @Param({ ".01", ".1", ".5", "1" })
  double randomness;

  static final String BASE = "com.kno10.java.cervidae.algorithms.sort.";

  @Param({ //
  BASE + "DualPivotQuickSortBo5", //
  BASE + "QuickSortBo5", //
  BASE + "QuickSortBo3", //
  BASE + "QuickSortTextbook",//
  // We already know that these three are much slower.
  BASE + "BubbleSort", //
  BASE + "BubbleSortTextbook", //
  BASE + "BidirectionalBubbleSort", //
  BASE + "InsertionSort", //
  BASE + "SortBenchmark$JavaSort", //
  BASE + "SortBenchmark$OptimizedSort", //
  })
  String algname;

  ArraySortAlgorithm alg;

  @Param({ "INCREASING", "DECREASING", "SAW8" })
  MacroPattern pattern;

  /**
   * The unsorted data.
   */
  double[] array;

  @Override
  protected void setUp() {
    try {
      alg = (ArraySortAlgorithm) Class.forName(algname).newInstance();
    } catch (Exception e) {
      System.err.println("Exception loading algorithm: " + algname + ": " + e);
      e.printStackTrace(System.err);
      throw new RuntimeException("Exception loading algorithm: " + algname, e);
    }
    // @Param values are guaranteed to have been injected by now
    array = SortBenchmarkUtil.generateRandomData(size, pattern, randomness, 0L);
  }

  @Macrobenchmark
  public double benchmarkSortPrimitiveDoubles() {
    double ret = 0.0;
    // for (int i = 0; i < reps; i++) {
    double[] tmp = array.clone();
    alg.sort(DoubleArrayAdapter.STATIC, tmp);
    ret += tmp[0] + tmp[tmp.length - 1];
    // }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkSortObjects() {
    double ret = 0.0;
    // for (int i = 0; i < reps; i++) {
    Double[] tmp = new Double[array.length];
    for (int j = 0; j < array.length; j++) {
      tmp[j] = array[j];
    }
    alg.sort(new ComparableArrayAdapter<>(), tmp);
    ret += tmp[0] + tmp[tmp.length - 1];
    // }
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

  public static class OptimizedSort extends AbstractArraySortAlgorithm {
    @Override
    public <T> void sort(ArraySortAdapter<? super T> adapter, T data, int start, int end) {
      if (adapter instanceof DoubleArrayAdapter) {
        DoubleArrayQuickSort.sort((double[]) data, start, end, new DoubleComparator() {
          @Override
          public int compare(double arg0, double arg1) {
            return Double.compare(arg0, arg1);
          }
        });
      } else {
        DualPivotQuickSortBo5.STATIC.sort(adapter, data, start, end);
      }
    }
  }

  public static void main(String[] args) {
    CaliperMain.main(SortBenchmark.class, args);
  }
}
