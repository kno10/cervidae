package com.kno10.java.cervidae.algorithms.select;

import java.util.Random;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

/**
 * Benchmark class for sorting algorithms.
 * 
 * @author Erich Schubert
 */
public class SelectBenchmark extends Benchmark {
  @Param({ "10", "100", "1000", "10000" })
  int size;

  @Param({ ".01", ".1", ".5", "1" })
  double randomness;

  static final String BASE = "com.kno10.java.cervidae.algorithms.select.";

  @Param({ //
  BASE + "QuickSelectBo5", //
  BASE + "QuickSelectBo3", //
  BASE + "QuickSelectTextbook",//
  })
  String algname;

  ArraySelectionAlgorithm alg;

  @Param({ "INCREASING", "DECREASING", "SAW8", "DECINC", "CONSTANT" })
  MacroPattern pattern;

  /**
   * The unsorted data.
   */
  double[] array;

  /**
   * The rank to select.
   */
  int rank;

  @Override
  protected void setUp() {
    try {
      alg = (ArraySelectionAlgorithm) Class.forName(algname).newInstance();
    }
    catch(Exception e) {
      System.err.println("Exception loading algorithm: " + algname + ": " + e);
      e.printStackTrace(System.err);
      throw new RuntimeException("Exception loading algorithm: " + algname, e);
    }
    // @Param values are guaranteed to have been injected by now
    array = SortBenchmarkUtil.generateRandomData(size, pattern, randomness, 0L);
    rank = (new Random(1L)).nextInt(size);
  }

  @Macrobenchmark
  public double benchmarkSelectPrimitiveDoubles() {
    double ret = 0.0;
    // for (int i = 0; i < reps; i++) {
    double[] tmp = array.clone();
    alg.select(DoubleArrayAdapter.STATIC, tmp, rank);
    ret += tmp[rank];
    // }
    return ret;
  }

  // Microbenchmarking variant:
  public double timeSelectPrimitiveDoubles(int reps) {
    double ret = 0.0;
    for(int i = 0; i < reps; i++) {
      ret += benchmarkSelectPrimitiveDoubles();
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkSelectObjects() {
    double ret = 0.0;
    // for (int i = 0; i < reps; i++) {
    Double[] tmp = new Double[array.length];
    for(int j = 0; j < array.length; j++) {
      tmp[j] = array[j];
    }
    alg.select(new ComparableArrayAdapter<>(), tmp, rank);
    ret += tmp[rank];
    // }
    return ret;
  }

  // Microbenchmarking variant:
  public double timeSelectObjects(int reps) {
    double ret = 0.0;
    for(int i = 0; i < reps; i++) {
      ret += benchmarkSelectObjects();
    }
    return ret;
  }

  public static void main(String[] args) {
    CaliperMain.main(SelectBenchmark.class, args);
  }
}
