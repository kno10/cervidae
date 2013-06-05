package com.kno10.java.cervidae.datastructures;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.api.SkipThisScenarioException;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;
import com.kno10.java.cervidae.datastructures.HeapBenchmarkUtil.Workload;
import com.kno10.java.cervidae.datastructures.heap2.ComparableMinHeap2;
import com.kno10.java.cervidae.datastructures.heap24.ComparableMinHeap24;
import com.kno10.java.cervidae.datastructures.heap3.ComparableMinHeap3;
import com.kno10.java.cervidae.datastructures.heap3.ComparableMinHeap3Loop;
import com.kno10.java.cervidae.datastructures.heap4.ComparableMinHeap4;
import com.kno10.java.cervidae.datastructures.heap4.ComparableMinHeap4Loop;
import com.kno10.java.cervidae.datastructures.heap5.ComparableMinHeap5;
import com.kno10.java.cervidae.datastructures.heap5.ComparableMinHeap5Loop;

/**
 * Benchmark class for basic heaps.
 * 
 * @author Erich Schubert
 */
public class ObjectHeapBenchmark extends Benchmark {
  @Param
  Method method;

  @Param
  MacroPattern pattern;

  @Param({ "10", "100", "1000", "10000" })
  int size;

  @Param({ ".01", ".1", ".5", "1" })
  double randomness;

  @Param
  Workload workload;

  enum Method {
    HEAP2 {
      {
        heap = new ComparableMinHeap2<>();
        sheap = new ComparableMinHeap2<>();
      }
    },
    HEAP24 {
      {
        heap = new ComparableMinHeap24<>();
        sheap = new ComparableMinHeap24<>();
      }
    },
    HEAP3 {
      {
        heap = new ComparableMinHeap3<>();
        sheap = new ComparableMinHeap3<>();
      }
    },
    HEAP4 {
      {
        heap = new ComparableMinHeap4<>();
        sheap = new ComparableMinHeap4<>();
      }
    },
    HEAP5 {
      {
        heap = new ComparableMinHeap5<>();
        sheap = new ComparableMinHeap5<>();
      }
    },
    HEAP3L {
      {
        heap = new ComparableMinHeap3Loop<>();
        sheap = new ComparableMinHeap3Loop<>();
      }
    },
    HEAP4L {
      {
        heap = new ComparableMinHeap4Loop<>();
        sheap = new ComparableMinHeap4Loop<>();
      }
    },
    HEAP5L {
      {
        heap = new ComparableMinHeap5Loop<>();
        sheap = new ComparableMinHeap5Loop<>();
      }
    },
    ARRAYINSERT {
      ArrayList<Double> alist = new ArrayList<>();

      ArrayList<String> salist = new ArrayList<>();

      @Override
      double process(double[] data, Workload workload) {
        final int numbatches = workload.numBatches(data.length);
        alist.clear();
        int pos = 0;
        Double cur = Double.NEGATIVE_INFINITY;
        for (int b = 0; b < numbatches; b++) {
          final int batchsize = workload.batchSize(b, data.length);
          if (batchsize > 0) {
            for (int i = 0; i < batchsize; i++, pos++) {
              // We sort increasing, as removing from the end is cheaper.
              Double o = Double.valueOf(data[pos]);
              int ipos = alist.size();
              while (ipos > 0 && o.compareTo(alist.get(ipos - 1)) > 0) {
                ipos--;
              }
              alist.add(ipos, o);
            }
          } else {
            cur = null;
            // Negative
            for (int i = 0; i > batchsize; i--) {
              Double next = alist.remove(alist.size() - 1);
              if (cur != null && next < cur) {
                throw new RuntimeException("Heap inconsistent." + alist.getClass().getSimpleName() + " at " + alist.size() + " " + alist.toString());
              }
              cur = next;
            }
          }
        }
        return cur;
      }

      @Override
      int process(String[] data, Workload workload) {
        final int numbatches = workload.numBatches(data.length);
        salist.clear();
        int pos = 0;
        String cur = null;
        for (int b = 0; b < numbatches; b++) {
          final int batchsize = workload.batchSize(b, data.length);
          if (batchsize > 0) {
            for (int i = 0; i < batchsize; i++, pos++) {
              // We sort increasing, as removing from the end is cheaper.
              int ipos = salist.size();
              while (ipos > 0 && data[pos].compareTo(salist.get(ipos - 1)) > 0) {
                ipos--;
              }
              salist.add(ipos, data[pos]);
            }
          } else {
            cur = null;
            // Negative
            for (int i = 0; i > batchsize; i--) {
              String next = salist.remove(salist.size() - 1);
              if (cur != null && cur.compareTo(next) > 0) {
                throw new RuntimeException("Heap inconsistent." + salist.getClass().getSimpleName() + " at " + salist.size() + " " + salist.toString());
              }
              cur = next;
            }
          }
        }
        return cur.charAt(0);
      }
    },
    JAVA {
      PriorityQueue<Double> jheap = new PriorityQueue<>();

      PriorityQueue<String> jsheap = new PriorityQueue<>();

      @Override
      double process(double[] data, Workload workload) {
        final int numbatches = workload.numBatches(data.length);
        int pos = 0;
        Double cur = Double.NaN;
        for (int b = 0; b < numbatches; b++) {
          final int batchsize = workload.batchSize(b, data.length);
          if (batchsize > 0) {
            for (int i = 0; i < batchsize; i++, pos++) {
              jheap.add(Double.valueOf(data[pos]));
            }
          } else {
            cur = Double.NEGATIVE_INFINITY;
            // Negative
            for (int i = 0; i > batchsize; i--) {
              Double next = jheap.poll();
              if (next < cur) {
                throw new RuntimeException("Heap inconsistent." + jheap.getClass().getSimpleName() + " at " + pos + "/" + jheap.size() + " " + jheap.toString());
              }
              cur = next;
            }
          }
        }
        jheap.clear();
        return cur;
      }

      @Override
      int process(String[] data, Workload workload) {
        final int numbatches = workload.numBatches(data.length);
        int pos = 0;
        String cur = null;
        for (int b = 0; b < numbatches; b++) {
          final int batchsize = workload.batchSize(b, data.length);
          if (batchsize > 0) {
            for (int i = 0; i < batchsize; i++, pos++) {
              jsheap.add(data[pos]);
            }
          } else {
            cur = null;
            // Negative
            for (int i = 0; i > batchsize; i--) {
              String next = jsheap.poll();
              if (cur != null && cur.compareTo(next) > 0) {
                throw new RuntimeException("Heap inconsistent." + jsheap.getClass().getSimpleName() + " at " + pos + "/" + jsheap.size() + " " + jsheap.toString());
              }
              cur = next;
            }
          }
        }
        jsheap.clear();
        return cur.charAt(0);
      }
    },
    ;

