package com.example.algorithms.divideconquer;

import com.example.algorithms.divideconquer.metrics.Metrics;
import com.example.algorithms.divideconquer.sort.MergeSort;
import com.example.algorithms.divideconquer.sort.QuickSort;
import com.example.algorithms.divideconquer.select.DeterministicSelect;
import com.example.algorithms.divideconquer.geom.ClosestPair2D;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class AllAlgorithmsTest {

    @Test
    void mergesort_basic() {
        Integer[] testArray = {5,4,3,2,1,0};
        Metrics testMetrics = new Metrics();
        MergeSort.sort(testArray, testMetrics);
        assertArrayEquals(new Integer[]{0,1,2,3,4,5}, testArray);
    }

    @Test
    void mergesort_depth_positive_largeN() {
        int arraySize = 200;
        Integer[] testArray = new Random(7).ints(arraySize, -1000, 1000).boxed().toArray(Integer[]::new);
        Metrics testMetrics = new Metrics();
        MergeSort.sort(testArray, testMetrics);
        for (int index = 1; index < testArray.length; index++) assertTrue(testArray[index-1] <= testArray[index]);
        assertTrue(testMetrics.maxRecursionDepth > 0, "Ожидалась положительная глубина рекурсии для n > cutoff");
    }

    @Test
    void quicksort_randomized_depth_is_log_n_typical() {
        Integer[] testArray = new Random(1).ints(2000, -1_000_000, 1_000_000).boxed().toArray(Integer[]::new);
        Metrics testMetrics = new Metrics();
        QuickSort.sort(testArray, testMetrics);
        for (int index = 1; index < testArray.length; index++) assertTrue(testArray[index-1] <= testArray[index]);
        int log2ofSize = (int)(Math.log(testArray.length) / Math.log(2));
        assertTrue(testMetrics.maxRecursionDepth <= 2 * log2ofSize + 10, "Ожидалась типичная O(log n) глубина стека");
    }

    @Test
    void deterministic_select_matches_java_sort_kth() {
        Integer[] testArray = new Random(2).ints(501, -999, 999).boxed().toArray(Integer[]::new);
        int kthElementIndex = 250;
        Metrics testMetrics = new Metrics();
        Integer selectedAnswer = DeterministicSelect.select(testArray.clone(), 0, testArray.length - 1, kthElementIndex, testMetrics);
        Integer[] sortedArray = testArray.clone(); Arrays.sort(sortedArray);
        assertEquals(sortedArray[kthElementIndex], selectedAnswer);
    }

    @Test
    void closest_pair_small_and_compare_naive() {
        List<ClosestPair2D.Pt> testPoints = Arrays.asList(
                new ClosestPair2D.Pt(0,0),
                new ClosestPair2D.Pt(1,1),
                new ClosestPair2D.Pt(2,2),
                new ClosestPair2D.Pt(2.1,2.05)
        );
        Metrics testMetrics = new Metrics();
        double distanceResult = ClosestPair2D.closestDistance(testPoints, testMetrics);
        assertEquals(Math.hypot(0.1, 0.05), distanceResult, 1e-9);
    }
}
