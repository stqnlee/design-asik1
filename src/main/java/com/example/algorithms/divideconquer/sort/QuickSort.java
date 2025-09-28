package com.example.algorithms.divideconquer.sort;

import com.example.algorithms.divideconquer.metrics.Metrics;
import java.util.concurrent.ThreadLocalRandom;

public final class QuickSort {
    private static final int INSERTION_CUTOFF = 20;

    public static <T extends Comparable<T>> void sort(T[] array, Metrics performanceMetrics) {
        qs(array, 0, array.length - 1, performanceMetrics);
    }

    private static <T extends Comparable<T>> void qs(T[] array, int startIndex, int endIndex, Metrics performanceMetrics) {
        while (startIndex < endIndex) {
            int count = endIndex - startIndex + 1;
            if (count <= INSERTION_CUTOFF) { insertion(array, startIndex, endIndex, performanceMetrics); return; }

            performanceMetrics.enter();
            int partitionIndex = partitionRandom(array, startIndex, endIndex, performanceMetrics);
            if (partitionIndex - startIndex < endIndex - partitionIndex) {
                qs(array, startIndex, partitionIndex - 1, performanceMetrics);
                startIndex = partitionIndex + 1;
            } else {
                qs(array, partitionIndex + 1, endIndex, performanceMetrics);
                endIndex = partitionIndex - 1;
            }
            performanceMetrics.leave();
        }
    }

    private static <T extends Comparable<T>> int partitionRandom(T[] array, int startIndex, int endIndex, Metrics performanceMetrics) {
        int randomIndex = ThreadLocalRandom.current().nextInt(startIndex, endIndex + 1);
        swap(array, randomIndex, endIndex, performanceMetrics);
        T pivotValue = array[endIndex];
        int lesserElementIndex = startIndex - 1;
        for (int iterator = startIndex; iterator < endIndex; iterator++) {
            performanceMetrics.incCmp();
            if (array[iterator].compareTo(pivotValue) <= 0) {
                lesserElementIndex++; swap(array, lesserElementIndex, iterator, performanceMetrics);
            }
        }
        swap(array, lesserElementIndex + 1, endIndex, performanceMetrics);
        return lesserElementIndex + 1;
    }

    private static <T> void swap(T[] array, int index1, int index2, Metrics performanceMetrics) {
        if (index1 == index2) return;
        T temp = array[index1]; array[index1] = array[index2]; array[index2] = temp; performanceMetrics.addMove(3);
    }

    private static <T extends Comparable<T>> void insertion(T[] array, int startIndex, int endIndex, Metrics performanceMetrics) {
        for (int outerLoopIndex = startIndex + 1; outerLoopIndex <= endIndex; outerLoopIndex++) {
            T currentValue = array[outerLoopIndex]; performanceMetrics.incMove();
            int innerLoopIndex = outerLoopIndex - 1;
            while (innerLoopIndex >= startIndex) {
                performanceMetrics.incCmp();
                if (array[innerLoopIndex].compareTo(currentValue) > 0) { array[innerLoopIndex + 1] = array[innerLoopIndex]; performanceMetrics.incMove(); innerLoopIndex--; }
                else break;
            }
            array[innerLoopIndex + 1] = currentValue; performanceMetrics.incMove();
        }
    }
}
