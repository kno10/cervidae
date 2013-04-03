package com.kno10.java.cervidae.iterator;

/**
 * Iterator interface for more than one return value.
 * 
 * The Java standard {@link java.util.Iterator} interface has some drawbacks:
 * <ul>
 * <li>the only way to get the current value is to advance the iterator</li>
 * <li>the iterator can only point to a single value</li>
 * <li>the iterator can only return objects, not primitives</li>
 * </ul>
 * 
 * This iterator interface is a bit more flexible. For example on a distance
 * list, we can have a single type of iterator that allows access to the
 * distance, the object ID or the combination of both.
 * 
 * In some situations, this can save the creation of many small objects, which
 * put load on the garbage collector. This super interface does not have a "get"
 * operation, which is to come from specialized interfaces instead.
 * 
 * Usage example:
 * 
 * <pre>
 * {@code 
 * for (Iter iter = ids.iter(); iter.valid(); iter.advance()) {
 *   iter.doSomething();
 * }
 * }
 * </pre>
 * 
 * @author Erich Schubert
 */
public interface Iter {
  /**
   * Returns true if the iterator currently points to a valid object.
   * 
   * @return a <code>boolean</code> value, whether the position is valid.
   */
  public boolean valid();

  /**
   * Moves the iterator forward to the next entry.
   */
  public void advance();
}
