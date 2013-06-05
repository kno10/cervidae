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
        if (totalsize < 10) { return 1; }
        return ((totalsize - 10) << 1) + 1;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return Math.min(totalsize, 10);
        } else {
          return (batchnum % 2 == 1) ? -1 : 1;
        }
      }
    },
    KNN50 {
      @Override
      public int numBatches(int totalsize) {
        if (totalsize < 50) { return 1; }
        return ((totalsize - 50) << 1) + 1;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return Math.min(totalsize, 50);
        } else {
          return (batchnum % 2 == 1) ? -1 : 1;
        }
      }
    },
    KNN100 {
      @Override
      public int numBatches(int totalsize) {
        if (totalsize < 100) { return 1; }
        return ((totalsize - 100) << 1) + 1;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return Math.min(totalsize, 100);
        } else {
          return (batchnum % 2 == 1) ? -1 : 1;
        }
      }
    },
    KNN200 {
      @Override
      public int numBatches(int totalsize) {
        if (totalsize < 200) { return 1; }
        return ((totalsize - 200) << 1) + 1;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return Math.min(totalsize, 200);
        } else {
          return (batchnum % 2 == 1) ? -1 : 1;
        }
      }
    },
    KNN500 {
      @Override
      public int numBatches(int totalsize) {
        if (totalsize < 200) { return 1; }
        return ((totalsize - 200) << 1) + 1;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return Math.min(totalsize, 200);
        } else {
          return (batchnum % 2 == 1) ? -1 : 1;
        }
      }
    },
    KNN8P {
      @Override
      public int numBatches(int totalsize) {
        return 1 + 2 * 14;
      }

      @Override
      public int batchSize(int batchnum, int totalsize) {
        if (batchnum == 0) {
          return (totalsize >> 3);
        } else {
          return (batchnum % 2 == 1) ? -(totalsize >> 4) : (totalsize >> 4);
        }
      }
    },
    ;

    abstract public int numBatches(int totalsize);

    abstract public int batchSize(int batchnum, int totalsize);
  };
}
