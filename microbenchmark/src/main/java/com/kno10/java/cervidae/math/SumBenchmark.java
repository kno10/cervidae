package com.kno10.java.cervidae.math;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

@Fork(1)
@Warmup(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SumBenchmark {
  @Param({ "1000000" })
  int size; // set automatically by framework

  private double[] array; // set by us, in setUp()

  @Setup
  public void setUp() {
    array = SortBenchmarkUtil.generateRandomDoubleData(size, MacroPattern.NORMAL10D, 0, 0);
  }

  @Benchmark
  public double forSum() {
    double sum = 0.;
    for(int i = 0; i < array.length; ++i) {
      sum += array[i];
    }
    return sum;
  }

  @Benchmark
  public double forSumL() {
    final int l = array.length;
    double sum = 0.;
    for(int i = 0; i < l; ++i) {
      sum += array[i];
    }
    return sum;
  }

  @Benchmark
  public double forSumL2() {
    double sum = 0.;
    // Non-final length:
    for(int i = 0, l = array.length; i < l; ++i) {
      sum += array[i];
    }
    return sum;
  }

  @Benchmark
  public double forSumR() {
    double sum = 0.;
    for(int i = array.length - 1; i >= 0; --i) {
      sum += array[i];
    }
    return sum;
  }

  @Benchmark
  public double whileSum() {
    double sum = 0.;
    int i = 0;
    while(i < array.length) {
      sum += array[i++];
    }
    return sum;
  }

  @Benchmark
  public double whileSumL() {
    final int l = array.length;
    double sum = 0.;
    int i = 0;
    while(i < l) {
      sum += array[i++];
    }
    return sum;
  }

  @Benchmark
  public double foreachSum() {
    double sum = 0.;
    for(double v : array) {
      sum += v;
    }
    return sum;
  }

  // This one is expected to be significantly slower, because it accounts for
  // some rounding errors, i.e. has higher precision
  @Benchmark
  public double streamsSum() {
    return Arrays.stream(array).sum();
  }

  @Benchmark
  public double lambdaSum() {
    double[] sum = new double[1];
    Arrays.stream(array).forEach(x -> sum[0] += x);
    return sum[0];
  }

  @Benchmark
  public double adapterFor() {
    DoubleArrayAdapter adapter = DoubleArrayAdapter.STATIC;
    double sum = 0.;
    for(int i = 0; i < adapter.length(array); ++i) {
      sum += adapter.get(array, i);
    }
    return sum;
  }

  @Benchmark
  public double adapterForL() {
    DoubleArrayAdapter adapter = DoubleArrayAdapter.STATIC;
    final int size = adapter.length(array);
    double sum = 0.;
    for(int i = 0; i < size; ++i) {
      sum += adapter.get(array, i);
    }
    return sum;
  }

  @Benchmark
  public double adapterForR() {
    DoubleArrayAdapter adapter = DoubleArrayAdapter.STATIC;
    final int size = adapter.length(array);
    double sum = 0.;
    for(int i = size - 1; i >= 0; --i) {
      sum += adapter.get(array, i);
    }
    return sum;
  }

  @Benchmark
  public double adapterWhile() {
    DoubleArrayAdapter adapter = DoubleArrayAdapter.STATIC;
    double sum = 0.;
    int i = 0;
    while(i < adapter.length(array)) {
      sum += adapter.get(array, i++);
    }
    return sum;
  }

  @Benchmark
  public double adapterWhileL() {
    DoubleArrayAdapter adapter = DoubleArrayAdapter.STATIC;
    final int size = adapter.length(array);
    double sum = 0.;
    int i = 0;
    while(i < size) {
      sum += adapter.get(array, i++);
    }
    return sum;
  }

  @Benchmark
  public double boxedFor() {
    Array array = new NaiveArray(this.array);
    double sum = 0.;
    for(int i = 0; i < array.length(); ++i) {
      sum += array.get(i);
    }
    return sum;
  }

  @Benchmark
  public double boxedForL() {
    Array array = new NaiveArray(this.array);
    final int size = array.length();
    double sum = 0.;
    for(int i = 0; i < size; ++i) {
      sum += array.get(i);
    }
    return sum;
  }

  @Benchmark
  public double boxedForR() {
    Array array = new NaiveArray(this.array);
    double sum = 0.;
    for(int i = array.length() - 1; i >= 0; --i) {
      sum += array.get(i);
    }
    return sum;
  }

  @Benchmark
  public double boxedWhile() {
    Array array = new NaiveArray(this.array);
    double sum = 0.;
    int i = 0;
    while(i < array.length()) {
      sum += array.get(i++);
    }
    return sum;
  }

  @Benchmark
  public double boxedWhileL() {
    Array array = new NaiveArray(this.array);
    final int size = array.length();
    double sum = 0.;
    int i = 0;
    while(i < size) {
      sum += array.get(i++);
    }
    return sum;
  }

  static interface Array {
    int length();

    double get(int pos);
  }

  static class NaiveArray implements Array {
    double[] array;

    public NaiveArray(double[] array) {
      this.array = array;
    }

    @Override
    public int length() {
      return array.length;
    }

    @Override
    public double get(int pos) {
      return array[pos];
    }
  }
}
