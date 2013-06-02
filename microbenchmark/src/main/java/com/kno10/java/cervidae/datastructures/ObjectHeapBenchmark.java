package com.kno10.java.cervidae.datastructures;

import java.util.ArrayList;
import java.util.PriorityQueue;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.Macrobenchmark;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;
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
      double sort(double[] data) {
        alist.clear();
        // We sort increasing, as removing from the end is cheaper.
        for(double d : data) {
          Double o = Double.valueOf(d);
          int pos = alist.size();
          while(pos > 0 && o.compareTo(alist.get(pos - 1)) > 0) {
            pos--;
          }
          alist.add(pos, o);
        }
        Double cur = alist.get(alist.size() - 1);
        while(!alist.isEmpty()) {
          Double next = alist.remove(alist.size() - 1);
          if(next < cur) {
            throw new RuntimeException("Heap inconsistent." + alist.getClass().getSimpleName() + " at " + alist.size() + " " + alist.toString());
          }
          cur = next;
        }
        return cur;
      }

      @Override
      int sort(String[] data) {
        salist.clear();
        // We sort increasing, as removing from the end is cheaper.
        for(String d : data) {
          int pos = salist.size();
          while(pos > 0 && d.compareTo(salist.get(pos - 1)) > 0) {
            pos--;
          }
          salist.add(pos, d);
        }
        String cur = salist.get(salist.size() - 1);
        while(!salist.isEmpty()) {
          String next = salist.remove(salist.size() - 1);
          if(cur.compareTo(next) > 0) {
            throw new RuntimeException("Heap inconsistent." + salist.getClass().getSimpleName() + " at " + salist.size() + " " + salist.toString());
          }
          cur = next;
        }
        return cur.charAt(0);
      }
    },
    JAVA {
      PriorityQueue<Double> jheap = new PriorityQueue<>();

      PriorityQueue<String> jsheap = new PriorityQueue<>();

      @Override
      double sort(double[] data) {
        jheap.clear();
        for(double d : data) {
          jheap.add(Double.valueOf(d));
        }
        Double cur = jheap.peek();
        while(!jheap.isEmpty()) {
          Double next = jheap.poll();
          if(next < cur) {
            throw new RuntimeException("Heap inconsistent." + jheap.getClass().getSimpleName() + " at " + jheap.size() + " " + jheap.toString());
          }
          cur = next;
        }
        return cur;
      }

      @Override
      int sort(String[] data) {
        jsheap.clear();
        for(String d : data) {
          jsheap.add(d);
        }
        String cur = jsheap.peek();
        while(!jsheap.isEmpty()) {
          String next = jsheap.poll();
          if(cur.compareTo(next) > 0) {
            throw new RuntimeException("Heap inconsistent." + jsheap.getClass().getSimpleName() + " at " + jsheap.size() + " " + jsheap.toString());
          }
          cur = next;
        }
        return cur.charAt(0);
      }
    },
    ;

    ObjectHeap<Double> heap;

    ObjectHeap<String> sheap;

    double sort(double[] data) {
      heap.clear();
      for(double d : data) {
        heap.add(Double.valueOf(d));
      }
      Double cur = heap.peek();
      while(!heap.isEmpty()) {
        Double next = heap.poll();
        if(next < cur) {
          throw new RuntimeException("Heap inconsistent." + heap.getClass().getSimpleName() + " at " + heap.size() + " " + heap.toString());
        }
        cur = next;
      }
      return cur;
    }

    int sort(String[] data) {
      sheap.clear();
      for(String d : data) {
        sheap.add(d);
      }
      String cur = sheap.peek();
      while(!sheap.isEmpty()) {
        String next = sheap.poll();
        if(cur.compareTo(next) > 0) {
          throw new RuntimeException("Heap inconsistent." + sheap.getClass().getSimpleName() + " at " + sheap.size() + " " + sheap.toString());
        }
        cur = next;
      }
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
  public int timeStringHeap(int reps) {
    int ret = 0;
    for(int i = 0; i < reps; i++) {
      String[] tmp = sarray.clone();
      ret += method.sort(tmp) + tmp[0].charAt(0);
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkStringHeap() {
    String[] tmp = sarray.clone();
    return method.sort(tmp) + tmp[0].charAt(0);
  }

  public static void main(String[] args) {
    CaliperMain.main(ObjectHeapBenchmark.class, args);
  }
}
