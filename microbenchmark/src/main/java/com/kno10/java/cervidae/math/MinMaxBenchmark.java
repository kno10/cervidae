package com.kno10.java.cervidae.math;

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

import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

@Fork(1)
@Warmup(iterations = 5, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 1000, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MinMaxBenchmark {
	@Param({ "10000" })
	int size; // set automatically by framework

	private double[] array; // set by us, in setUp()

	private int[] iarray; // set by us, in setUp()

	@Setup
	public void setUp() {
		array = SortBenchmarkUtil.generateRandomDoubleData(size,
				MacroPattern.NORMAL10D, 0, 0);
		iarray = SortBenchmarkUtil.generateRandomIntegerData(size,
				MacroPattern.CONSTANT, 1, 0);
	}

	@Benchmark
	public double doubleMaxMath() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			max = Math.max(array[j], max);
		}
		return max;
	}

	@Benchmark
	public double doubleMaxIf1() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			if (array[j] > max) {
				max = array[j];
			}
		}
		return max;
	}

	@Benchmark
	public double doubleMaxIf2() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			final double loc = array[j];
			if (loc > max) {
				max = loc;
			}
		}
		return max;
	}

	@Benchmark
	public double doubleMaxIf3() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			if (array[j] > max) {
				max = array[j];
			}
		}
		return max;
	}

	@Benchmark
	public double doubleMaxIf4() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			final double loc = array[j];
			if (loc > max) {
				max = loc;
			}
		}
		return max;
	}

	@Benchmark
	public double doubleMaxTernary() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			max = (array[j] > max) ? array[j] : max;
		}
		return max;
	}

	@Benchmark
	public double doubleMaxTernary2() {
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			final double loc = array[j];
			max = (loc > max) ? loc : max;
		}
		return max;
	}

	@Benchmark
	public double doubleMinMaxMath() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			min = Math.min(array[j], min);
			max = Math.max(array[j], max);
		}
		return min + max;
	}

	@Benchmark
	public double doubleMinMaxIf() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			if (array[j] < min) {
				min = array[j];
			} else if (array[j] > max) {
				max = array[j];
			}
		}
		return min + max;
	}

	@Benchmark
	public double doubleMinMaxIf2() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			final double loc = array[j];
			if (loc < min) {
				min = loc;
			} else if (loc > max) {
				max = loc;
			}
		}
		return min + max;
	}

	@Benchmark
	public double doubleMinMaxIf3() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			if (array[j] < min) {
				min = array[j];
			}
			if (array[j] > max) {
				max = array[j];
			}
		}
		return min + max;
	}

	@Benchmark
	public double doubleMinMaxIf4() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			final double loc = array[j];
			if (loc < min) {
				min = loc;
			}
			if (loc > max) {
				max = loc;
			}
		}
		return min + max;
	}

	@Benchmark
	public double doubleMinMaxTernary() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			min = (array[j] < min) ? array[j] : min;
			max = (array[j] > max) ? array[j] : max;
		}
		return min + max;
	}

	@Benchmark
	public double doubleMinMaxTernary2() {
		double min = Double.POSITIVE_INFINITY;
		double max = Double.NEGATIVE_INFINITY;
		for (int j = 0; j < size; j++) {
			final double loc = array[j];
			min = (loc < min) ? loc : min;
			max = (loc > max) ? loc : max;
		}
		return min + max;
	}

	@Benchmark
	public int integerMaxMath() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			max = Math.max(iarray[j], max);
		}
		return max;
	}

	@Benchmark
	public int integerMaxIf() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			if (iarray[j] > max) {
				max = iarray[j];
			}
		}
		return max;
	}

	@Benchmark
	public int integerMaxIf2() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			final int loc = iarray[j];
			if (loc > max) {
				max = loc;
			}
		}
		return max;
	}

	@Benchmark
	public int integerMaxIf3() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			if (iarray[j] > max) {
				max = iarray[j];
			}
		}
		return max;
	}

	@Benchmark
	public int integerMaxIf4() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			final int loc = iarray[j];
			if (loc > max) {
				max = loc;
			}
		}
		return max;
	}

	@Benchmark
	public int integerMaxTernary() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			max = (iarray[j] > max) ? iarray[j] : max;
		}
		return max;
	}

	@Benchmark
	public int integerMaxTernary2() {
		int max = Integer.MIN_VALUE;
		for (int j = 0; j < size; j++) {
			final int loc = iarray[j];
			max = (loc > max) ? loc : max;
		}
		return max;
	}

	@Benchmark
	public int integerMinMaxMath() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			min = Math.min(iarray[j], min);
			max = Math.max(iarray[j], max);
		}
		return min + max;
	}

	@Benchmark
	public int integerMinMaxIf() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			if (iarray[j] < min) {
				min = iarray[j];
			} else if (iarray[j] > max) {
				max = iarray[j];
			}
		}
		return min + max;
	}

	@Benchmark
	public int integerMinMaxIf2() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			final int loc = iarray[j];
			if (loc < min) {
				min = loc;
			} else if (loc > max) {
				max = loc;
			}
		}
		return min + max;
	}

	@Benchmark
	public int integerMinMaxIf3() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			if (iarray[j] < min) {
				min = iarray[j];
			}
			if (iarray[j] > max) {
				max = iarray[j];
			}
		}
		return min + max;
	}

	@Benchmark
	public int integerMinMaxIf4() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			final int loc = iarray[j];
			if (loc < min) {
				min = loc;
			}
			if (loc > max) {
				max = loc;
			}
		}
		return min + max;
	}

	@Benchmark
	public int integerMinMaxTernary() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			min = (iarray[j] < min) ? iarray[j] : min;
			max = (iarray[j] > max) ? iarray[j] : max;
		}
		return min + max;
	}

	@Benchmark
	public int integerMinMaxTernary2() {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int j = 0; j < size; j++) {
			final int loc = iarray[j];
			min = (loc < min) ? loc : min;
			max = (loc > max) ? loc : max;
		}
		return min + max;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(MinMaxBenchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
