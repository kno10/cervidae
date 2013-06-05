package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Optimized methods to sort <b>exactly</b> five elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}BinaryInsertionSort5A implements ${Type}Sort5 {
  /**
   * Five element explicit dual insertion sort, using a binary search
   * for finding the insertion position of the 4th and 5th elements.
   * 
   * Note: for this to make sense, {@code d1 < d2 < d3 < d4 < d5} should hold!
   * 
   * This version avoids else cases with extra writes.
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
    ${type} tmp;
    // Insert 2
    tmp = data[d2];
    if (data[d1] > tmp) {
      data[d2] = data[d1];
      data[d1] = tmp;
    }
    // Insert 3
    tmp = data[d3];
    if (data[d2] > tmp) {
      data[d3] = data[d2];
      data[d2] = tmp;
    }
    if (data[d1] > tmp) {
      data[d2] = data[d1];
      data[d1] = tmp;
    }
    // Insert 4
    tmp = data[d4];
    if (data[d2] > tmp) {
      data[d4] = data[d3];
      data[d3] = data[d2];
      data[d2] = tmp;
      if (data[d1] > tmp) {
        data[d2] = data[d1];
        data[d1] = tmp;
      }
    } else {
      if (data[d3] > tmp) {
        data[d4] = data[d3];
        data[d3] = tmp;
      }
    }
    // Insert 5
    tmp = data[d5];
    if (data[d3] > tmp) {
      data[d5] = data[d4];
      data[d4] = data[d3];
      data[d3] = tmp;
      if (data[d2] > tmp) {
        data[d3] = data[d2];
        data[d2] = tmp;
      }
      if (data[d1] > tmp) {
        data[d2] = data[d1];
        data[d1] = tmp;
      }
    } else {
      if (data[d4] > tmp) {
        data[d5] = data[d4];
        data[d4] = tmp;
      }
    }
  }
}
