package com.example.algorithms.divideconquer.geom;


import com.example.algorithms.divideconquer.metrics.Metrics;
import java.util.*;


public final class ClosestPair2D {
    public static final class Pt {
        public final double x, y;
        public Pt(double x, double y) { this.x = x; this.y = y; }
    }


    public static double closestDistance(List<Pt> pts, Metrics m) {
        if (pts.size() < 2) return Double.POSITIVE_INFINITY;
        List<Pt> byX = new ArrayList<>(pts);
        byX.sort(Comparator.comparingDouble(p -> p.x));
        List<Pt> byY = new ArrayList<>(byX);
        byY.sort(Comparator.comparingDouble(p -> p.y));
        return divide(byX, byY, 0, byX.size(), m);
    }


    // Work on half-open interval [lo, hi)
    private static double divide(List<Pt> byX, List<Pt> byY, int lo, int hi, Metrics m) {
        int n = hi - lo;
        if (n <= 3) {
            double best = Double.POSITIVE_INFINITY;
            for (int i = lo; i < hi; i++)
                for (int j = i + 1; j < hi; j++) {
                    best = Math.min(best, dist(byX.get(i), byX.get(j), m));
                }
            byY.subList(0, byY.size()).sort(Comparator.comparingDouble(p -> p.y));
            return best;
        }
        m.enter();
        int mid = lo + (n >>> 1);
        double midX = byX.get(mid).x;


// Split byY into leftY/rightY while keeping y-order
        List<Pt> leftY = new ArrayList<>(mid - lo);
        List<Pt> rightY = new ArrayList<>(hi - mid);
        for (Pt p : byY) { if (p.x < midX || (p.x == midX && indexOf(byX, p) < mid)) leftY.add(p); else rightY.add(p); }


        double dl = divide(byX, leftY, lo, mid, m);
        double dr = divide(byX, rightY, mid, hi, m);
        double d = Math.min(dl, dr);


// Build strip: points within d of midX, already sorted by y
        List<Pt> strip = new ArrayList<>();
        for (Pt p : byY) if (Math.abs(p.x - midX) < d) strip.add(p);


// Check up to 7 next points by y
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i + 1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < d; j++) {
                d = Math.min(d, dist(strip.get(i), strip.get(j), m));
            }
        }
        m.leave();
        return d;
    }


    private static int indexOf(List<Pt> byX, Pt p) { return byX.indexOf(p); }


    private static double dist(Pt a, Pt b, Metrics m) {
        m.addCmp(2);
        double dx = a.x - b.x, dy = a.y - b.y;
        return Math.hypot(dx, dy);
    }
}
