package com.kno10.java.cervidae.algorithms.select;

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

import com.kno10.java.cervidae.adapter.arraylike.ComparableArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;
import com.kno10.java.cervidae.algorithms.sort.SortBenchmarkUtil;
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
public class SelectBenchmark {
	@Param({ "10", "100", "1000", "10000" })
	int size;

	@Param({ ".01", ".1", ".5", "1" })
	double randomness;

	static final String BASE = "com.kno10.java.cervidae.algorithms.select.";

	@Param({ //
	BASE + "QuickSelectBo5", //
			BASE + "QuickSelectBo3", //
			BASE + "QuickSelectTextbook",//
	})
	String algname;

	ArraySelectionAlgorithm alg;

	@Param({ "INCREASING", "DECREASING", "SAW8", "DECINC", "CONSTANT" })
	MacroPattern pattern;

	/**
	 * The unsorted data.
	 */
	double[] array;

	/**
	 * The rank to select.
	 */
	int rank;

	@Setup
	public void setUp() {
		try {
			alg = (ArraySelectionAlgorithm) Class.forName(algname)
					.newInstance();
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
		rank = (new Random(1L)).nextInt(size);
	}

	@Benchmark
	public double benchmarkSelectPrimitiveDoubles() {
		double[] tmp = array.clone();
		alg.select(DoubleArrayAdapter.STATIC, tmp, rank);
		return tmp[rank];
	}

	@Benchmark
	public double benchmarkSelectObjects() {
		Double[] tmp = new Double[array.length];
		for (int j = 0; j < array.length; j++) {
			tmp[j] = array[j];
		}
		alg.select(new ComparableArrayAdapter<>(), tmp, rank);
		return tmp[rank];
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(SelectBenchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
