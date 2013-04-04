cervidae
========

Cervidae - Low-Level Data Structures and Algorithms


See also: [TODO](TODO.md) and [Sorting Documentation](Sorting.md)

Installation
============

Cervidae is a new effort, so we don't yet provide Maven packages, although this is the
plan on the long run. The current version is structures highly modular, to split code
that serves just as a proof-of-concept (e.g. slower sorting algorithms) and code that
is actually useful for certain users.

For building, you need a recent Maven and you must install a recent
[Google Caliper](https://code.google.com/p/caliper/) manually.
Google Caliper 1.0 is not yet released, so we do not know whether the 1.0 release
will be compatible.


Design goals
============

A key design goal of Cervidae is **flexibility**.

In order to achieve this, we try to perform a model-view-controller (MVC) like
separation of concerns, even for low-level data structures and algorithms.
The resulting code may look a bit complicated and not too much like object
oriented programming anymore, but actually it is a good idea to separate
data objects from code objects instead of intermixing data handling and code.

Roughly said, the general design employed looks like this:

Algorithm  ->  Adapter  ->  Data

so for example with sorting algorithms:

SortingAlgorithm  ->  ArrayListAdapter  ->  java.util.ArrayList

SortingAlgorithm  ->  DoubleArrayAdapter  ->  double[]

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


Similar projects
================

This project bears some similarity with e.g. Google Guava. However, in contrast to
these projects we are not interested in being able to write complex code with a few
lines, but instead focus on high performance and flexibility; even if this departs
with using popular APIs such as java.util.Iterator (see e.g. Google Guavas
PeekingIterator for a hack around limitations of that particular API).

As such, we are actually more similar to GNU Trove, which provides high-performance
primitive collections, and for these also has the need to provide non-standard
iterators ("Iterator.next()" would create a boxed object)


Contributing
============

We understand that this fairly new library with it's complicated codebase will
not be easy to contribute to. However, we really appreciate contributions!

When contributing, please agree to these terms:

1. the contributed code must be your own work, or you must ensure that the
true author of the code agrees to these terms.

2. you must license the code with the current effective license of cervidae

3. you must allow us to relicense the code at our discretion to any of the
following open source licenses: GPL-3, LGPL-3, AGPL-3, Apache
(as we have not yet decided on the final license, and it may turn out to be
beneficial to dual-license using the more common - for Java code - Apache license)

4. all required copyright statements are already included in your contribution.


License
=======
As of now, cervidae is LGPL-3 licensed, which can be read in detail at
http://www.gnu.org/licenses/lgpl.txt

Why LGPL? I do believe in the stronger requirements of
[Copyleft](https://en.wikipedia.org/wiki/Copyleft), while I do understand that
for a foundation library such as this, GPL is too strict.

However, since this apparently rules out the use of Cervidae for Apache projects
I do require all contributors to agree to the option of relicensing the library
to the Apache license on the long run, if there is a convincing use case.