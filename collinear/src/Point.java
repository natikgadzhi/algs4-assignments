/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException("Can't get a slope to null");

        if (that.y == this.y) {
            if (that.x == this.x) return Double.NEGATIVE_INFINITY;
            else return +0.0;
        }
        else if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }
        else {
            return (double) (that.y - this.y)/(that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException("Can't compare a point to null");

        if (this.y != that.y) {
            return this.y - that.y;
        }
        else {
            return this.x - that.x;
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                if (o1 == null) throw new NullPointerException("First point arg is null");
                if (o2 == null) throw new NullPointerException("Second point arg is null");
                double slope1 = slopeTo(o1);
                double slope2 = slopeTo(o2);

                if (slope1 > slope2) {
                    return 1;
                }
                else if (slope1 == slope2) {
                    return 0;
                }
                else return -1;
            }
        };

    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // Let's create a few points and see their slopes.

        Point pt1 = new Point(1, 1);
        Point pt2 = new Point(2, 2);

        Point pt3 = new Point(1, 1);

        Point pt4 = new Point(1, 3);

        Point pt5 = new Point(4, 2);


        System.out.println(System.out.format("Slope between (1,1) and (2,2): %2.0f (should be 1)",
                pt1.slopeTo(pt2)));


        System.out.println(System.out.format("Slope between (1,1) and (1,3): %2.0f (should be Inf)",
                pt1.slopeTo(pt4)));

        System.out.println(System.out.format("Slope between (2,2) and (4,2): %2.0f (should be 0)",
                pt2.slopeTo(pt5)));

        System.out.println(System.out.format("Slope between (1,1) and (1,1): %2.0f (should be Neg Inf)",
                pt1.slopeTo(pt3)));

        System.out.println(System.out.format("(1,1) > (1,3): %d",
                pt1.compareTo(pt4)));

        System.out.println(System.out.format("Compare by slope: (1,1) and (1,3) & (2,2) %d",
                pt1.slopeOrder().compare(pt4, pt2)));

    }
}