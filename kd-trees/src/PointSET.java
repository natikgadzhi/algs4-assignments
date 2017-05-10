import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

public class PointSET {

    private SET<Point2D> pts;
    private int size;

    // construct an empty set of points
    public PointSET() {
        pts = new SET<Point2D>();
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }


    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException();
        if (!pts.contains(p)) {
            pts.add(p);
            size++;
        }
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();

        return pts.contains(p);
    }


    // draw all points to standard draw
    public void draw() {
        for (Point2D p : pts) {
            p.draw();
        }
    }


    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Stack<Point2D> pointsInRect = new Stack<Point2D>();

        for (Point2D p:pts) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() &&
                    p.y() >= rect.ymin() && p.y() <= rect.ymax())
            pointsInRect.push(p);
        }

        return pointsInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D that) {
        Point2D closest = pts.max();
        double minDistance = closest.distanceTo(that);

        for (Point2D p:pts) {
            if (p.distanceTo(that) < minDistance) {
                closest = p;
                minDistance = p.distanceTo(that);
            }
        }

        return closest;
    }

    public static void main(String[] args) {

    }
}