package com.kno10.java.cervidae.algorithms.sort;

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;

/**
 * Sorting a <b>fixed</b> size of elements, using an optimized sorting network.
 * 
 * See e.g. Knuth for details on optimal sorting networks.
 * 
 * @author Erich Schubert
 */
public class SortingNetworks {
  /**
   * Explicit (and optimal) sorting network for 5 elements.
   * 
   * See Knuth for details.
   * 
   * @param control Controller
   * @param data Data array
   * @param m1 Position of first
   * @param m2 Position of second
   * @param m3 Position of third
   * @param m4 Position of fifth
   * @param m5 Position of fourth
   */
  public static <T> void sort5(ArraySortAdapter<T> control, T data, final int m1, final int m2, final int m3, final int m4, final int m5) {
    if (control.greaterThan(data, m1, m4)) {
      control.swap(data, m1, m4);
    }
    //
    if (control.greaterThan(data, m1, m3)) {
      control.swap(data, m1, m3);
    }
    if (control.greaterThan(data, m2, m5)) {
      control.swap(data, m2, m5);
    }
    //
    if (control.greaterThan(data, m2, m4)) {
      control.swap(data, m2, m4);
    }
    if (control.greaterThan(data, m3, m5)) {
      control.swap(data, m3, m5);
    }
    //
    if (control.greaterThan(data, m1, m2)) {
      control.swap(data, m1, m2);
    }
    if (control.greaterThan(data, m3, m4)) {
      control.swap(data, m3, m4);
    }
    //
    if (control.greaterThan(data, m2, m3)) {
      control.swap(data, m2, m3);
    }
    if (control.greaterThan(data, m4, m5)) {
      control.swap(data, m4, m5);
    }
  }
}
