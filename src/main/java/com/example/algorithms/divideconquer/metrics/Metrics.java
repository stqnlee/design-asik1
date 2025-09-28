package com.example.algorithms.divideconquer.metrics;


public final class Metrics {
    public long comparisons;
    public long moves;
    public int maxDepth;


    private int depth;


    public void incCmp() { comparisons++; }
    public void addCmp(long k) { comparisons += k; }
    public void incMove() { moves++; }
    public void addMove(long k) { moves += k; }


    public void enter() {
        depth++;
        if (depth > maxDepth) maxDepth = depth;
    }
    public void leave() { depth--; }
}
