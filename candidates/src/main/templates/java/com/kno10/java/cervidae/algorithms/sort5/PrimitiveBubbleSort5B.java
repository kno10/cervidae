package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Bubble sort for 5 elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}BubbleSort5B implements ${Type}Sort5 {  
  /**
   * Bubble sort with 5 elements, unrolled, with branches
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
  public void sort5(${atype}[] data, int p1, int p2, int p3, int p4, int p5) {
    ${type} tmp;
    if (data[p1] > data[p2]) {
      tmp = data[p1]; data[p1] = data[p2]; data[p2] = tmp;
    }
    if (data[p2] > data[p3]) {
      tmp = data[p2]; data[p2] = data[p3]; data[p3] = tmp;
    }
    if (data[p3] > data[p4]) {
      tmp = data[p3]; data[p3] = data[p4]; data[p4] = tmp;
    }
    if (data[p4] > data[p5]) {
      tmp = data[p4]; data[p4] = data[p5]; data[p5] = tmp;
    }
    //
    if (data[p1] > data[p2]) {
      tmp = data[p1]; data[p1] = data[p2]; data[p2] = tmp;
    }
    if (data[p2] > data[p3]) {
      tmp = data[p2]; data[p2] = data[p3]; data[p3] = tmp;
    }
    if (data[p3] > data[p4]) {
      tmp = data[p3]; data[p3] = data[p4]; data[p4] = tmp;
    }
    //
    if (data[p1] > data[p2]) {
      tmp = data[p1]; data[p1] = data[p2]; data[p2] = tmp;
    }
    if (data[p2] > data[p3]) {
      tmp = data[p2]; data[p2] = data[p3]; data[p3] = tmp;
    }
    //
    if (data[p1] > data[p2]) {
      tmp = data[p1]; data[p1] = data[p2]; data[p2] = tmp;
    }
  }
}
