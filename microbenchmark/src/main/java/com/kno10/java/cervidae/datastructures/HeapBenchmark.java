package com.kno10.java.cervidae.datastructures;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;
import com.kno10.java.cervidae.datastructures.heap2.DoubleMinHeap2;
import com.kno10.java.cervidae.datastructures.heap2.IntegerMinHeap2;
import com.kno10.java.cervidae.datastructures.heap24.DoubleMinHeap24;
import com.kno10.java.cervidae.datastructures.heap24.IntegerMinHeap24;
import com.kno10.java.cervidae.datastructures.heap3.DoubleMinHeap3;
import com.kno10.java.cervidae.datastructures.heap3.DoubleMinHeap3Loop;
import com.kno10.java.cervidae.datastructures.heap3.IntegerMinHeap3;
import com.kno10.java.cervidae.datastructures.heap3.IntegerMinHeap3Loop;
import com.kno10.java.cervidae.datastructures.heap4.DoubleMinHeap4;
import com.kno10.java.cervidae.datastructures.heap4.IntegerMinHeap4;
import com.kno10.java.cervidae.datastructures.heap4.DoubleMinHeap4Loop;
import com.kno10.java.cervidae.datastructures.heap4.IntegerMinHeap4Loop;
import com.kno10.java.cervidae.datastructures.heap5.DoubleMinHeap5;
import com.kno10.java.cervidae.datastructures.heap5.DoubleMinHeap5Loop;
import com.kno10.java.cervidae.datastructures.heap5.IntegerMinHeap5;
import com.kno10.java.cervidae.datastructures.heap5.IntegerMinHeap5Loop;

/**
 * Benchmark class for basic heaps.
 * 
 * @author Erich Schubert
 */
public class HeapBenchmark extends Benchmark {
  @Param({ "10", "100", "1000", "10000" })
  int size;

  @Param({ ".01", ".1", ".5", "1" })
  double randomness;

  @Param
  Method method;

  @Param
  MacroPattern pattern;

  enum Method {
    HEAP2 {
      {
        dheap = new DoubleMinHeap2();
        iheap = new IntegerMinHeap2();
      }
    },
    HEAP24 {
      {
        dheap = new DoubleMinHeap24();
        iheap = new IntegerMinHeap24();
      }
    },
    HEAP3 {
      {
        dheap = new DoubleMinHeap3();
        iheap = new IntegerMinHeap3();
      }
    },
    HEAP4 {
      {
        dheap = new DoubleMinHeap4();
        iheap = new IntegerMinHeap4();
      }
    },
    HEAP5 {
      {
        dheap = new DoubleMinHeap5();
        iheap = new IntegerMinHeap5();
      }
    },
    HEAP3L {
      {
        dheap = new DoubleMinHeap3Loop();
        iheap = new IntegerMinHeap3Loop();
      }
    },
    HEAP4L {
      {
        dheap = new DoubleMinHeap4Loop();
        iheap = new IntegerMinHeap4Loop();
      }
    },
    HEAP5L {
      {
        dheap = new DoubleMinHeap5Loop();
        iheap = new IntegerMinHeap5Loop();
      }
    },
    ;

    DoubleHeap dheap;

    IntegerHeap iheap;

    double sort(double[] data) {
      for(double d : data) {
        dheap.add(d);
      }
      double cur = dheap.peek();
      while(!dheap.isEmpty()) {
        double next = dheap.poll();
        if(next < cur) {
          throw new RuntimeException("Heap inconsistent." + dheap.getClass().getSimpleName() + " at " + dheap.size() + " " + dheap.toString());
        }
        cur = next;
      }
      return cur;
    }

    int sort(int[] data) {
      for(int d : data) {
        iheap.add(d);
      }
      int cur = iheap.peek();
      while(!iheap.isEmpty()) {
        int next = iheap.poll();
        if(next < cur) {
          throw new RuntimeException("Heap inconsistent: " + iheap.getClass().getSimpleName() + " at " + iheap.size() + " " + iheap.toString());
        }
        cur = next;
      }
      return cur;
    }
  }

  /**
   * The unsorted data.
   */
  double[] array;

  int[] iarray;

  @Override
  protected void setUp() {
    array = SortBenchmarkUtil.generateRandomDoubleData(size, pattern, randomness, 0L);
    iarray = SortBenchmarkUtil.generateRandomIntegerData(size, pattern, randomness, 0L);
  }

  // Microbenchmark variant:
  public double timeDoubleHeap(int reps) {
    double ret = 0.0;
    for(int i = 0; i < reps; i++) {
      double[] tmp = array.clone();
      ret += method.sort(tmp) + tmp[0];
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkDoubleHeap() {
    double[] tmp = array.clone();
    return method.sort(tmp) + tmp[0];
  }

  // Microbenchmark variant:
  public double timeIntegerHeap(int reps) {
    int ret = 0;
    for(int i = 0; i < reps; i++) {
      int[] tmp = iarray.clone();
      ret ^= method.sort(tmp) ^ tmp[0];
    }
    return ret;
  }

  @Macrobenchmark
  public int benchmarkIntegerHeap() {
    int[] tmp = iarray.clone();
    return method.sort(tmp) + tmp[0];
  }

  public static void main(String[] args) {
    CaliperMain.main(HeapBenchmark.class, args);
  }
}
