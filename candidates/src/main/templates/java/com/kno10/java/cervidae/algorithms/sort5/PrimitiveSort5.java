package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Optimized methods to sort <b>exactly</b> five elements.
 * 
 * @author Erich Schubert
 */
public interface ${Type}Sort5 {
  /**
   * Sort the elements at the given five positions.
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
  void sort5(${atype}[] data, int d1, int d2, int d3, int d4, int d5);
}
