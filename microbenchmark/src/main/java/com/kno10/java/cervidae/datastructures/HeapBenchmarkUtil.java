package com.kno10.java.cervidae.datastructures;

public final class HeapBenchmarkUtil {
  enum Workload {
    LOAD_AND_EMPTY {
      @Override
      public int numBatches(int totalsize) {
        return 2;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return totalsize;
        } else {
          return -totalsize;
        }
      }
    },
    KNN10 {
      @Override
      public int numBatches(int totalsize) {
        return totalsize - 9;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return 10;
        } else {
          return -1;
        }
      }
    },
    KNN50 {
      @Override
      public int numBatches(int totalsize) {
        return totalsize - 49;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return 50;
        } else {
          return -1;
        }
      }
    },
    KNN100 {
      @Override
      public int numBatches(int totalsize) {
        return totalsize - 99;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return 100;
        } else {
          return -1;
        }
      }
    },
    ;

    abstract public int numBatches(int totalsize);

    abstract public int batchSize(int batchnum, int totalsize);
  };
}
