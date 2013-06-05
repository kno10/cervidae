package com.kno10.java.cervidae.algorithms.sort5;

import java.util.Arrays;
import java.util.Random;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.algorithms.sort.DoubleArrayQuickSort;
import com.kno10.java.cervidae.algorithms.sort.IntegerArrayQuickSort;
import com.kno10.java.cervidae.comparator.PrimitiveComparators;

/**
 * Benchmark class for sorting algorithms.
 * 
 * @author Erich Schubert
 */
public class Sort5Benchmark extends Benchmark {
  @Param
  Method method;

  @Param
  Pattern pattern;

  enum Pattern {
    RANDOM, SORTED, REVERSE, EQUAL
  }

  enum Method {
    NETWORK {
      {
        dsort = new DoubleNetworkSort5();
        isort = new IntegerNetworkSort5();
      }
    },
    INSERTIONA {
      {
        dsort = new DoubleInsertionSort5A();
        isort = new IntegerInsertionSort5A();
      }
    },
    INSERTIONB {
      {
        dsort = new DoubleInsertionSort5B();
        isort = new IntegerInsertionSort5B();
      }
    },
    INSERTIONC {
      {
        dsort = new DoubleInsertionSort5C();
        isort = new IntegerInsertionSort5C();
      }
    },
    INSERTIOND {
      {
        dsort = new DoubleInsertionSort5D();
        isort = new IntegerInsertionSort5D();
      }
    },
    INSERTIONE {
      {
        dsort = new DoubleInsertionSort5E();
        isort = new IntegerInsertionSort5E();
      }
    },
    INSERTIONF {
      {
        dsort = new DoubleInsertionSort5F();
        isort = new IntegerInsertionSort5F();
      }
    },
    INSERTIONG {
      {
        dsort = new DoubleInsertionSort5G();
        isort = new IntegerInsertionSort5G();
      }
    },
    INSERTIONH {
      {
        dsort = new DoubleInsertionSort5H();
        isort = new IntegerInsertionSort5H();
      }
    },
    DECISIONA {
      {
        dsort = new DoubleDecisionSort5A();
        isort = new IntegerDecisionSort5A();
      }
    },
    DECISIONB {
      {
        dsort = new DoubleDecisionSort5B();
        isort = new IntegerDecisionSort5B();
      }
    },
    BUBBLEA {
      {
        dsort = new DoubleBubbleSort5A();
        isort = new IntegerBubbleSort5A();
      }
    },
    BUBBLEB {
      {
        dsort = new DoubleBubbleSort5B();
        isort = new IntegerBubbleSort5B();
      }
    },
    JAVA { // This one is actually not just for 5 elements...
      @Override
      void sort(double[] data) {
        Arrays.sort(data);
      }

      @Override
      void sort(int[] data) {
        Arrays.sort(data);
      }
    },
    CERVIDAE { // This one is actually not just for 5 elements...
      @Override
      void sort(double[] data) {
        DoubleArrayQuickSort.STATIC.sort(data, 0, 5, PrimitiveComparators.DOUBLE_ASCENDING);
      }

      @Override
      void sort(int[] data) {
        IntegerArrayQuickSort.STATIC.sort(data, 0, 5, PrimitiveComparators.INTEGER_ASCENDING);
      }
    },
    FINSERTA { // This one is actually not just for 5 elements...
      @Override
      void sort(double[] data) {
        DoubleArrayQuickSort.STATIC.insertionSortAscending(data, 0, 5);
      }

      @Override
      void sort(int[] data) {
        IntegerArrayQuickSort.STATIC.insertionSortAscending(data, 0, 5);
      }
    },
    FINSERTB { // This one is actually not just for 5 elements...
      @Override
      void sort(double[] data) {
        DoubleArrayQuickSort.STATIC.dualInsertionSort(data, 0, 5);
      }

      @Override
      void sort(int[] data) {
        IntegerArrayQuickSort.STATIC.dualInsertionSort(data, 0, 5);
      }
    },
    BINSERTIONA {
      {
        dsort = new DoubleBinaryInsertionSort5A();
        isort = new IntegerBinaryInsertionSort5A();
      }
    },
    BINSERTIONB {
      {
        dsort = new DoubleBinaryInsertionSort5B();
        isort = new IntegerBinaryInsertionSort5B();
      }
    },
    ;

    DoubleSort5 dsort;

    IntegerSort5 isort;

    void sort(double[] data) {
      dsort.sort5(data, 0, 1, 2, 3, 4);
    }

    void sort(int[] data) {
      isort.sort5(data, 0, 1, 2, 3, 4);
    }
  }

  /**
   * The unsorted data.
   */
  double[] array;

  int[] iarray;

  @Override
  protected void setUp() {
    array = new double[5];
    iarray = new int[5];
    switch(pattern) {
    case EQUAL:
      // Keep zero.
      break;
    case RANDOM:
      Random rnd = new Random();
      for (int i = 0; i < 5; i++) {
        array[i] = rnd.nextDouble();
        iarray[i] = rnd.nextInt();
      }
      break;
    case REVERSE:
      for (int i = 0; i < 5; i++) {
        array[i] = 5 - i;
        iarray[i] = 5 - i;
      }
      break;
    case SORTED:
      for (int i = 0; i < 5; i++) {
        array[i] = i;
        iarray[i] = i;
      }
      break;
    default:
      throw new RuntimeException("Unknown pattern.");
    }
  }

  public double timeDoubleSort5(int reps) {
    double ret = 0.0;
    for (int i = 0; i < reps; i++) {
      double[] tmp = array.clone();
      method.sort(tmp);
      if (tmp[0] > tmp[1] || tmp[1] > tmp[2] || tmp[2] > tmp[3] || tmp[3] > tmp[4]) {
        throw new RuntimeException("Sorting algorithm failed: " + method + " on pattern: " + pattern);
      }
      ret += tmp[0] + tmp[tmp.length - 1];
    }
    return ret;
  }

  public double timeIntegerSort5(int reps) {
    int ret = 0;
    for (int i = 0; i < reps; i++) {
      int[] tmp = iarray.clone();
      method.sort(tmp);
      if (tmp[0] > tmp[1] || tmp[1] > tmp[2] || tmp[2] > tmp[3] || tmp[3] > tmp[4]) {
        throw new RuntimeException("Sorting algorithm failed: " + method + " on pattern: " + pattern);
      }
      ret ^= tmp[0] ^ tmp[tmp.length - 1];
    }
    return ret;
  }

  public static void main(String[] args) {
    CaliperMain.main(Sort5Benchmark.class, args);
  }
}
