package com.kno10.java.cervidae.algorithms.sort5;

/**
 * Merge-sort / decision tree style sort for 5 elements.
 * 
 * @author Erich Schubert
 */
public class ${Type}DecisionSort5B implements ${Type}Sort5 {  
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
    ${type} tmp;
    if (data[p1] > data[p2]) {
      tmp = data[p1]; data[p1] = data[p2]; data[p2] = tmp;
    }
    // Have: d1 < d2
    if (data[p3] > data[p4]) {
      tmp = data[p3]; data[p3] = data[p4]; data[p4] = tmp;
    }
    // Have: d3 < d4
    if (data[p2] > data[p4]) {
      tmp = data[p2]; data[p2] = data[p4]; data[p4] = tmp;
      tmp = data[p1]; data[p1] = data[p3]; data[p3] = tmp;
    }
    // Have: d1 < d2 < d4 and d3 < d4

    // Find position of d5 in the longer chain:
    if (data[p5] < data[p2]) {
      if (data[p5] < data[p1]) {
        // Have: d5 < d1 < d2 < d4 and d3 < d4
        // Find position of d3, given d3 < d4
        if (data[p3] < data[p1]) {
          if (data[p3] < data[p5]) {
            // Have d3 < d5 < d1 < d2 < d4
            tmp = data[p1]; data[p1] = data[p3]; data[p3] = tmp;
            tmp = data[p5]; data[p5] = data[p4]; data[p4] = tmp;
          } else {
            // Have d5 < d3 < d1 < d2 < d4
            tmp = data[p1]; data[p1] = data[p5]; data[p5] = data[p4]; data[p4] = data[p2]; data[p2] = data[p3]; data[p3] = tmp; 
          }
        } else {
          if (data[p3] < data[p2]) {
            // Have d5 < d1 < d3 < d2 < d4
            tmp = data[p1]; data[p1] = data[p5]; data[p5] = data[p4]; data[p4] = data[p2]; data[p2] = tmp;  
          } else {
            // Have d5 < d1 < d2 < d3 < d4
            tmp = data[p1]; data[p1] = data[p5]; data[p5] = data[p4]; data[p4] = data[p3]; data[p3] = data[p2]; data[p2] = tmp; 
          }
        }
      } else {
        // Have: d1 < d5 < d2 < d4 and d3 < d4
        // Find position of d3, given d3 < d4
        if (data[p3] < data[p5]) {
          if (data[p3] < data[p1]) {
            // Have d3 < d1 < d5 < d2 < d4
            tmp = data[p1]; data[p1] = data[p3]; data[p3] = data[p5]; data[p5] = data[p4]; data[p4] = data[p2]; data[p2] = tmp; 
          } else {
            // Have d1 < d3 < d5 < d2 < d4
            tmp = data[p2]; data[p2] = data[p3]; data[p3] = data[p5]; data[p5] = data[p4]; data[p4] = tmp;
          }
        } else {
          if (data[p3] < data[p2]) {
            // Have d1 < d5 < d3 < d2 < d4
            tmp = data[p2]; data[p2] = data[p5]; data[p5] = data[p4]; data[p4] = tmp;
          } else {
            // Have d1 < d5 < d2 < d3 < d4
            tmp = data[p2]; data[p2] = data[p5]; data[p5] = data[p4]; data[p4] = data[p3]; data[p3] = tmp;
          }
        }
      }
    } else {
      if (data[p5] < data[p4]) {
        // Have: d1 < d2 < d5 < d4 and d3 < d4
        // Find position of d3, given d3 < d4
        if (data[p3] < data[p2]) {
          if (data[p3] < data[p1]) {
            // Have d3 < d1 < d2 < d5 < d4
            tmp = data[p1]; data[p1] = data[p3]; data[p3] = data[p2]; data[p2] = tmp;
            tmp = data[p4]; data[p4] = data[p5]; data[p5] = tmp;
          } else {
            // Have d1 < d3 < d2 < d5 < d4
            tmp = data[p2]; data[p2] = data[p3]; data[p3] = tmp;
            tmp = data[p4]; data[p4] = data[p5]; data[p5] = tmp;
          }
        } else {
          if (data[p3] < data[p5]) {
            // Have d1 < d2 < d3 < d5 < d4
            tmp = data[p4]; data[p4] = data[p5]; data[p5] = tmp;
          } else {
            // Have d1 < d2 < d5 < d3 < d4
            tmp = data[p3]; data[p3] = data[p5]; data[p5] = data[p4]; data[p4] = tmp;
          }
        }
      } else {
        // Have: d1 < d2 < d4 < d5 and d3 < d4
        // Find position of d3, given d3 < d4
        if (data[p3] < data[p2]) {
          if (data[p3] < data[p1]) {
            // Have d3 < d1 < d2 < d4 < d5
            tmp = data[p1]; data[p1] = data[p3]; data[p3] = data[p2]; data[p2] = tmp;
          } else {
            // Have d1 < d3 < d2 < d4 < d5
            tmp = data[p2]; data[p2] = data[p3]; data[p3] = tmp;
          }
        } else {
          // Have d1 < d2 < d3 < d4 < d5
          // Not needed, because we already committed the early changes.
        }
      }
    }
  }  
}
