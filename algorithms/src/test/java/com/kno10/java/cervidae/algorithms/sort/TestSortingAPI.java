package com.kno10.java.cervidae.algorithms.sort;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import com.kno10.java.cervidae.controller.arraylike.ComparableArrayController;
import com.kno10.java.cervidae.controller.arraylike.DoubleArrayController;

public class TestSortingAPI {
  final int size = 10000;

  @Test
  public void testPrimtiveDouble() {
    double[] data = new double[size];
    Random rnd = new Random(0L);
    for (int i = 0; i < data.length; i++) {
      data[i] = rnd.nextDouble();
    }
    
    DualPivotQuickSortBo5.sort(DoubleArrayController.STATIC, data);
    
    TestSortingAlgorithm.testSorted(data);
  }

  @Test
  public void testObjectDouble() {
    Double[] data = new Double[size];
    Random rnd = new Random(0L);
    for (int i = 0; i < data.length; i++) {
      data[i] = rnd.nextDouble();
    }
    
    DualPivotQuickSortBo5.sort(new ComparableArrayController<Double, Double>(), data);
    
    for (int i = 1; i < data.length; i++) {
      assertTrue("Array not sorted.", data[i - 1] <= data[i]);
    }
  }


  @Test
  public void testObjectDoubleObject() {
    Object[] data = new Object[size];
    Random rnd = new Random(0L);
    for (int i = 0; i < data.length; i++) {
      data[i] = rnd.nextDouble();
    }
    
    DualPivotQuickSortBo5.sort(new ComparableArrayController<Double, Object>(), data);
    
    for (int i = 1; i < data.length; i++) {
      assertTrue("Array not sorted.", (Double)data[i - 1] <= (Double)data[i]);
    }
  }
}
