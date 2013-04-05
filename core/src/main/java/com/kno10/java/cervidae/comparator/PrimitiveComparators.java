package com.kno10.java.cervidae.comparator;

import com.kno10.java.cervidae.comparator.ByteComparator;
import com.kno10.java.cervidae.comparator.CharacterComparator;
import com.kno10.java.cervidae.comparator.DoubleComparator;
import com.kno10.java.cervidae.comparator.FloatComparator;
import com.kno10.java.cervidae.comparator.IntegerComparator;
import com.kno10.java.cervidae.comparator.LongComparator;
import com.kno10.java.cervidae.comparator.ShortComparator;

/**
 * Class containing the default iterators for the primitive types.
 * 
 * @author Erich Schubert
 */
public final class PrimitiveComparators {
  /**
   * Ascending default comparator.
   */
  public static final DoubleComparator DOUBLE_ASCENDING = new DoubleComparator() {
    @Override
    public int compare(double x, double y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final DoubleComparator DOUBLE_DESCENDING = new DoubleComparator() {
    @Override
    public int compare(double x, double y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final FloatComparator FLOAT_ASCENDING = new FloatComparator() {
    @Override
    public int compare(float x, float y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final FloatComparator FLOAT_DESCENDING = new FloatComparator() {
    @Override
    public int compare(float x, float y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final IntegerComparator INTEGER_ASCENDING = new IntegerComparator() {
    @Override
    public int compare(int x, int y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final IntegerComparator INTEGER_DESCENDING = new IntegerComparator() {
    @Override
    public int compare(int x, int y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final ShortComparator SHORT_ASCENDING = new ShortComparator() {
    @Override
    public int compare(short x, short y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final ShortComparator SHORT_DESCENDING = new ShortComparator() {
    @Override
    public int compare(short x, short y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final LongComparator LONG_ASCENDING = new LongComparator() {
    @Override
    public int compare(long x, long y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final LongComparator LONG_DESCENDING = new LongComparator() {
    @Override
    public int compare(long x, long y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final ByteComparator BYTE_ASCENDING = new ByteComparator() {
    @Override
    public int compare(byte x, byte y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final ByteComparator BYTE_DESCENDING = new ByteComparator() {
    @Override
    public int compare(byte x, byte y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final CharacterComparator CHARACTER_ASCENDING = new CharacterComparator() {
    @Override
    public int compare(char x, char y) {
      return (x < y) ? -1 : (x > y) ? 1 : 0;
    }
  };

  /**
   * Ascending default comparator.
   */
  public static final CharacterComparator CHARACTER_DESCENDING = new CharacterComparator() {
    @Override
    public int compare(char x, char y) {
      return (x > y) ? -1 : (x < y) ? 1 : 0;
    }
  };

}
