cervidae
========

Cervidae - Low-Level Data Structures and Algorithms


Design goals
============

A key design goal of Cervidae is **flexibility**.

In order to achieve this, we try to perform a model-view-controller (MVC) like
separation of concerns, even for low-level data structures and algorithms.
The resulting code may look a bit complicated and not too much like object
oriented programming anymore, but actually it is a good idea to separate
data objects from code objects instead of intermixing data handling and code.

Roughly said, the general design employed looks like this:

Algorithm  ->  Controller  ->  Data

so for example with sorting algorithms:

SortingAlgorithm  ->  ArrayListController  ->  java.util.ArrayList

SortingAlgorithm  ->  DoubleArrayController  ->  double[]

in contrast to the Java Collections API, by separating algorithms and the
data storage, the same sorting algorithm can be applied to different datastructures,
and only needs to be implemented once.


Benchmarking
============

Besides flexibility, a key interest of this project is to find good implementations
of basic algorithms, with the most obvious example being sorting. There are dozens
of sorting algorithms, and it is not at all obvious which one is best. In fact, it
is common knowledge that QuickSort, while having worst case quadratic performance,
usually outperforms "optimal" sorting algorithms such as HeapSort. Dual-Pivot
QuickSort was believed to be strictly more expensive than regular QuickSort. Yet,
experiments have confirmed that it can offer benefits, and with OpenJDK 7, the
default sorting algorithm is such a Dual-Pivot QuickSort.