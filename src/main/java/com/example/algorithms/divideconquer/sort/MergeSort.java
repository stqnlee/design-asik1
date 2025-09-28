package com.example.algorithms.divideconquer.sort;


import com.example.algorithms.divideconquer.metrics.Metrics;
import java.util.Arrays;


public final class MergeSort {
    private static final int INSERTION_CUTOFF = 24;


    public static <T extends Comparable<T>> void sort(T[] a, Metrics m) {
        @SuppressWarnings("unchecked")
        T[] buf = (T[]) new Comparable[a.length];
        sort(a, buf, 0, a.length, m);
    }


    private static <T extends Comparable<T>> void sort(T[] a, T[] buf, int lo, int hi, Metrics m) {
        int n = hi - lo;
        if (n <= 1) return;
        if (n <= INSERTION_CUTOFF) {
            insertion(a, lo, hi, m);
            return;
        }
        m.enter();
        int mid = lo + (n >>> 1);
        sort(a, buf, lo, mid, m);
        sort(a, buf, mid, hi, m);
        merge(a, buf, lo, mid, hi, m);
        m.leave();
    }


    private static <T extends Comparable<T>> void merge(T[] a, T[] buf, int lo, int mid, int hi, Metrics m) {
        System.arraycopy(a, lo, buf, lo, hi - lo);
        int i = lo, j = mid, k = lo;
        while (i < mid && j < hi) {
            m.incCmp();
            if (compare(buf[i], buf[j]) <= 0) {
                a[k++] = buf[i++]; m.incMove();
            } else {
                a[k++] = buf[j++]; m.incMove();
            }
        }
        while (i < mid) { a[k++] = buf[i++]; m.incMove(); }
        while (j < hi) { a[k++] = buf[j++]; m.incMove(); }
    }


    private static <T extends Comparable<T>> void insertion(T[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i < hi; i++) {
            T key = a[i]; m.incMove();
            int j = i - 1;
            while (j >= lo) {
                m.incCmp();
                if (compare(a[j], key) > 0) { a[j+1] = a[j]; m.incMove(); j--; }
                else break;
            }
            a[j+1] = key; m.incMove();
        }
    }


    private static <T extends Comparable<T>> int compare(T x, T y) { return x.compareTo(y); }
}
