package com.example.algorithms.divideconquer.bench;

import com.example.algorithms.divideconquer.metrics.Metrics;
import com.example.algorithms.divideconquer.sort.MergeSort;
import com.example.algorithms.divideconquer.sort.QuickSort;

import java.util.Locale;
import java.util.Random;

public class SortBench {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 100_000;
        Integer[] base = new Random(42).ints(n).boxed().toArray(Integer[]::new);

        bench("mergesort", base.clone());
        bench("quicksort", base.clone());
    }

    private static void bench(String name, Integer[] a) {
        Metrics m = new Metrics();
        long t0 = System.nanoTime();
        if (name.equals("mergesort")) {
            MergeSort.sort(a, m);
        } else {
            QuickSort.sort(a, m);
        }
        long t1 = System.nanoTime();
        System.out.printf("%s,%d,%.3f,%d,%d,%d%n",
                name, a.length, (t1 - t0) / 1e6, m.maxDepth, m.comparisons, m.moves);
    }
}
