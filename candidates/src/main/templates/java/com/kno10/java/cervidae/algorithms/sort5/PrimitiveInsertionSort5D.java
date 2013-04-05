package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Optimized methods to sort <b>exactly</b> five elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}InsertionSort5D implements ${Type}Sort5 {
  /**
   * Five element explicit insertion sort, inserting 3+4 at the same time.
   * 
   * Note: for this to make sense, {@code d1 < d2 < d3 < d4 < d5} should hold!
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
    // Sort third and fourth.
    if (data[d3] > data[d4]) {
      final ${type} tmp = data[d4];
      data[d4] = data[d3];
      data[d3] = tmp;
    }
    // Insert fourth and third
    final ${type} tmp4 = data[d4];
    final ${type} tmp3 = data[d3];
    if (data[d2] > tmp4) {
      data[d4] = data[d2];
      if (data[d1] > tmp4) {
        data[d3] = data[d1];
        data[d2] = tmp4;
        data[d1] = tmp3;
      } else {
        data[d3] = tmp4;
        if (data[d1] > tmp3) {
          data[d2] = data[d1];
          data[d1] = tmp3;
        } else {
          data[d2] = tmp3;
        }
      }
    } else if (data[d2] > tmp3) {
      if (data[d1] > tmp3) {
        data[d3] = data[d2];
        data[d2] = data[d1];
        data[d1] = tmp3;
      } else {
        data[d3] = data[d2];
        data[d2] = tmp3;
      }
    }
    // Insert fifth
    if (data[d4] > data[d5]) {
      final ${type} tmp5 = data[d5];
      data[d5] = data[d4];
      if (data[d3] > tmp5) {
        data[d4] = data[d3];
        if (data[d2] > tmp5) {
          data[d3] = data[d2];
          if (data[d1] > tmp5) {
            data[d2] = data[d1];
            data[d1] = tmp5;
          } else {
            data[d2] = tmp5;
          }
        } else {
          data[d3] = tmp5;
        }
      } else {
        data[d4] = tmp5;
      }
    }
  }
}
