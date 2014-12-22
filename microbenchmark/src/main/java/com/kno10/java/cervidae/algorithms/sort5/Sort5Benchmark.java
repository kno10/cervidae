package com.kno10.java.cervidae.algorithms.sort5;

import java.util.Arrays;
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

import com.kno10.java.cervidae.adapter.arraylike.DoubleArrayAdapter;
import com.kno10.java.cervidae.adapter.arraylike.IntegerArrayAdapter;
import com.kno10.java.cervidae.algorithms.sort.DoubleArrayQuickSort;
import com.kno10.java.cervidae.algorithms.sort.InsertionSort;
import com.kno10.java.cervidae.algorithms.sort.IntegerArrayQuickSort;
import com.kno10.java.cervidae.comparator.PrimitiveComparators;

/**
 * Benchmark class for sorting algorithms.
 * 
 * @author Erich Schubert
 */
@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Sort5Benchmark {
	@Param
	Method method;

	@Param
	Pattern pattern;

	public enum Pattern {
		RANDOM, SORTED, REVERSE, EQUAL
	}

	public enum Method {
		NETWORK {
			{
				dsort = new DoubleNetworkSort5();
				isort = new IntegerNetworkSort5();
			}
		},
		INSERTIONA {
			{
				dsort = new DoubleInsertionSort5A();
				isort = new IntegerInsertionSort5A();
			}
		},
		INSERTIONB {
			{
				dsort = new DoubleInsertionSort5B();
				isort = new IntegerInsertionSort5B();
			}
		},
		INSERTIONC {
			{
				dsort = new DoubleInsertionSort5C();
				isort = new IntegerInsertionSort5C();
			}
		},
		INSERTIOND {
			{
				dsort = new DoubleInsertionSort5D();
				isort = new IntegerInsertionSort5D();
			}
		},
		INSERTIONE {
			{
				dsort = new DoubleInsertionSort5E();
				isort = new IntegerInsertionSort5E();
			}
		},
		INSERTIONF {
			{
				dsort = new DoubleInsertionSort5F();
				isort = new IntegerInsertionSort5F();
			}
		},
		INSERTIONG {
			{
				dsort = new DoubleInsertionSort5G();
				isort = new IntegerInsertionSort5G();
			}
		},
		INSERTIONH {
			{
				dsort = new DoubleInsertionSort5H();
				isort = new IntegerInsertionSort5H();
			}
		},
		DECISIONA {
			{
				dsort = new DoubleDecisionSort5A();
				isort = new IntegerDecisionSort5A();
			}
		},
		DECISIONB {
			{
				dsort = new DoubleDecisionSort5B();
				isort = new IntegerDecisionSort5B();
			}
		},
		BUBBLEA {
			{
				dsort = new DoubleBubbleSort5A();
				isort = new IntegerBubbleSort5A();
			}
		},
		BUBBLEB {
			{
				dsort = new DoubleBubbleSort5B();
				isort = new IntegerBubbleSort5B();
			}
		},
		JAVA { // This one is actually not just for 5 elements...
			@Override
			void sort(double[] data) {
				Arrays.sort(data);
			}

			@Override
			void sort(int[] data) {
				Arrays.sort(data);
			}
		},
		CERVIDAE5 {
			@Override
			void sort(double[] data) {
				InsertionSort.sort5(DoubleArrayAdapter.STATIC, data, 0, 1, 2,
						3, 4);
			}

			@Override
			void sort(int[] data) {
				InsertionSort.sort5(IntegerArrayAdapter.STATIC, data, 0, 1, 2,
						3, 4);
			}
		},
		CERVIDAE { // This one is actually not just for 5 elements...
			@Override
			void sort(double[] data) {
				DoubleArrayQuickSort.STATIC.sort(data, 0, 5,
						PrimitiveComparators.DOUBLE_ASCENDING);
			}

			@Override
			void sort(int[] data) {
				IntegerArrayQuickSort.STATIC.sort(data, 0, 5,
						PrimitiveComparators.INTEGER_ASCENDING);
			}
		},
		FINSERTA { // This one is actually not just for 5 elements...
			@Override
			void sort(double[] data) {
				DoubleArrayQuickSort.STATIC.insertionSortAscending(data, 0, 5);
			}

			@Override
			void sort(int[] data) {
				IntegerArrayQuickSort.STATIC.insertionSortAscending(data, 0, 5);
			}
		},
		FINSERTB { // This one is actually not just for 5 elements...
			@Override
			void sort(double[] data) {
				DoubleArrayQuickSort.STATIC.dualInsertionSort(data, 0, 5);
			}

			@Override
			void sort(int[] data) {
				IntegerArrayQuickSort.STATIC.dualInsertionSort(data, 0, 5);
			}
		},
		BINSERTIONA {
			{
				dsort = new DoubleBinaryInsertionSort5A();
				isort = new IntegerBinaryInsertionSort5A();
			}
		},
		BINSERTIONB {
			{
				dsort = new DoubleBinaryInsertionSort5B();
				isort = new IntegerBinaryInsertionSort5B();
			}
		},
		;

		DoubleSort5 dsort;

		IntegerSort5 isort;

		void sort(double[] data) {
			dsort.sort5(data, 0, 1, 2, 3, 4);
		}

		void sort(int[] data) {
			isort.sort5(data, 0, 1, 2, 3, 4);
		}
	}

	/**
	 * The unsorted data.
	 */
	double[] array;

	int[] iarray;

	@Setup
	public void setUp() {
		array = new double[5];
		iarray = new int[5];
		switch (pattern) {
		case EQUAL:
			// Keep zero.
			break;
		case RANDOM:
			Random rnd = new Random();
			for (int i = 0; i < 5; i++) {
				array[i] = rnd.nextDouble();
				iarray[i] = rnd.nextInt();
			}
			break;
		case REVERSE:
			for (int i = 0; i < 5; i++) {
				array[i] = 5 - i;
				iarray[i] = 5 - i;
			}
			break;
		case SORTED:
			for (int i = 0; i < 5; i++) {
				array[i] = i;
				iarray[i] = i;
			}
			break;
		default:
			throw new RuntimeException("Unknown pattern.");
		}
	}

	@Benchmark
	public double timeDoubleSort5() {
		double[] tmp = array.clone();
		method.sort(tmp);
		if (tmp[0] > tmp[1] || tmp[1] > tmp[2] || tmp[2] > tmp[3]
				|| tmp[3] > tmp[4]) {
			throw new RuntimeException("Sorting algorithm failed: " + method
					+ " on pattern: " + pattern);
		}
		return tmp[0] + tmp[tmp.length - 1];
	}

	@Benchmark
	public double timeIntegerSort5() {
		int[] tmp = iarray.clone();
		method.sort(tmp);
		if (tmp[0] > tmp[1] || tmp[1] > tmp[2] || tmp[2] > tmp[3]
				|| tmp[3] > tmp[4]) {
			throw new RuntimeException("Sorting algorithm failed: " + method
					+ " on pattern: " + pattern);
		}
		return tmp[0] ^ tmp[tmp.length - 1];
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(Sort5Benchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
