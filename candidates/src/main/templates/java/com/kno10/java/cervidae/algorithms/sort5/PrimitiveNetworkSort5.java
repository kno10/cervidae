package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Optimized methods to sort <b>exactly</b> five elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}NetworkSort5 implements ${Type}Sort5 {
  /**
   * Sort the elements at the given five positions using an optimal sorting network.
   * 
   * Note: for this to make sense, {@code d1 < d2 < d3 < d4 < d5} should hold!
   * 
   * @param data Data array
   * @param d1 Position of first
   * @param d2 Position of second
   * @param d3 Position of third
   * @param d4 Position of fifth
   * @param d5 Position of fourth
   * @param comp Comparator
   */
  @Override
  public void sort5(${atype}[] data, int d1, int d2, int d3, int d4, int d5) {
    if (data[d1] > data[d4]) {
      final ${type} tmp = data[d4];
      data[d4] = data[d1];
      data[d1] = tmp;
    }
    // Independent - should be nicer to the CPU:
    if (data[d1] > data[d3]) {
      final ${type} tmp = data[d3];
      data[d3] = data[d1];
      data[d1] = tmp;
    }
    if (data[d2] > data[d5]) {
      final ${type} tmp = data[d5];
      data[d5] = data[d2];
      data[d2] = tmp;
    }
    // Independent - should be nicer to the CPU:
    if (data[d2] > data[d4]) {
      final ${type} tmp = data[d4];
      data[d4] = data[d2];
      data[d2] = tmp;
    }
    if (data[d3] > data[d5]) {
      final ${type} tmp = data[d5];
      data[d5] = data[d3];
      data[d3] = tmp;
    }
    // Independent - should be nicer to the CPU:
    if (data[d1] > data[d2]) {
      final ${type} tmp = data[d2];
      data[d2] = data[d1];
      data[d1] = tmp;
    }
    if (data[d3] > data[d4]) {
      final ${type} tmp = data[d4];
      data[d4] = data[d3];
      data[d3] = tmp;
    }
    // Independent - should be nicer to the CPU:
    if (data[d2] > data[d3]) {
      final ${type} tmp = data[d3];
      data[d3] = data[d2];
      data[d2] = tmp;
    }
    if (data[d4] > data[d5]) {
      final ${type} tmp = data[d5];
      data[d5] = data[d4];
      data[d4] = tmp;
    }
  }
}
