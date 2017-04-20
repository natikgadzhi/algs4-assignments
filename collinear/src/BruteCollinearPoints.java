/**
 * Created by xnutsive on 19/04/2017.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<>();
    private int numberOfSegments, pointsCount;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("Points array is null");

        numberOfSegments = 0;
        pointsCount = points.length;
        Point[] sg = new Point[4];

        Point[] internalPoints = Arrays.copyOf(points, points.length);

        Arrays.sort(internalPoints, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int res = p1.compareTo(p2);
                if (res == 0) throw new IllegalArgumentException();
                return res;
            }
        });

        for (int a = 0; a < pointsCount; a++) {
            for (int b = a+1; b < pointsCount; b++) {
                for (int c = b+1; c < pointsCount; c++) {
                    for (int d = c+1; d < pointsCount; d++) {
                        if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[c])
                                && points[a].slopeTo(points[b]) == points[a].slopeTo(points[d])) {

                            sg[0] = points[a];
                            sg[1] = points[b];
                            sg[2] = points[c];
                            sg[3] = points[d];

                            Arrays.sort(sg);
                            segments.add(new LineSegment(sg[0], sg[3]));
                        }
                    }
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}