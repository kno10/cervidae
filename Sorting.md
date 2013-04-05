Sorting Algorithms in Cervidae
==============================

For cervidae, we implemented a number of well-known sorting
algorithm variants for comparison. In contrast to most similar
efforts, we tried to make our implementation highly generic,
so that it can be used with arbitrary data types and backing
storages. This makes is much more *flexible* than e.g. the
existing sorting functionality in the Java SDK.


Benchmark Results
=================

Here are some recent benchmarking results:


Primitive Doubles
-----------------

On this data type, the Java SDK code (Arrays.sort) is highly optimized
and it is no surprise that none of our current implementations can compete.

So when sorting primitive doubles, it currently is a good idea to stick to the JDK.

However, when e.g. sorting an array of integers not by size, but by comparing referenced
objects, the JDK can no longer be used. This is where the cervidae code becomes relevant.

For this experiment, we benchmarked the sorting of 100000 objects, with 20% randomness
and a primitive pattern (constant, decreasing, increasing, decreasing and increasing, sawtooth)
underneath. Note that the constant pattern effectively is 100% random.

```
Algorithm              CONSTANT     INCREASING   DECREASING   DECINC       SAWTOOTH
JDK Arrays.sort        10214.92 ms   9468.91 ms  10165.42 ms  10099.47 ms   9352.75 ms
DoubleArrayQuickSort   12147.44 ms  11216.83 ms  11375.65 ms  11307.08 ms  11642.28 ms
DualPivotQuickSortBo5  13045.07 ms  12355.88 ms  12347.59 ms  12490.66 ms  13474.55 ms
```

As you can see, the Java SDK sort in particular optimizes pre-sorted runs in the data very well,
as they occur in the decreasing and increasing patterns. On this kind of data, it supposedly
switches to a merge sort instead of a QuickSort.
On the more complex decinc and sawtooth patterns, the difference is much smaller.

The DualPivotQuickSortBo5 is a datatype agnostic implementation. It is good to see that
this indirection is largely optimized away by the hotspot compiler and comes at less than 5% cost.
In contrast to the other two algorithms benchmarked, it can actually use comparators.


Arrays of Comparable
--------------------

Comparable objects is the more interesting case for optimization. Comparables can represent
anything that can be sorted. However, due to this indirection, it will generally be more
expensive than sorting primitive types.

For the benchmark, we chose one of the simples objects: Double, the boxed version of the
primitive data type we used above. Again, we benchmarked the sorting of 100000 objects,
with 20% randomness added to the primitive pattern underneath.

```
Algorithm             CONSTANT    INCREASING  DECREASING  DECINC      SAW8
JDK Collections.sort  40708.42 ms 27997.95 ms 29283.89 ms 28827.52 ms 31940.26 ms
DualPivotQuickSortBo5 30480.66 ms 23279.76 ms 23443.27 ms 23103.37 ms 23388.52 ms
```

So as you can see, this implementation is competitive with the JDK sorting algorithm at
least on this benchmark data set (note that JDK supposedly uses TimSort here, which
may be better at largely pre-sorted data. See the TODO entry for adding TimSort!)


TODO: add benchmarks that count the number of comparisons performed!



Advanced Scenarios
==================

Cervidae sorting allows for advanced scenarios that are not trivially possible with stock Java.

For example, we may have our data in an existing data structure. For simplicity, let us assume
this is just something like an array or an array list. However, because other parts of the code
keep integer offsets into this list, we must not modify it.

Instead, we want to perform an *external* sort of this data structure, or parts of it.

We can do this trivially with the Cervidae sorting API:

```java
// Raw data, that should not be modified:
ArrayList<SomeObject> storage = ...;
// Or e.g. a trove TIntHashMap<SomeObject>

// Setup an array of integer indexes for the
// interval to be sorted (start to end):
int start = 100, end = 200;
int[] indexes = new int[end - start];
for (int i = 0; i < end; i++) indexes[i] = start + i;

IntegerArrayQuickSort.STATIC.sort(indexes, new IntegerComparator() {
    @Override
    public int compare(int i, int j) {
        // Compare objects at positions i, j
        return storage.get(i).compareByWhatever(storage.get(j));
    }
});

// Invoke some method that needs to know the integer index of the 10th
// smallest object in the chosen interval:
invokeSomeMethod(indexes[10]);
```

In order to get the same results with the stock Java API, you would need to either
- sort an array of `Integer[]` using a custom comparator (additional boxing memory overhead!)
- store the array position in the objects themselves
- copy the relevant part of the array, and sort the copy

Or in above example, assume the `storage` is e.g. a SQLite database, and you cannot keep
all the data in memory all the time.
