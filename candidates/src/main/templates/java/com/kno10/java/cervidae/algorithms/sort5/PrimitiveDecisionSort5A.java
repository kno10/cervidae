package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Merge-sort / decision tree style sort for 5 elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}DecisionSort5A implements ${Type}Sort5 {  
  /**
   * This is a merge-sort inspired optimal sorting, using a maximum of 7 comparisons.
   * 
   * However, as it is quite explicit, it has a rather large byte code.
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
    // Temporary storage for sorting:
    ${atype} d1 = data[p1], d2 = data[p2], d3 = data[p3], d4 = data[p4], d5 = data[p5];
    
    if (d1 > d2) {
      ${type} tmp = d1; d1 = d2; d2 = tmp;
    }
    // Have: d1 < d2
    if (d3 > d4) {
      ${type} tmp = d3; d3 = d4; d4 = tmp;
    }
    // Have: d3 < d4
    if (d2 > d4) {
      ${type} tmp = d2; d2 = d4; d4 = tmp;
      tmp = d1; d1 = d3; d3 = tmp;
    }
    // Have: d1 < d2 < d4 and d3 < d4

    // Find position of d5 in the longer chain:
    if (d5 < d2) {
      if (d5 < d1) {
        // Have: d5 < d1 < d2 < d4 and d3 < d4
        // Find position of d3, given d3 < d4
        if (d3 < d1) {
          if (d3 < d5) {
            // Have d3 < d5 < d1 < d2 < d4
            set5(data, p1, p2, p3, p4, p5, d3, d5, d1, d2, d4);
          } else {
            // Have d5 < d3 < d1 < d2 < d4
            set5(data, p1, p2, p3, p4, p5, d5, d3, d1, d2, d4);
          }
        } else {
          if (d3 < d2) {
            // Have d5 < d1 < d3 < d2 < d4
            set5(data, p1, p2, p3, p4, p5, d5, d1, d3, d2, d4);
          } else {
            // Have d5 < d1 < d2 < d3 < d4
            set5(data, p1, p2, p3, p4, p5, d5, d1, d2, d3, d4);
          }
        }
      } else {
        // Have: d1 < d5 < d2 < d4 and d3 < d4
        // Find position of d3, given d3 < d4
        if (d3 < d5) {
          if (d3 < d1) {
            // Have d3 < d1 < d5 < d2 < d4
            set5(data, p1, p2, p3, p4, p5, d3, d1, d5, d2, d4);
          } else {
            // Have d1 < d3 < d5 < d2 < d4
            set5(data, p1, p2, p3, p4, p5, d1, d3, d5, d2, d4);
          }
        } else {
          if (d3 < d2) {
            // Have d1 < d5 < d3 < d2 < d4
            set5(data, p1, p2, p3, p4, p5, d1, d5, d3, d2, d4);
          } else {
            // Have d1 < d5 < d2 < d3 < d4
            set5(data, p1, p2, p3, p4, p5, d1, d5, d2, d3, d4);
          }
        }
      }
    } else {
      if (d5 < d4) {
        // Have: d1 < d2 < d5 < d4 and d3 < d4
        // Find position of d3, given d3 < d4
        if (d3 < d2) {
          if (d3 < d1) {
            // Have d3 < d1 < d2 < d5 < d4
            set5(data, p1, p2, p3, p4, p5, d3, d1, d2, d5, d4);
          } else {
            // Have d1 < d3 < d2 < d5 < d4
            set5(data, p1, p2, p3, p4, p5, d1, d3, d2, d5, d4);
          }
        } else {
          if (d3 < d5) {
            // Have d1 < d2 < d3 < d5 < d4
            set5(data, p1, p2, p3, p4, p5, d1, d2, d3, d5, d4);
          } else {
            // Have d1 < d2 < d5 < d3 < d4
            set5(data, p1, p2, p3, p4, p5, d1, d2, d5, d3, d4);
          }
        }
      } else {
        // Have: d1 < d2 < d4 < d5 and d3 < d4
        // Find position of d3, given d3 < d4
        if (d3 < d2) {
          if (d3 < d1) {
            // Have d3 < d1 < d2 < d4 < d5
            set5(data, p1, p2, p3, p4, p5, d3, d1, d2, d4, d5);
          } else {
            // Have d1 < d3 < d2 < d4 < d5
            set5(data, p1, p2, p3, p4, p5, d1, d3, d2, d4, d5);
          }
        } else {
          // Have d1 < d2 < d3 < d4 < d5
          set5(data, p1, p2, p3, p4, p5, d1, d2, d3, d4, d5);
          // Needed, because we swapped ds before.
        }
      }
    }
  }
  
  /**
   * Replace the values into the data structure.
   * 
   * @param data Data
   * @param p1 Position of first
   * @param p2 Position of second
   * @param p3 Position of third
   * @param p4 Position of fourth
   * @param p5 Position of fifth
   * @param d1 Data of first
   * @param d2 Data of second
   * @param d3 Data of third
   * @param d4 Data of fourth
   * @param d5 Data of fifth
   */
  private static final void set5(${atype}[] data, int p1, int p2, int p3, int p4, int p5, ${atype} d1, ${atype} d2, ${atype} d3, ${atype} d4, ${atype} d5) {
    data[p1] = d1;
    data[p2] = d2;
    data[p3] = d3;
    data[p4] = d4;
    data[p5] = d5;
  }
}
