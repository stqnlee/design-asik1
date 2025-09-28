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
        Integer[] a = {5,4,3,2,1,0};
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        assertArrayEquals(new Integer[]{0,1,2,3,4,5}, a);
    }

    @Test
    void mergesort_depth_positive_largeN() {
        int n = 200;
        Integer[] a = new Random(7).ints(n, -1000, 1000).boxed().toArray(Integer[]::new);
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        for (int i = 1; i < a.length; i++) assertTrue(a[i-1] <= a[i]);
        assertTrue(m.maxDepth > 0, "Ожидалась положительная глубина рекурсии для n > cutoff");
    }

    @Test
    void quicksort_randomized_depth_is_log_n_typical() {
        Integer[] a = new Random(1).ints(2000, -1_000_000, 1_000_000).boxed().toArray(Integer[]::new);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        for (int i = 1; i < a.length; i++) assertTrue(a[i-1] <= a[i]);
        int log2n = (int)(Math.log(a.length) / Math.log(2));
        assertTrue(m.maxDepth <= 2 * log2n + 10, "Ожидалась типичная O(log n) глубина стека");
    }

    @Test
    void deterministic_select_matches_java_sort_kth() {
        Integer[] a = new Random(2).ints(501, -999, 999).boxed().toArray(Integer[]::new);
        int k = 250;
        Metrics m = new Metrics();
        Integer ans = DeterministicSelect.select(a.clone(), 0, a.length - 1, k, m);
        Integer[] b = a.clone(); Arrays.sort(b);
        assertEquals(b[k], ans);
    }

    @Test
    void closest_pair_small_and_compare_naive() {
        List<ClosestPair2D.Pt> pts = Arrays.asList(
                new ClosestPair2D.Pt(0,0),
                new ClosestPair2D.Pt(1,1),
                new ClosestPair2D.Pt(2,2),
                new ClosestPair2D.Pt(2.1,2.05)
        );
        Metrics m = new Metrics();
        double d = ClosestPair2D.closestDistance(pts, m);
        assertEquals(Math.hypot(0.1, 0.05), d, 1e-9);
    }
}
