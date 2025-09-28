package com.example.algorithms.divideconquer.geom;

import com.example.algorithms.divideconquer.metrics.Metrics;
import java.util.*;

public final class ClosestPair2D {
    public static final class Pt {
        public final double xCoordinate, yCoordinate;
        public Pt(double x, double y) { this.xCoordinate = x; this.yCoordinate = y; }
    }

    public static double closestDistance(List<Pt> points, Metrics performanceMetrics) {
        if (points.size() < 2) return Double.POSITIVE_INFINITY;
        List<Pt> pointsSortedByX = new ArrayList<>(points);
        pointsSortedByX.sort(Comparator.comparingDouble(p -> p.xCoordinate));
        List<Pt> pointsSortedByY = new ArrayList<>(pointsSortedByX);
        pointsSortedByY.sort(Comparator.comparingDouble(p -> p.yCoordinate));
        return divide(pointsSortedByX, pointsSortedByY, 0, pointsSortedByX.size(), performanceMetrics);
    }

    private static double divide(List<Pt> pointsSortedByX, List<Pt> pointsSortedByY, int startIndex, int endIndex, Metrics performanceMetrics) {
        int count = endIndex - startIndex;
        if (count <= 3) {
            double bestDistance = Double.POSITIVE_INFINITY;
            for (int index1 = startIndex; index1 < endIndex; index1++)
                for (int index2 = index1 + 1; index2 < endIndex; index2++) {
                    bestDistance = Math.min(bestDistance, dist(pointsSortedByX.get(index1), pointsSortedByX.get(index2), performanceMetrics));
                }
            pointsSortedByY.subList(0, pointsSortedByY.size()).sort(Comparator.comparingDouble(p -> p.yCoordinate));
            return bestDistance;
        }
        performanceMetrics.enter();
        int midIndex = startIndex + (count >>> 1);
        double midXCoordinate = pointsSortedByX.get(midIndex).xCoordinate;

        List<Pt> leftHalfSortedByY = new ArrayList<>(midIndex - startIndex);
        List<Pt> rightHalfSortedByY = new ArrayList<>(endIndex - midIndex);
        for (Pt point : pointsSortedByY) {
            if (point.xCoordinate < midXCoordinate || (point.xCoordinate == midXCoordinate && indexOf(pointsSortedByX, point) < midIndex)) {
                leftHalfSortedByY.add(point);
            } else {
                rightHalfSortedByY.add(point);
            }
        }

        double distanceLeft = divide(pointsSortedByX, leftHalfSortedByY, startIndex, midIndex, performanceMetrics);
        double distanceRight = divide(pointsSortedByX, rightHalfSortedByY, midIndex, endIndex, performanceMetrics);
        double minDistance = Math.min(distanceLeft, distanceRight);

        List<Pt> stripPoints = new ArrayList<>();
        for (Pt point : pointsSortedByY) if (Math.abs(point.xCoordinate - midXCoordinate) < minDistance) stripPoints.add(point);

        for (int i = 0; i < stripPoints.size(); i++) {
            for (int j = i + 1; j < stripPoints.size() && (stripPoints.get(j).yCoordinate - stripPoints.get(i).yCoordinate) < minDistance; j++) {
                minDistance = Math.min(minDistance, dist(stripPoints.get(i), stripPoints.get(j), performanceMetrics));
            }
        }
        performanceMetrics.leave();
        return minDistance;
    }

    private static int indexOf(List<Pt> pointsList, Pt point) { return pointsList.indexOf(point); }

    private static double dist(Pt pointA, Pt pointB, Metrics performanceMetrics) {
        performanceMetrics.addCmp(2);
        double deltaX = pointA.xCoordinate - pointB.xCoordinate, deltaY = pointA.yCoordinate - pointB.yCoordinate;
        return Math.hypot(deltaX, deltaY);
    }
}
