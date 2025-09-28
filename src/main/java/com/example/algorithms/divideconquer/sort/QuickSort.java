package com.example.algorithms.divideconquer.sort;


import com.example.algorithms.divideconquer.metrics.Metrics;
import java.util.concurrent.ThreadLocalRandom;


public final class QuickSort {
    private static final int INSERTION_CUTOFF = 20;


    public static <T extends Comparable<T>> void sort(T[] a, Metrics m) {
        qs(a, 0, a.length - 1, m);
    }


    private static <T extends Comparable<T>> void qs(T[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            int n = hi - lo + 1;
            if (n <= INSERTION_CUTOFF) { insertion(a, lo, hi, m); return; }


            m.enter();
            int p = partitionRandom(a, lo, hi, m);
            if (p - lo < hi - p) {
                qs(a, lo, p - 1, m);
                lo = p + 1;
            } else {
                qs(a, p + 1, hi, m);
                hi = p - 1;
            }
            m.leave();
        }
    }


    private static <T extends Comparable<T>> int partitionRandom(T[] a, int lo, int hi, Metrics m) {
        int r = ThreadLocalRandom.current().nextInt(lo, hi + 1);
        swap(a, r, hi, m);
        T pivot = a[hi];
        int i = lo - 1;
        for (int j = lo; j < hi; j++) {
            m.incCmp();
            if (a[j].compareTo(pivot) <= 0) {
                i++; swap(a, i, j, m);
            }
        }
        swap(a, i + 1, hi, m);
        return i + 1;
    }


    private static <T> void swap(T[] a, int i, int j, Metrics m) {
        if (i == j) return;
        T t = a[i]; a[i] = a[j]; a[j] = t; m.addMove(3);
    }


    private static <T extends Comparable<T>> void insertion(T[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            T key = a[i]; m.incMove();
            int j = i - 1;
            while (j >= lo) {
                m.incCmp();
                if (a[j].compareTo(key) > 0) { a[j+1] = a[j]; m.incMove(); j--; }
                else break;
            }
            a[j+1] = key; m.incMove();
        }
    }
}
