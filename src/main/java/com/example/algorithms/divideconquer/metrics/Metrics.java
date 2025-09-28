package com.example.algorithms.divideconquer.metrics;

public final class Metrics {
    public long comparisonCount;
    public long moveCount;
    public int maxRecursionDepth;

    private int currentDepth;

    public void incCmp() { comparisonCount++; }
    public void addCmp(long amount) { comparisonCount += amount; }
    public void incMove() { moveCount++; }
    public void addMove(long amount) { moveCount += amount; }

    public void enter() {
        currentDepth++;
        if (currentDepth > maxRecursionDepth) maxRecursionDepth = currentDepth;
    }
    public void leave() { currentDepth--; }
}
