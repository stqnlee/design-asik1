package com.example.algorithms.divideconquer.select;


import com.example.algorithms.divideconquer.metrics.Metrics;
import java.util.Arrays;


public final class DeterministicSelect {
    public static <T extends Comparable<T>> T select(T[] a, int lo, int hi, int k, Metrics m) {
        if (k < lo || k > hi) throw new IllegalArgumentException("k out of range");
        while (true) {
            if (lo == hi) return a[lo];
            m.enter();
            int pivotIndex = pivotByMediansOf5(a, lo, hi, m);
            int p = partition(a, lo, hi, pivotIndex, m);
            if (k == p) { m.leave(); return a[p]; }
            else if (k < p) { hi = p - 1; }
            else { lo = p + 1; }
            m.leave();
        }
    }


    private static <T extends Comparable<T>> int pivotByMediansOf5(T[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertion(a, lo, hi, m);
            return lo + n/2;
        }
        int write = lo;
        for (int i = lo; i <= hi; i += 5) {
            int r = Math.min(i + 4, hi);
            insertion(a, i, r, m);
            int median = i + (r - i) / 2;
            swap(a, write++, median, m);
        }
        return selectIndex(a, lo, write - 1, (write - lo)/2, m);
    }


    private static <T extends Comparable<T>> int selectIndex(T[] a, int lo, int hi, int kOffset, Metrics m) {
        while (true) {
            if (lo == hi) return lo;
            int pivotIndex = pivotByMediansOf5(a, lo, hi, m);
            int p = partition(a, lo, hi, pivotIndex, m);
            int rank = p - lo;
            if (kOffset == rank) return p;
            else if (kOffset < rank) hi = p - 1;
            else { kOffset -= rank + 1; lo = p + 1; }
        }
    }


    private static <T extends Comparable<T>> int partition(T[] a, int lo, int hi, int pivotIndex, Metrics m) {
        swap(a, pivotIndex, hi, m);
        T pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            m.incCmp();
            if (a[j].compareTo(pivot) < 0) {
                swap(a, i, j, m); i++;
            }
        }
        swap(a, i, hi, m);
        return i;
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


    private static <T> void swap(T[] a, int i, int j, Metrics m) {
        if (i == j) return; T t = a[i]; a[i] = a[j]; a[j] = t; m.addMove(3);
    }
}
