package com.kno10.java.cervidae.algorithms.sort;

import java.util.Arrays;
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

import com.kno10.java.cervidae.adapter.arraylike.ArraySortAdapter;
import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil.MacroPattern;

/**
 * Benchmark class for sorting algorithms.
 * 
 * @author Erich Schubert
 */
@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SortBenchmark {
	@Param({ "10", "100", "1000", "10000" })
	int size;

	@Param({ ".01", ".1", ".5", "1" })
	double randomness;

	static final String BASE = "com.kno10.java.cervidae.algorithms.sort.";

	@Param({ //
	BASE + "DualPivotQuickSortBo5", //
			BASE + "QuickSortBo5", //
			BASE + "QuickSortBo3", //
			BASE + "QuickSortTextbook",//
			BASE + "HeapSortTextbook",//
			// We already know that these three are much slower.
			BASE + "BubbleSort", //
			BASE + "BubbleSortTextbook", //
			BASE + "BidirectionalBubbleSort", //
			BASE + "InsertionSort", //
			BASE + "BinaryInsertionSort", //
			// Reference combinations
			BASE + "SortBenchmark$JavaSort", //
			BASE + "SortBenchmark$OptimizedSort", //
	})
	String algname;

	ArraySortAlgorithm alg;

	@Param({ "INCREASING", "DECREASING", "SAW8", "DECINC", "CONSTANT" })
	MacroPattern pattern;

	/**
	 * The unsorted data.
	 */
	double[] array;

	@Setup
	public void setUp() {
		try {
			alg = (ArraySortAlgorithm) Class.forName(algname).newInstance();
		} catch (Exception e) {
			System.err.println("Exception loading algorithm: " + algname + ": "
					+ e);
			e.printStackTrace(System.err);
			throw new RuntimeException("Exception loading algorithm: "
					+ algname, e);
		}
		// @Param values are guaranteed to have been injected by now
		array = SortBenchmarkUtil.generateRandomDoubleData(size, pattern,
				randomness, 0L);
	}

	@Benchmark
	public double benchmarkSortPrimitiveDoubles() {
		double[] tmp = array.clone();
		alg.sort(DoubleArrayAdapter.STATIC, tmp);
		return tmp[0] + tmp[tmp.length - 1];
	}

	@Benchmark
	public double benchmarkSortObjects() {
		Double[] tmp = new Double[array.length];
		for (int j = 0; j < array.length; j++) {
			tmp[j] = array[j];
		}
		alg.sort(new ComparableArrayAdapter<>(), tmp);
		return tmp[0] + tmp[tmp.length - 1];
	}

	public static class JavaSort extends AbstractArraySortAlgorithm {
		@Override
		public <T> void sort(ArraySortAdapter<? super T> adapter, T data,
				int start, int end) {
			if (adapter instanceof DoubleArrayAdapter) {
				Arrays.sort((double[]) data, start, end);
			} else if (adapter instanceof ComparableArrayAdapter) {
				Arrays.sort((Object[]) data, start, end);
			}
		}
	}

	public static class OptimizedSort extends AbstractArraySortAlgorithm {
		@Override
		public <T> void sort(ArraySortAdapter<? super T> adapter, T data,
				int start, int end) {
			if (adapter instanceof DoubleArrayAdapter) {
				DoubleArrayQuickSort.STATIC.ascending((double[]) data, start,
						end);
			} else {
				DualPivotQuickSortBo5.STATIC.sort(adapter, data, start, end);
			}
		}
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(SortBenchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