    ObjectHeap<Double> heap;

    ObjectHeap<String> sheap;

    double process(double[] data, Workload workload) {
      final int numbatches = workload.numBatches(data.length);
      int pos = 0;
      Double cur = Double.NaN;
      for (int b = 0; b < numbatches; b++) {
        final int batchsize = workload.batchSize(b, data.length);
        if (batchsize > 0) {
          for (int i = 0; i < batchsize; i++, pos++) {
            heap.add(Double.valueOf(data[pos]));
          }
        } else {
          cur = Double.NEGATIVE_INFINITY;
          // Negative
          for (int i = 0; i > batchsize; i--) {
            Double next = heap.poll();
            if (next < cur) {
              throw new RuntimeException("Heap inconsistent." + heap.getClass().getSimpleName() + " at " + pos + "/" + heap.size() + " " + heap.toString());
            }
            cur = next;
          }
        }
      }
      heap.clear();
      return cur;
    }

    int process(String[] data, Workload workload) {
      final int numbatches = workload.numBatches(data.length);
      int pos = 0;
      String cur = null;
      for (int b = 0; b < numbatches; b++) {
        final int batchsize = workload.batchSize(b, data.length);
        if (batchsize > 0) {
          for (int i = 0; i < batchsize; i++, pos++) {
            sheap.add(data[pos]);
          }
        } else {
          cur = null;
          // Negative
          for (int i = 0; i > batchsize; i--) {
            String next = sheap.poll();
            if (cur != null && cur.compareTo(next) > 0) {
              throw new RuntimeException("Heap inconsistent." + sheap.getClass().getSimpleName() + " at " + pos + "/" + sheap.size() + " " + sheap.toString());
            }
            cur = next;
          }
        }
      }
      sheap.clear();
      return cur.charAt(0);
    }
  }

  /**
   * The unsorted data.
   */
  double[] array;

  String[] sarray;

  @Override
  protected void setUp() {
    array = SortBenchmarkUtil.generateRandomDoubleData(size, pattern, randomness, 0L);
    sarray = SortBenchmarkUtil.generateRandomStringData(size, pattern, randomness, 0L);
  }

  // Microbenchmark variant:
  public double timeDoubleHeap(int reps) {
    double ret = 0.0;
    for (int i = 0; i < reps; i++) {
      ret += method.process(array, workload) + array[0];
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkDoubleHeap() {
    return method.process(array, workload) + array[0];
  }

  // Microbenchmark variant:
  public int timeStringHeap(int reps) {
    if (sarray == null) {
      throw new SkipThisScenarioException();
    }
    int ret = 0;
    for (int i = 0; i < reps; i++) {
      ret += method.process(sarray, workload) + sarray[0].charAt(0);
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkStringHeap() {
    if (sarray == null) {
      throw new SkipThisScenarioException();
    }
    return method.process(sarray, workload) + sarray[0].charAt(0);
  }

  public static void main(String[] args) {
    CaliperMain.main(ObjectHeapBenchmark.class, args);
  }
}
