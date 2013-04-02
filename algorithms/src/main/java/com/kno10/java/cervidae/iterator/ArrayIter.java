package com.kno10.java.cervidae.iterator;

/**
 * Array iterators can also go backwards and seek.
 * 
 * @author Erich Schubert
 */
public interface ArrayIter extends Iter {
  /**
   * Get current iterator offset.
   * 
   * @return Iterator position
   */
  public int getOffset();

  /**
   * Moves the iterator forward or backward by the given offset.
   * 
   * @param count offset to move forward or backwards
   */
  public void advance(int count);

  /**
   * Moves the iterator backward to the previous entry.
   */
  public void retract();

  /**
   * Moves the iterator to the given position
   * 
   * @param off Seek offset
   */
  public void seek(int off);
}
