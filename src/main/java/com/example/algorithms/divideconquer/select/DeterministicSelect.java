package com.example.algorithms.divideconquer.select;

import com.example.algorithms.divideconquer.metrics.Metrics;

public final class DeterministicSelect {
    public static <T extends Comparable<T>> T select(T[] array, int startIndex, int endIndex, int targetIndex, Metrics performanceMetrics) {
        if (targetIndex < startIndex || targetIndex > endIndex) throw new IllegalArgumentException("targetIndex out of range");
        while (true) {
            if (startIndex == endIndex) return array[startIndex];
            performanceMetrics.enter();
            int newPivotIndex = pivotByMediansOf5(array, startIndex, endIndex, performanceMetrics);
            int partitionIndex = partition(array, startIndex, endIndex, newPivotIndex, performanceMetrics);
            if (targetIndex == partitionIndex) { performanceMetrics.leave(); return array[partitionIndex]; }
            else if (targetIndex < partitionIndex) { endIndex = partitionIndex - 1; }
            else { startIndex = partitionIndex + 1; }
            performanceMetrics.leave();
        }
    }

    private static <T extends Comparable<T>> int pivotByMediansOf5(T[] array, int startIndex, int endIndex, Metrics performanceMetrics) {
        int count = endIndex - startIndex + 1;
        if (count <= 5) {
            insertion(array, startIndex, endIndex, performanceMetrics);
            return startIndex + count / 2;
        }
        int writeIndex = startIndex;
        for (int iterator = startIndex; iterator <= endIndex; iterator += 5) {
            int rightBound = Math.min(iterator + 4, endIndex);
            insertion(array, iterator, rightBound, performanceMetrics);
            int medianIndex = iterator + (rightBound - iterator) / 2;
            swap(array, writeIndex++, medianIndex, performanceMetrics);
        }
        return selectIndex(array, startIndex, writeIndex - 1, (writeIndex - startIndex) / 2, performanceMetrics);
    }

    private static <T extends Comparable<T>> int selectIndex(T[] array, int startIndex, int endIndex, int targetOffset, Metrics performanceMetrics) {
        while (true) {
            if (startIndex == endIndex) return startIndex;
            int newPivotIndex = pivotByMediansOf5(array, startIndex, endIndex, performanceMetrics);
            int partitionIndex = partition(array, startIndex, endIndex, newPivotIndex, performanceMetrics);
            int partitionRank = partitionIndex - startIndex;
            if (targetOffset == partitionRank) return partitionIndex;
            else if (targetOffset < partitionRank) endIndex = partitionIndex - 1;
            else { targetOffset -= partitionRank + 1; startIndex = partitionIndex + 1; }
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int startIndex, int endIndex, int initialPivotIndex, Metrics performanceMetrics) {
        swap(array, initialPivotIndex, endIndex, performanceMetrics);
        T pivotValue = array[endIndex];
        int storeIndex = startIndex;
        for (int iterator = startIndex; iterator < endIndex; iterator++) {
            performanceMetrics.incCmp();
            if (array[iterator].compareTo(pivotValue) < 0) {
                swap(array, storeIndex, iterator, performanceMetrics); storeIndex++;
            }
        }
        swap(array, storeIndex, endIndex, performanceMetrics);
        return storeIndex;
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

    private static <T> void swap(T[] array, int index1, int index2, Metrics performanceMetrics) {
        if (index1 == index2) return; T temp = array[index1]; array[index1] = array[index2]; array[index2] = temp; performanceMetrics.addMove(3);
    }
}
