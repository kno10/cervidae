package com.kno10.java.cervidae.algorithms;

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

@Fork(1)
@Warmup(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class DecodeBitmapBenchmark {
	@Param({ "100" })
	int size;

	@Param({ "1", "2", "4", "8", "16", "32", "48" })
	int sparsity;

	private long[] data; // set by us, in setUp()

	int numbit; // initialized in setUp()

	int[] output; // initialized in setUp()

	java.util.Random r = new java.util.Random(0L);

	@Setup
	public void setUp() {
		data = new long[size];
		int setbitsmax = sparsity * size;
		for (int k = 0; k < setbitsmax; ++k) {
			// We don't need to care for speed in setup.
			int wnum = r.nextInt(size);
			int bnum = r.nextInt(64);
			data[wnum] |= (1L << bnum);
		}
		numbit = 0;
		for (long word : data) {
			numbit += Long.bitCount(word);
		}
		output = new int[numbit];
	}

	@Param
	Method method;

	public enum Method {
		NAIVE_SHIFT_DOWN {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					long word = data[k];
					final int wbase = k << 6;
					for (int b = 0; word != 0L; word >>>= 1, b++) {
						if ((word & 1L) != 0L) {
							output[nbit++] = wbase + b;
						}
					}
				}
				return nbit;
			}
		},
		NAIVE_SHIFT_UP {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					final long word = data[k];
					final int wbase = k << 6;
					for (int b = 0; b < 64; b++) {
						if ((word & (1L << b)) != 0L) {
							output[nbit++] = wbase + b;
						}
					}
				}
				return nbit;
			}
		},
		NAIVE_SHIFT_UP2 {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					long word = data[k];
					final int wbase = k << 6;
					for (int b = 0; word != 0L && b < 64; b++) {
						final long bit = 1L << b;
						if ((word & bit) != 0L) {
							output[nbit++] = wbase + b;
							word ^= bit;
						}
					}
				}
				return nbit;
			}
		},
		BITCOUNT {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					final int wbase = k << 6;
					long bitset = data[k];
					while (bitset != 0L) {
						final long lowestonebit = bitset & -bitset;
						output[nbit++] = wbase
								+ Long.bitCount(lowestonebit - 1);
						bitset ^= lowestonebit;
					}
				}
				return nbit;
			}
		},
		BITCOUNT_PUREJAVA {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					final int wbase = k << 6;
					long bitset = data[k];
					while (bitset != 0L) {
						final long lowestonebit = bitset & -bitset;
						output[nbit++] = wbase + javaBitCount(lowestonebit - 1);
						bitset ^= lowestonebit;
					}
				}
				return nbit;
			}
		},
		SHIFT_UP_TRAILINGZEROS {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					final int wbase = k << 6;
					long word = data[k];
					while (word != 0L) {
						final int ntz = Long.numberOfTrailingZeros(word);
						output[nbit++] = wbase + ntz;
						word ^= (1L << ntz);
					}
				}
				return nbit;
			}
		},
		SHIFT_DOWN_TRAILINGZEROS {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					long word = data[k];
					final int wbase = k << 6;
					int b = 0;
					while (word != 0L) {
						int trail = Long.numberOfTrailingZeros(word);
						word >>>= trail; // trail must not be 64 here!
						b += trail;
						output[nbit++] = wbase + b;
						word >>>= 1;
						++b;
					}
				}
				return nbit;
			}
		},
		SHIFT_UP_TRAILINGZEROS_PUREJAVA {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					final int wbase = k << 6;
					long word = data[k];
					while (word != 0L) {
						final int ntz = javaNumberOfTrailingZeros(word);
						output[nbit++] = wbase + ntz;
						word ^= (1L << ntz);
					}
				}
				return nbit;
			}
		},
		SHIFT_DOWN_TRAILINGZEROS_PUREJAVA {
			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					long word = data[k];
					final int wbase = k << 6;
					int b = 0;
					while (word != 0L) {
						int trail = javaNumberOfTrailingZeros(word);
						word >>>= trail; // trail must not be 64 here!
						b += trail;
						output[nbit++] = wbase + b;
						word >>>= 1;
						++b;
					}
				}
				return nbit;
			}
		},
		TABLE_BASED {
			final int[] table = { 0, 1, 2, 7, 3, 13, 8, 19, 4, 25, 14, 28, 9,
					34, 20, 40, 5, 17, 26, 38, 15, 46, 29, 48, 10, 31, 35, 54,
					21, 50, 41, 57, 63, 6, 12, 18, 24, 27, 33, 39, 16, 37, 45,
					47, 30, 53, 49, 56, 62, 11, 23, 32, 36, 44, 52, 55, 61, 22,
					43, 51, 60, 42, 59, 58 };

			@Override
			int scan(long[] data, int[] output) {
				int nbit = 0;
				for (int k = 0; k < data.length; ++k) {
					long bitset = data[k];
					final int wbase = k << 6;
					while (bitset != 0) {
						final long lowestonebit = bitset & -bitset;
						output[nbit++] = wbase
								+ table[(int) (lowestonebit * 0x0218a392cd3d5dbfL) >>> 58];
						bitset ^= lowestonebit;
					}
				}
				return nbit;
			}
		},
		;
		abstract int scan(long[] data, int[] output);
	}

	@Benchmark
	public int benchmarkBitmapDecode() {
		int nbit = 0;
		nbit = method.scan(data, output);
		if (nbit != numbit) {
			throw new RuntimeException("Expected " + numbit + " got " + nbit
					+ " for " + method);
		}
		return nbit; // ensure side effect.
	}

	// Copied from java.lang.Long#numberOfTrailingZeros
	// (to bypass intrinsics)
	public static int javaNumberOfTrailingZeros(long i) {
		// HD, Figure 5-14
		int x, y;
		if (i == 0) {
			return 64;
		}
		int n = 63;
		y = (int) i;
		if (y != 0) {
			n = n - 32;
			x = y;
		} else {
			x = (int) (i >>> 32);
		}
		y = x << 16;
		if (y != 0) {
			n = n - 16;
			x = y;
		}
		y = x << 8;
		if (y != 0) {
			n = n - 8;
			x = y;
		}
		y = x << 4;
		if (y != 0) {
			n = n - 4;
			x = y;
		}
		y = x << 2;
		if (y != 0) {
			n = n - 2;
			x = y;
		}
		return n - ((x << 1) >>> 31);
	}

	// Copied from java.lang.Long#bitCount
	// to bypass intrinsics
	public static int javaBitCount(long i) {
		// HD, Figure 5-14
		i = i - ((i >>> 1) & 0x5555555555555555L);
		i = (i & 0x3333333333333333L) + ((i >>> 2) & 0x3333333333333333L);
		i = (i + (i >>> 4)) & 0x0f0f0f0f0f0f0f0fL;
		i = i + (i >>> 8);
		i = i + (i >>> 16);
		i = i + (i >>> 32);
		return (int) i & 0x7f;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder() //
				.include(DecodeBitmapBenchmark.class.getSimpleName()) //
				.build();
		new Runner(opt).run();
	}
}
