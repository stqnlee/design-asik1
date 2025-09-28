package com.example.algorithms.divideconquer.sort;

import com.example.algorithms.divideconquer.metrics.Metrics;

public final class MergeSort {
    private static final int INSERTION_CUTOFF = 24;

    public static <T extends Comparable<T>> void sort(T[] array, Metrics performanceMetrics) {
        @SuppressWarnings("unchecked")
        T[] buffer = (T[]) new Comparable[array.length];
        sort(array, buffer, 0, array.length, performanceMetrics);
    }

    private static <T extends Comparable<T>> void sort(T[] array, T[] buffer, int startIndex, int endIndex, Metrics performanceMetrics) {
        int count = endIndex - startIndex;
        if (count <= 1) return;
        if (count <= INSERTION_CUTOFF) {
            insertion(array, startIndex, endIndex, performanceMetrics);
            return;
        }
        performanceMetrics.enter();
        int midIndex = startIndex + (count >>> 1);
        sort(array, buffer, startIndex, midIndex, performanceMetrics);
        sort(array, buffer, midIndex, endIndex, performanceMetrics);
        merge(array, buffer, startIndex, midIndex, endIndex, performanceMetrics);
        performanceMetrics.leave();
    }

    private static <T extends Comparable<T>> void merge(T[] array, T[] buffer, int startIndex, int midIndex, int endIndex, Metrics performanceMetrics) {
        System.arraycopy(array, startIndex, buffer, startIndex, endIndex - startIndex);
        int leftPointer = startIndex, rightPointer = midIndex, writePointer = startIndex;
        while (leftPointer < midIndex && rightPointer < endIndex) {
            performanceMetrics.incCmp();
            if (compare(buffer[leftPointer], buffer[rightPointer]) <= 0) {
                array[writePointer++] = buffer[leftPointer++]; performanceMetrics.incMove();
            } else {
                array[writePointer++] = buffer[rightPointer++]; performanceMetrics.incMove();
            }
        }
        while (leftPointer < midIndex) { array[writePointer++] = buffer[leftPointer++]; performanceMetrics.incMove(); }
        while (rightPointer < endIndex) { array[writePointer++] = buffer[rightPointer++]; performanceMetrics.incMove(); }
    }

    private static <T extends Comparable<T>> void insertion(T[] array, int startIndex, int endIndex, Metrics performanceMetrics) {
        for (int outerLoopIndex = startIndex + 1; outerLoopIndex < endIndex; outerLoopIndex++) {
            T currentValue = array[outerLoopIndex]; performanceMetrics.incMove();
            int innerLoopIndex = outerLoopIndex - 1;
            while (innerLoopIndex >= startIndex) {
                performanceMetrics.incCmp();
                if (compare(array[innerLoopIndex], currentValue) > 0) { array[innerLoopIndex + 1] = array[innerLoopIndex]; performanceMetrics.incMove(); innerLoopIndex--; }
                else break;
            }
            array[innerLoopIndex + 1] = currentValue; performanceMetrics.incMove();
        }
    }

    private static <T extends Comparable<T>> int compare(T val1, T val2) { return val1.compareTo(val2); }
}
