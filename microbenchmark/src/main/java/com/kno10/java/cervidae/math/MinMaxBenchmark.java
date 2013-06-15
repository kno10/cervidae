package com.kno10.java.cervidae.math;

import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.runner.CaliperMain;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

public class MinMaxBenchmark extends Benchmark {
  @Param({ "100", "10000" })
  int size; // set automatically by framework

  private double[] array; // set by us, in setUp()

  private int[] iarray; // set by us, in setUp()

  enum Variant {//
    MATH_MAX, //
    IF, IF2, //
    IF3, IF4, //
    TERNARY, TERNARY2,
  };

  @Param
  Variant variant;

  @Override
  protected void setUp() {
    array = SortBenchmarkUtil.generateRandomDoubleData(size, MacroPattern.NORMAL10D, 0, 0);
    iarray = SortBenchmarkUtil.generateRandomIntegerData(size, MacroPattern.CONSTANT, 1, 0);
  }

  public double timeDoubleMax(int reps) {
    double ret = 0;
    for(int i = 0; i < reps; i++) {
      double max = Double.NEGATIVE_INFINITY;
      switch(variant){
      case MATH_MAX:
        for(int j = 0; j < size; j++) {
          max = Math.max(ret, max);
        }
        break;
      case IF:
        for(int j = 0; j < size; j++) {
          if(array[j] > max) {
            max = array[j];
          }
        }
        break;
      case IF2:
        for(int j = 0; j < size; j++) {
          final double loc = array[j];
          if(loc > max) {
            max = loc;
          }
        }
        break;
      case IF3:
        for(int j = 0; j < size; j++) {
          if(array[j] > max) {
            max = array[j];
          }
        }
        break;
      case IF4:
        for(int j = 0; j < size; j++) {
          final double loc = array[j];
          if(loc > max) {
            max = loc;
          }
        }
        break;
      case TERNARY:
        for(int j = 0; j < size; j++) {
          max = (array[j] > max) ? array[j] : max;
        }
        break;
      case TERNARY2:
        for(int j = 0; j < size; j++) {
          final double loc = array[j];
          max = (loc > max) ? loc : max;
        }
        break;
      }
      ret += max;
    }
    return ret;
  }

  public double timeDoubleMinMax(int reps) {
    double ret = 0;
    for(int i = 0; i < reps; i++) {
      double min = Double.POSITIVE_INFINITY;
      double max = Double.NEGATIVE_INFINITY;
      switch(variant){
      case MATH_MAX:
        for(int j = 0; j < size; j++) {
          min = Math.min(ret, min);
          max = Math.max(ret, max);
        }
        break;
      case IF:
        for(int j = 0; j < size; j++) {
          if(array[j] < min) {
            min = array[j];
          }
          else if(array[j] > max) {
            max = array[j];
          }
        }
        break;
      case IF2:
        for(int j = 0; j < size; j++) {
          final double loc = array[j];
          if(loc < min) {
            min = loc;
          }
          else if(loc > max) {
            max = loc;
          }
        }
        break;
      case IF3:
        for(int j = 0; j < size; j++) {
          if(array[j] < min) {
            min = array[j];
          }
          if(array[j] > max) {
            max = array[j];
          }
        }
        break;
      case IF4:
        for(int j = 0; j < size; j++) {
          final double loc = array[j];
          if(loc < min) {
            min = loc;
          }
          if(loc > max) {
            max = loc;
          }
        }
        break;
      case TERNARY:
        for(int j = 0; j < size; j++) {
          min = (array[j] < min) ? array[j] : min;
          max = (array[j] > max) ? array[j] : max;
        }
        break;
      case TERNARY2:
        for(int j = 0; j < size; j++) {
          final double loc = array[j];
          min = (loc < min) ? loc : min;
          max = (loc > max) ? loc : max;
        }
        break;
      }
      ret += max;
    }
    return ret;
  }

  public int timeIntegerMax(int reps) {
    int ret = 0;
    for(int i = 0; i < reps; i++) {
      int max = Integer.MAX_VALUE;
      switch(variant){
      case MATH_MAX:
        for(int j = 0; j < size; j++) {
          max = Math.max(ret, max);
        }
        break;
      case IF:
        for(int j = 0; j < size; j++) {
          if(iarray[j] > max) {
            max = iarray[j];
          }
        }
        break;
      case IF2:
        for(int j = 0; j < size; j++) {
          final int loc = iarray[j];
          if(loc > max) {
            max = loc;
          }
        }
        break;
      case IF3:
        for(int j = 0; j < size; j++) {
          if(iarray[j] > max) {
            max = iarray[j];
          }
        }
        break;
      case IF4:
        for(int j = 0; j < size; j++) {
          final int loc = iarray[j];
          if(loc > max) {
            max = loc;
          }
        }
        break;
      case TERNARY:
        for(int j = 0; j < size; j++) {
          max = (iarray[j] > max) ? iarray[j] : max;
        }
        break;
      case TERNARY2:
        for(int j = 0; j < size; j++) {
          final int loc = iarray[j];
          max = (loc > max) ? loc : max;
        }
        break;
      }
      ret += max;
    }
    return ret;
  }

  public int timeIntegerMinMax(int reps) {
    int ret = 0;
    for(int i = 0; i < reps; i++) {
      int min = Integer.MIN_VALUE;
      int max = Integer.MAX_VALUE;
      switch(variant){
      case MATH_MAX:
        for(int j = 0; j < size; j++) {
          min = Math.min(ret, min);
          max = Math.max(ret, max);
        }
        break;
      case IF:
        for(int j = 0; j < size; j++) {
          if(iarray[j] < min) {
            min = iarray[j];
          }
          else if(iarray[j] > max) {
            max = iarray[j];
          }
        }
        break;
      case IF2:
        for(int j = 0; j < size; j++) {
          final int loc = iarray[j];
          if(loc < min) {
            min = loc;
          }
          else if(loc > max) {
            max = loc;
          }
        }
        break;
      case IF3:
        for(int j = 0; j < size; j++) {
          if(iarray[j] < min) {
            min = iarray[j];
          }
          if(iarray[j] > max) {
            max = iarray[j];
          }
        }
        break;
      case IF4:
        for(int j = 0; j < size; j++) {
          final int loc = iarray[j];
          if(loc < min) {
            min = loc;
          }
          if(loc > max) {
            max = loc;
          }
        }
        break;
      case TERNARY:
        for(int j = 0; j < size; j++) {
          min = (iarray[j] < min) ? iarray[j] : min;
          max = (iarray[j] > max) ? iarray[j] : max;
        }
        break;
      case TERNARY2:
        for(int j = 0; j < size; j++) {
          final int loc = iarray[j];
          min = (loc < min) ? loc : min;
          max = (loc > max) ? loc : max;
        }
        break;
      }
      ret += max;
    }
    return ret;
  }

  public static void main(String[] args) {
    CaliperMain.main(MinMaxBenchmark.class, args);
  }
}
