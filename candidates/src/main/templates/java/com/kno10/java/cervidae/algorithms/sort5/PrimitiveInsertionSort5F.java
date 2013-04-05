package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Optimized methods to sort <b>exactly</b> five elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}InsertionSort5F implements ${Type}Sort5 {
  /**
   * Five element explicit dual insertion sort, inserting 4 and 5 at the same time.
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
    // Insert third
    if (data[d2] > data[d3]) {
      final ${type} tmp = data[d3];
      data[d3] = data[d2];
      if (data[d1] > tmp) {
        data[d2] = data[d1];
        data[d1] = tmp;
      } else {
        data[d2] = tmp;
      }
    }
    // Sort fourth and fifth.
    if (data[d4] > data[d5]) {
      final ${type} tmp = data[d5];
      data[d5] = data[d4];
      data[d4] = tmp;
    }
    // Insert fifth and fourth.
    final ${type} tmp5 = data[d5];
    final ${type} tmp4 = data[d4];
    if (data[d3] > tmp5) {
      data[d5] = data[d3];
      if (data[d2] > tmp5) {
        data[d4] = data[d2];
        if (data[d1] > tmp5) {
          data[d3] = data[d1];
          data[d2] = tmp5;
          data[d1] = tmp4;
        } else {
          data[d3] = tmp5;
          if (data[d1] > tmp4) {
            data[d2] = data[d1];
            data[d1] = tmp4;
          } else {
            data[d2] = tmp4;
          }
        }
      } else {
        data[d4] = tmp5;
        if (data[d2] > tmp4) {
          data[d3] = data[d2];
          if (data[d1] > tmp4) {
            data[d2] = data[d1];
            data[d1] = tmp4;
          } else {
            data[d2] = tmp4;
          }
        } else {
          data[d3] = tmp4;
        }
      }
    } else if (data[d3] > tmp4) {
      data[d4] = data[d3];
      if (data[d2] > tmp4) {
        data[d3] = data[d2];
        if (data[d1] > tmp4) {
          data[d2] = data[d1];
          data[d1] = tmp4;
        } else {
          data[d2] = tmp4;
        }
      } else {
        data[d3] = tmp4;
      }
    }
  }
}
