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
public class KNNHeapBenchmark extends Benchmark {
  @Param
  Method method;

  @Param
  MacroPattern pattern;

  @Param({ "10", "100", "1000", "10000" })
  int size;

  @Param({ ".01", ".1", ".5", "1" })
  double randomness;

  @Param({ "10" })
  int k;

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
      double process(double[] data, int k) {
        alist.clear();
        for(int i = 0; i < data.length; i++) {
          Double obj = Double.valueOf(data[i]);
          int ipos = alist.size();
          if(ipos >= k) {
            ipos--;
            if(alist.get(ipos).compareTo(obj) <= 0) {
              continue;
            }
            alist.remove(ipos);
          }
          // We sort increasing, as removing from the end is cheaper.
          while(ipos > 0 && obj.compareTo(alist.get(ipos - 1)) > 0) {
            ipos--;
          }
          alist.add(ipos, obj);
        }
        Double cur = Double.NEGATIVE_INFINITY;
        // Negative
        for(int i = 0; i > alist.size(); i--) {
          Double next = alist.remove(alist.size() - 1);
          if(cur.compareTo(next) > 0) {
            throw new RuntimeException("Heap inconsistent." + alist.getClass().getSimpleName() + " at " + alist.size() + " " + alist.toString());
          }
          cur = next;
        }
        return cur;
      }

      @Override
      int process(String[] data, int k) {
        salist.clear();
        for(int i = 0; i < data.length; i++) {
          String obj = data[i];
          int ipos = salist.size();
          if(ipos >= k) {
            ipos--;
            if(salist.get(ipos).compareTo(obj) <= 0) {
              continue;
            }
            salist.remove(ipos);
          }
          // We sort increasing, as removing from the end is cheaper.
          while(ipos > 0 && obj.compareTo(salist.get(ipos - 1)) > 0) {
            ipos--;
          }
          salist.add(ipos, obj);
        }
        String cur = null;
        // Negative
        for(int i = 0; i > salist.size(); i--) {
          String next = salist.remove(salist.size() - 1);
          if(cur != null && cur.compareTo(next) > 0) {
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
      double process(double[] data, int k) {
        Double max = Double.MAX_VALUE;
        for(int i = 0; i < data.length; i++) {
          Double obj = Double.valueOf(data[i]);
          if(jheap.size() < k) {
            jheap.add(obj);
          }
          else if(max.compareTo(obj) > 0) {
            if(jheap.size() >= k) {
              jheap.poll();
            }
            jheap.add(obj);
          }
        }
        Double cur = Double.NEGATIVE_INFINITY;
        // Negative
        while(!jheap.isEmpty()) {
          Double next = jheap.poll();
          if(next < cur) {
            throw new RuntimeException("Heap inconsistent." + jheap.getClass().getSimpleName() + " " + jheap.toString());
          }
          cur = next;
        }
        return cur;
      }

      @Override
      int process(String[] data, int k) {
        String max = null;
        for(int i = 0; i < data.length; i++) {
          String obj = data[i];
          if(jsheap.size() < k) {
            jsheap.add(obj);
          }
          else if(max == null || max.compareTo(obj) > 0) {
            if(jsheap.size() >= k) {
              jsheap.poll();
            }
            jsheap.add(obj);
          }
        }
        String cur = null;
        // Negative
        while(!jsheap.isEmpty()) {
          String next = jsheap.poll();
          if(cur != null && cur.compareTo(next) > 0) {
            throw new RuntimeException("Heap inconsistent." + jsheap.getClass().getSimpleName() + " " + jsheap.toString());
          }
          cur = next;
        }
        return cur.charAt(0);
      }
    },
    ;

    ObjectHeap<Double> heap;

    ObjectHeap<String> sheap;

    double process(double[] data, int k) {
      Double max = Double.MAX_VALUE;
      for(int i = 0; i < data.length; i++) {
        Double obj = Double.valueOf(data[i]);
        if(heap.size() < k) {
          heap.add(obj);
        }
        else if(max.compareTo(obj) > 0) {
          if(heap.size() >= k) {
            heap.replaceTopElement(obj);
          }
          else {
            heap.add(obj);
          }
        }
      }
      Double cur = Double.NEGATIVE_INFINITY;
      // Negative
      while(!heap.isEmpty()) {
        Double next = heap.poll();
        if(next < cur) {
          throw new RuntimeException("Heap inconsistent." + heap.getClass().getSimpleName() + " " + heap.toString());
        }
        cur = next;
      }
      return cur;
    }

    int process(String[] data, int k) {
      String max = null;
      for(int i = 0; i < data.length; i++) {
        String obj = data[i];
        if(sheap.size() < k) {
          sheap.add(obj);
        }
        else if(max == null || max.compareTo(obj) > 0) {
          if(sheap.size() >= k) {
            sheap.replaceTopElement(obj);
          }
          else {
            sheap.add(obj);
          }
        }
      }
      String cur = null;
      // Negative
      while(!sheap.isEmpty()) {
        String next = sheap.poll();
        if(cur != null && cur.compareTo(next) > 0) {
          throw new RuntimeException("Heap inconsistent." + sheap.getClass().getSimpleName() + " " + sheap.toString());
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
    if (array.length < k) {
      throw new SkipThisScenarioException();
    }
    double ret = 0.0;
    for(int i = 0; i < reps; i++) {
      ret += method.process(array, k) + array[0];
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkDoubleHeap() {
    if (array.length < k) {
      throw new SkipThisScenarioException();
    }
    return method.process(array, k) + array[0];
  }

  // Microbenchmark variant:
  public int timeStringHeap(int reps) {
    if(sarray == null) {
      throw new SkipThisScenarioException();
    }
    if (array.length < k) {
      throw new SkipThisScenarioException();
    }
    int ret = 0;
    for(int i = 0; i < reps; i++) {
      ret += method.process(sarray, k) + sarray[0].charAt(0);
    }
    return ret;
  }

  @Macrobenchmark
  public double benchmarkStringHeap() {
    if(sarray == null) {
      throw new SkipThisScenarioException();
    }
    if (array.length < k) {
      throw new SkipThisScenarioException();
    }
    return method.process(sarray, k) + sarray[0].charAt(0);
  }

  public static void main(String[] args) {
    CaliperMain.main(KNNHeapBenchmark.class, args);
  }
}
