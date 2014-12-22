package com.kno10.java.cervidae.math;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Benchmark to measure the performance of different algorithms to compute the
 * mean of an array. This may sound trivial, but if you want both a fast and
 * accurate algorithm, you have to be careful.
 * 
 * From the initial analysis, Kahan summation will do a good job for sum and
 * mean. Welford suffers more from cancellation; but it may pay off when you
 * need to compute variance and higher order moments. Plus, the two can be
 * combined.
 * 
 * @author Erich Schubert
 */
@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class VarianceBenchmark {
	@Param({ "1000000" })
	int size; // set automatically by framework

	private double[] array; // set by us, in setUp()

	public enum Variant {//
		NAIVE_SINGLE { // The naive, but lowest precision method
			@Override
			double variance(double[] array) {
				double sum = 0., sqsum = 0.;
				int c = 0;
				for (int j = 0; j < array.length; j++) {
					sum += array[j];
					sqsum += array[j] * array[j];
					c++;
				}
				sum /= c;
				sqsum /= c - 1;
				return sqsum - sum * sum * c / (c - 1.);
			}
		},
		NAIVE_DOUBLE { // The naive double-pass method.
			@Override
			double variance(double[] array) {
				double sum = 0.;
				int c = 0;
				for (int j = 0; j < array.length; j++) {
					sum += array[j];
					c++;
				}
				final double mean = sum / c;

				double sqsum = 0.;
				c = 0;
				for (int j = 0; j < array.length; j++) {
					double val = array[j] - mean;
					sqsum += val * val;
					c++;
				}
				return sqsum / (c - 1);
			}
		},
		KAHAN_SINGLE { // Single-pass method using Kahan summation.
			@Override
			double variance(double[] array) {
				double sum = 0., err = 0.;
				double sqsum = 0., sqerr = 0.;
				int c = 0;
				for (int j = 0; j < array.length; j++) {
					{
						final double v = array[j] + err;
						final double tmp = sum + v; // may lose some precision
						err = v - (tmp - sum); // Compute loss
						sum = tmp;
					}
					{
						final double v = array[j] * array[j] + sqerr;
						final double tmp = sqsum + v; // may lose some precision
						sqerr = v - (tmp - sqsum); // Compute loss
						sqsum = tmp;
					}
					c++;
				}
				sum /= c;
				sqsum /= c - 1;
				return sqsum - sum * sum * c / (c - 1.);
			}
		},
		KAHAN_DOUBLE { // Double-pass method using Kahan summation.
			@Override
			double variance(double[] array) {
				double sum = 0., err = 0.;
				int c = 0;
				for (int j = 0; j < array.length; j++) {
					final double v = array[j] + err;
					final double tmp = sum + v; // may lose some precision
					err = v - (tmp - sum); // Compute loss
					sum = tmp;
					c++;
				}
				sum /= c;
				c = 0;
				double sqsum = 0., sqerr = 0.;
				for (int j = 0; j < array.length; j++) {
					final double d = array[j] - sum;
					final double v = d * d + sqerr;
					final double tmp = sqsum + v; // may lose some precision
					sqerr = v - (tmp - sqsum); // Compute loss
					sqsum = tmp;
					c++;
				}
				sqsum /= c - 1;
				return sqsum;
			}
		},
		WELFORD_I { // Incremental, as discussed by knuth-welford
			@Override
			double variance(double[] array) {
				double mean = 0.;
				double sqsum = 0.;
				int c = 0;
				for (int j = 0; j < array.length; j++) {
					final double delta = array[j] - mean;
					// Update mean.
					mean += delta / ++c;
					// Update sum of squares
					sqsum += delta * (array[j] - mean); // c.f. new mean!
				}
				return sqsum / (c - 1);
			}
		},
		WELFORD_D { // Incremental, as discussed by knuth-welford; supports
					// weighted observations.
			@Override
			double variance(double[] array) {
				double mean = 0.;
				double sqsum = 0.;
				double c = 0.;
				for (int j = 0; j < array.length; j++) {
					final double delta = array[j] - mean;
					c += 1.;
					// Update mean
					mean += delta / c;
					// Update sum of squares
					sqsum += delta * 1. * (array[j] - mean); // c.f. new mean!
				}
				return sqsum / (c - 1);
			}
		},
		WEL_KAH { // Combination of welford and kahan summation.
			@Override
			double variance(double[] array) {
				double mean = 0., err = 0.;
				double sqsum = 0., sqerr = 0.;
				double c = 0.;
				for (int j = 0; j < array.length; j++) {
					c += 1.;
					final double delta = array[j] - mean;
					{
						final double inc = delta / c + err;
						final double tmp = mean + inc; // may lose some
														// precision
						err = inc - (tmp - mean); // Compute loss
						mean = tmp;
					}
					final double sqinc = delta * (array[j] - mean) + sqerr;
					{
						final double tmp = sqsum + sqinc; // may lose some
															// precision
						sqerr = sqinc - (tmp - sqsum); // Compute loss
						sqsum = tmp;
					}
				}
				return sqsum / (c - 1);
			}
		},
		WEL_KAH2 { // Combination of welford and extra kahan summation on c.
			@Override
			double variance(double[] array) {
				double mean = 0., err = 0.;
				double sqsum = 0., sqerr = 0.;
				double c = 0., cerr = 0.;
				for (int j = 0; j < array.length; j++) {
					{
						final double inc = 1. + cerr;
						final double tmp = c + inc;
						cerr = inc - (tmp - c);
						c = tmp;
					}
					final double delta = array[j] - mean;
					{
						final double inc = delta / c + err;
						final double tmp = mean + inc; // may lose some
														// precision
						err = inc - (tmp - mean); // Compute loss
						mean = tmp;
					}
					final double sqinc = delta * (array[j] - mean) + sqerr;
					{
						final double tmp = sqsum + sqinc; // may lose some
															// precision
						sqerr = sqinc - (tmp - sqsum); // Compute loss
						sqsum = tmp;
					}
				}
				return sqsum / (c - 1);
			}
		},
		;
		abstract double variance(double[] array);
	};

	public enum Pattern {
		CONSTANT {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				for (int i = 0; i < size; i++) {
					array[i] = 1.23456789012345;
				}
				return array;
			}

			@Override
			double getMean() {
				return 1.23456789012345;
			}

			@Override
			double getVar(int size) {
				return 0.;
			}

			@Override
			double getErrorScale(int size) {
				return 1e-15 * size;
			}

			@Override
			double getVarScale(int size) {
				return 1e-15 * size;
			}
		},
		ASCENDING {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				for (int i = 0; i < size; i++) {
					array[i] = 1e15 * (i + 1) / (size + 1);
				}
				return array;
			}

			@Override
			double getMean() {
				return .5e15;
			}

			@Override
			double getVar(int size) {
				double stepwidth = 1e15 / (size + 1);
				return (size + 1.) / size * (size * size - 1.) / 12 * stepwidth
						* stepwidth;
			}

			@Override
			double getErrorScale(int size) {
				return 1. * size;
			}

			@Override
			double getVarScale(int size) {
				return 1e14;
			}
		},
		DESCENDING {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				for (int i = 0; i < size; i++) {
					array[i] = 1e15 * (size - i) / (size + 1);
				}
				return array;
			}

			@Override
			double getMean() {
				return .5e15;
			}

			@Override
			double getVar(int size) {
				double stepwidth = 1e15 / (size + 1);
				return (size + 1.) / size * (size * size - 1.) / 12 * stepwidth
						* stepwidth;
			}

			@Override
			double getErrorScale(int size) {
				return 1. * size;
			}

			@Override
			double getVarScale(int size) {
				return 1e14;
			}
		},
		DOWNUP {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				int halfsize = size >> 1;
				for (int i = 0; i < halfsize; i++) {
					array[i] = (1e15 * (halfsize - i)) / (halfsize + 1);
					array[size - 1 - i] = 1e15 - array[i];
				}
				return array;
			}

			@Override
			double getMean() {
				return .5e15;
			}

			@Override
			double getVar(int size) {
				double stepwidth = 1e15 / (size + 1);
				return (size + 1.) / size * (size * size - 1.) / 12 * stepwidth
						* stepwidth;
			}

			@Override
			double getErrorScale(int size) {
				return 1. * size;
			}

			@Override
			double getVarScale(int size) {
				return 1e14;
			}
		},
		UPDOWN {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				int halfsize = size >> 1;
				for (int i = 0; i < halfsize; i++) {
					array[i] = (1e15 * (i + 1)) / (halfsize + 1);
					array[size - 1 - i] = 1e15 - array[i];
				}
				return array;
			}

			@Override
			double getMean() {
				return .5e15;
			}

			@Override
			double getVar(int size) {
				double stepwidth = 1e15 / (size + 1);
				return (size + 1.) / size * (size * size - 1.) / 12 * stepwidth
						* stepwidth;
			}

			@Override
			double getErrorScale(int size) {
				return 1. * size;
			}

			@Override
			double getVarScale(int size) {
				return 1e14;
			}
		},
		GAUSSIAN {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				Random r = new Random(0);
				double stddev = 1e10;
				for (int i = 0; i < size; i++) {
					double v = r.nextGaussian() * stddev;
					array[i] = v;
				}
				return array;
			}

			@Override
			double getMean() {
				return 0;
			}

			@Override
			double getVar(int size) {
				return 1e20;
			}

			@Override
			double getErrorScale(int size) {
				// Expected variance decreases with size.
				return 1e10 * Math.sqrt(size);
			}

			@Override
			double getVarScale(int size) {
				return 1e10;
			}
		},
		GAUSSIAN_BIASED_E15 {
			@Override
			double[] generate(int size) {
				double[] array = new double[size];
				Random r = new Random(0);
				double mean = 1e15, stddev = 1e5;
				for (int i = 0; i < size; i++) {
					double v = r.nextGaussian() * stddev;
					array[i] = mean + v;
				}
				return array;
			}

			@Override
			double getMean() {
				return 1e15;
			}

			@Override
			double getVar(int size) {
				return 1e10;
			}

			@Override
			double getErrorScale(int size) {
				// Expected variance decreases with size.
				return 1e5 * Math.sqrt(size);
			}

			@Override
			double getVarScale(int size) {
				return 1e5;
			}
		},
		;
		/** Generate the data set */
		abstract double[] generate(int size);

		/** Expected mean value; approximate! */
		abstract double getMean();

		/** Expected scale of errors, for normalization */
		abstract double getErrorScale(int size);

		/** Expected variance; approximate! */
		abstract double getVar(int size);

		/** Expected scale of errors on variance, for normalization */
		abstract double getVarScale(int size);
	}

	@Param
	Variant variant;

	@Param
	Pattern pattern;

	@Setup
	public void setUp() {
		array = pattern.generate(size);
	}

	@Benchmark
	public double benchmarkDoubleMean() {
		return variant.variance(array);
	}

	public double meanError() {
		return 100
				* Math.sqrt(Math.abs(variant.variance(array)
						- pattern.getVar(size))) / pattern.getVarScale(size);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(VarianceBenchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
