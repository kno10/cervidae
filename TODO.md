Cervidae TODO list
==================

1. QuickSort algorithms don't necessarily require `length`, which can be expensive
with a disk- or database backed array.

2. TimSort would be nice to have

3. Heaps. Convert the existing ELKI templates to cervidae templating.

4. Benchmarking! Caliper seems to be a good choice, but is currently not available
via maven, plus it requires literally tons of dependencies.