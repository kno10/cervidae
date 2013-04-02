package com.kno10.java.cervidae.iterator;

/**
 * Modifiable iterator, that also supports removal.
 * 
 * Usage example:
 * 
 * <pre>
 * {@code 
 * for (MIter iter = ids.iter(); iter.valid(); iter.advance()) {
 *   if (testSomething(iter)) {
 *     iter.remove();
 *     continue; // Iterator may point to something else
 *   }
 * }
 * }
 * </pre>
 * 
 * @author Erich Schubert
 */
public interface MIter extends Iter {
  /**
   * Remove the object the iterator currently points to.
   * 
   * Note that, usually, the iterator will now point to a different object, very
   * often to the previous one (but this is not guaranteed!)
   */
  void remove();
}
