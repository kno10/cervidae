package com.kno10.java.cervidae;

import com.kno10.java.cervidae.algorithms.QuickSelectBo5;
import com.kno10.java.cervidae.arrays.DoubleArrayController;

/**
 * Utility class for double arrays.
 * 
 * @author Erich Schubert
 */
public class DoubleArrayUtil {
  /**
   * Compute the median of an array efficiently using the QuickSelect method.
   * 
   * Note: the array is <b>modified</b> by this.
   * 
   * @param data Data to process
   * @return Median value
   */
  public static double median(double[] data) {
    return median(data, 0, data.length);
  }

  /**
   * Compute the median of an array efficiently using the QuickSelect method.
   * 
   * Note: the array is <b>modified</b> by this.
   * 
   * @param data Data to process
   * @param begin Begin of valid values
   * @param end End of valid values (exclusive!)
   * @return Median value
   */
  public static double median(double[] data, int begin, int end) {
    final int length = end - begin;
    assert (length > 0);
    // Integer division is "floor" since we are non-negative.
    final int left = begin + ((length - 1) >> 1);
    QuickSelectBo5.quickSelect(DoubleArrayController.STATIC, data, begin, end, left);
    if (length % 2 == 1) {
      return data[left];
    } else {
      QuickSelectBo5.quickSelect(DoubleArrayController.STATIC, data, left + 1, end, left + 1);
      return data[left] + .5 * (data[left + 1] - data[left]);
    }
  }

  /**
   * Compute the median of an array efficiently using the QuickSelect method.
   * 
   * Note: the array is <b>modified</b> by this.
   * 
   * @param data Data to process
   * @param quant Quantile to compute
   * @return Value at quantile
   */
  public static double quantile(double[] data, double quant) {
    return quantile(data, 0, data.length, quant);
  }

  /**
   * Compute the median of an array efficiently using the QuickSelect method.
   * 
   * Note: the array is <b>modified</b> by this.
   * 
   * @param data Data to process
   * @param begin Begin of valid values
   * @param end End of valid values (exclusive!)
   * @param quant Quantile to compute
   * @return Value at quantile
   */
  public static double quantile(double[] data, int begin, int end, double quant) {
    final int length = end - begin;
    assert (length > 0) : "Quantile on empty set?";
    // Integer division is "floor" since we are non-negative.
    final double dleft = begin + (length - 1) * quant;
    final int ileft = (int) Math.floor(dleft);
    final double err = dleft - ileft;

    QuickSelectBo5.quickSelect(DoubleArrayController.STATIC, data, begin, end, ileft);
    if (err <= Double.MIN_NORMAL) {
      return data[ileft];
    } else {
      QuickSelectBo5.quickSelect(DoubleArrayController.STATIC, data, ileft + 1, end, ileft + 1);
      // Mix:
      double mix = data[ileft] + (data[ileft + 1] - data[ileft]) * err;
      return mix;
    }
  }
}
