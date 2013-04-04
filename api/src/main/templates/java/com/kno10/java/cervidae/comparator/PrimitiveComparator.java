package com.kno10.java.cervidae.comparator;

/**
 * Interface for comparing two ${Type}.
 * 
 * For default comparators, see {@link com.kno10.java.cervidae.comparator.PrimitiveComparators}
 * 
 * @author Erich Schubert
 */
//${NOTE}
public interface ${Type}Comparator {
  /**
   * Compare two ${Type}.
   * 
   * @param x First ${api-type}
   * @param y Second ${api-type}
   * @return Comparison result
   */
  int compare(${api-type} x, ${api-type} y);
}
