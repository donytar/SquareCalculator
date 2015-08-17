package com.donytar;

public class Point implements Comparable<Point> {

    public int x;
    public int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Point point) {
        if (x != point.x) {
            return x - point.x;
        }

        return y - point.y;
    }
}
