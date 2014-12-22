package com.kno10.java.cervidae.datastructures;

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
import com.kno10.java.cervidae.datastructures.HeapBenchmarkUtil.Workload;
import com.kno10.java.cervidae.datastructures.heap2.DoubleMinHeap2;
import com.kno10.java.cervidae.datastructures.heap2.IntegerMinHeap2;
import com.kno10.java.cervidae.datastructures.heap24.DoubleMinHeap24;
import com.kno10.java.cervidae.datastructures.heap24.IntegerMinHeap24;
import com.kno10.java.cervidae.datastructures.heap3.DoubleMinHeap3;
import com.kno10.java.cervidae.datastructures.heap3.DoubleMinHeap3Loop;
import com.kno10.java.cervidae.datastructures.heap3.IntegerMinHeap3;
import com.kno10.java.cervidae.datastructures.heap3.IntegerMinHeap3Loop;
import com.kno10.java.cervidae.datastructures.heap4.DoubleMinHeap4;
import com.kno10.java.cervidae.datastructures.heap4.DoubleMinHeap4Loop;
import com.kno10.java.cervidae.datastructures.heap4.IntegerMinHeap4;
import com.kno10.java.cervidae.datastructures.heap4.IntegerMinHeap4Loop;
import com.kno10.java.cervidae.datastructures.heap5.DoubleMinHeap5;
import com.kno10.java.cervidae.datastructures.heap5.DoubleMinHeap5Loop;
import com.kno10.java.cervidae.datastructures.heap5.IntegerMinHeap5;
import com.kno10.java.cervidae.datastructures.heap5.IntegerMinHeap5Loop;

/**
 * Benchmark class for basic heaps.
 * 
 * @author Erich Schubert
 */
@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class HeapBenchmark {
	@Param
	Method method;

	@Param
	MacroPattern pattern;

	@Param({ ".01", ".1", ".5", "1" })
	double randomness;

	@Param({ "10", "100", "1000", "10000" })
	int size;

	@Param
	Workload workload;

	public enum Method {
		HEAP2 {
			{
				dheap = new DoubleMinHeap2();
				iheap = new IntegerMinHeap2();
			}
		},
		HEAP24 {
			{
				dheap = new DoubleMinHeap24();
				iheap = new IntegerMinHeap24();
			}
		},
		HEAP3 {
			{
				dheap = new DoubleMinHeap3();
				iheap = new IntegerMinHeap3();
			}
		},
		HEAP4 {
			{
				dheap = new DoubleMinHeap4();
				iheap = new IntegerMinHeap4();
			}
		},
		HEAP5 {
			{
				dheap = new DoubleMinHeap5();
				iheap = new IntegerMinHeap5();
			}
		},
		HEAP3L {
			{
				dheap = new DoubleMinHeap3Loop();
				iheap = new IntegerMinHeap3Loop();
			}
		},
		HEAP4L {
			{
				dheap = new DoubleMinHeap4Loop();
				iheap = new IntegerMinHeap4Loop();
			}
		},
		HEAP5L {
			{
				dheap = new DoubleMinHeap5Loop();
				iheap = new IntegerMinHeap5Loop();
			}
		},
		;

		DoubleHeap dheap;

		IntegerHeap iheap;

		double process(double[] data, Workload workload) {
			final int numbatches = workload.numBatches(data.length);
			int pos = 0;
			double cur = Double.NaN;
			for (int b = 0; b < numbatches; b++) {
				final int batchsize = workload.batchSize(b, data.length);
				if (batchsize > 0) {
					for (int i = 0; i < batchsize; i++, pos++) {
						dheap.add(data[pos]);
					}
				} else {
					cur = Double.NEGATIVE_INFINITY;
					// Negative
					for (int i = 0; i > batchsize; i--) {
						double next = dheap.poll();
						if (next < cur) {
							throw new RuntimeException("Heap inconsistent."
									+ dheap.getClass().getSimpleName() + " at "
									+ pos + "/" + dheap.size() + " "
									+ dheap.toString());
						}
						cur = next;
					}
				}
			}
			dheap.clear();
			return cur;
		}

		int process(int[] data, Workload workload) {
			final int numbatches = workload.numBatches(data.length);
			int pos = 0;
			int cur = Integer.MAX_VALUE;
			for (int b = 0; b < numbatches; b++) {
				final int batchsize = workload.batchSize(b, data.length);
				if (batchsize > 0) {
					for (int i = 0; i < batchsize; i++, pos++) {
						iheap.add(data[pos]);
					}
				} else {
					cur = Integer.MIN_VALUE;
					// Negative
					for (int i = 0; i > batchsize; i--) {
						int next = iheap.poll();
						if (next < cur) {
							throw new RuntimeException("Heap inconsistent."
									+ iheap.getClass().getSimpleName() + " at "
									+ pos + "/" + iheap.size() + " "
									+ iheap.toString());
						}
						cur = next;
					}
				}
			}
			iheap.clear();
			return cur;
		}
	}

	/**
	 * The unsorted data.
	 */
	double[] array;

	int[] iarray;

	@Setup
	public void setUp() {
		array = SortBenchmarkUtil.generateRandomDoubleData(size, pattern,
				randomness, 0L);
		iarray = SortBenchmarkUtil.generateRandomIntegerData(size, pattern,
				randomness, 0L);
	}

	@Benchmark
	public double benchmarkDoubleHeap() {
		double[] tmp = array.clone();
		return method.process(tmp, workload) + tmp[0];
	}

	@Benchmark
	public int benchmarkIntegerHeap() {
		int[] tmp = iarray.clone();
		return method.process(tmp, workload) + tmp[0];
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(HeapBenchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
