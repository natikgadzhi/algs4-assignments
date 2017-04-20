import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new NullPointerException("Points array is null");

        Point[] internalPoints = Arrays.copyOf(points, points.length);
        int length = 0;

        Arrays.sort(internalPoints, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                int res = p1.compareTo(p2);
                if (res == 0) throw new IllegalArgumentException();
                return res;
            }
        });

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(internalPoints, points[i].slopeOrder());

            for (int j = 1; j < internalPoints.length - 1; j++) {
                if (points[i].slopeTo(internalPoints[j]) == points[i].slopeTo(internalPoints[j+1])) {
                    length++;
                }
                else {

                    if (length >= 4)
                        segments.add(new LineSegment(points[i], internalPoints[j]));

                    length = 2;
                }
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
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