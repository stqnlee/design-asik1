package com.example.algorithms.divideconquer.bench;

import com.example.algorithms.divideconquer.metrics.Metrics;
import com.example.algorithms.divideconquer.sort.MergeSort;
import com.example.algorithms.divideconquer.sort.QuickSort;

import java.util.Locale;
import java.util.Random;

public class SortBench {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        int arraySize = args.length > 0 ? Integer.parseInt(args[0]) : 100_000;
        Integer[] baseArray = new Random(42).ints(arraySize).boxed().toArray(Integer[]::new);

        bench("mergesort", baseArray.clone());
        bench("quicksort", baseArray.clone());
    }

    private static void bench(String algorithmName, Integer[] inputArray) {
        Metrics performanceMetrics = new Metrics();
        long startTime = System.nanoTime();
        if (algorithmName.equals("mergesort")) {
            MergeSort.sort(inputArray, performanceMetrics);
        } else {
            QuickSort.sort(inputArray, performanceMetrics);
        }
        long endTime = System.nanoTime();
        System.out.printf("%s,%d,%.3f,%d,%d,%d%n",
                algorithmName, inputArray.length, (endTime - startTime) / 1e6,
                performanceMetrics.maxRecursionDepth, performanceMetrics.comparisonCount, performanceMetrics.moveCount);
    }
}
