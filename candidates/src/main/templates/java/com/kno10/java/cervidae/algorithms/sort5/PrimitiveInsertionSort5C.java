package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Optimized methods to sort <b>exactly</b> five elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}InsertionSort5C implements ${Type}Sort5 {
  /**
   * Sort the elements at the given five positions using an explicit insertion sort.
   * If we can cache elements (and not just swap), this can be faster than the explicit sorting network.
   * 
   * Note: for this to make sense, {@code d1 < d2 < d3 < d4 < d5} should hold!
   * 
   * This variant avoids the else cases by using extra assignments.
   * 
   * @param data Data array
   * @param d1 Position of first
   * @param d2 Position of second
   * @param d3 Position of third
   * @param d4 Position of fifth
   * @param d5 Position of fourth
   */
  @Override
  public void sort5(${atype}[] data, int d1, int d2, int d3, int d4, int d5) {
    // Insert second
    if (data[d1] > data[d2]) {
      final ${type} tmp = data[d2];
      data[d2] = data[d1];
      data[d1] = tmp;
    }
    // Insert third
    if (data[d2] > data[d3]) {
      final ${type} tmp = data[d3];
      data[d3] = data[d2];
      data[d2] = tmp;
      if (data[d1] > tmp) {
        data[d2] = data[d1];
        data[d1] = tmp;
      }
    }
    // Insert fourth
    insertFour(data, d1, d2, d3, d4, data[d4]);
    // Insert fifth
    if (data[d4] > data[d5]) {
      final ${type} tmp = data[d5];
      data[d5] = data[d4];
      data[d4] = tmp;
      insertFour(data, d1, d2, d3, d4, tmp);
    }
  }

  private static void insertFour(${atype}[] data, int d1, int d2, int d3, int d4, ${atype} tmp) {
    if (data[d3] > tmp) {
      data[d4] = data[d3];
      data[d3] = tmp;
      if (data[d2] > tmp) {
        data[d3] = data[d2];
        data[d2] = tmp;
        if (data[d1] > tmp) {
          data[d2] = data[d1];
          data[d1] = tmp;
        }
      }
    }
  } 
}
