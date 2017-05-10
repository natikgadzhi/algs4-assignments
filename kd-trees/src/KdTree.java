import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node lb;
        private Node rt;

        public Node(Point2D p, RectHV rect) {
            if (p == null) throw new NullPointerException("Null point");
            this.p = p;
            this.rect = rect;
        }
    }

    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        root = null;
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

        if (isEmpty()) {
            root = new Node(p, new RectHV(0, 0, 1, 1));
            size++;
        }
        else {
            root = insert(root, p, 1);
        }
    }

    private Node insert(Node node, Point2D p, int level) {
        if (node == null) {
            size++;
            return new Node(p, null);
        }
        else if (node.p.equals(p)) {
            return node;
        }
        else {
            if (compare(p, node.p, level) > 0) {
                node.rt = insert(node.rt, p, level + 1);

                if (node.rt.rect == null) {
                    if (level % 2 == 1) {
                        node.rt.rect = new RectHV(node.p.x(), node.rect.ymin(),
                                node.rect.xmax(), node.rect.ymax());
                    } else {
                        node.rt.rect = new RectHV(node.rect.xmin(), node.p.y(),
                                node.rect.xmax(), node.rect.ymax());
                    }
                }

            }
            else {
                node.lb = insert(node.lb, p, level + 1);

                if (node.lb.rect == null) {
                    if (level % 2 == 1) {
                        node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                                node.p.x(), node.rect.ymax());
                    } else {
                        node.lb.rect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                                node.rect.xmax(), node.p.y());
                    }
                }
            }
        }
        return node;
    }

    private int compare(Point2D a, Point2D b, int level) {
        if (level % 2 == 1) {
            return Double.compare(a.x(), b.x());
        }
        else {
            return Double.compare(a.y(), b.y());
        }
    }


    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException();
        return contains(root, p, 1);
    }

    private boolean contains(Node node, Point2D p, int level) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;

        if (compare(p, node.p, level) > 0) {
            return contains(node.rt, p, level + 1);
        }
        else {
            return contains(node.lb, p, level +1);
        }
    }

    // Draw the kd-tree
    public void draw() {
        draw(root, 1);
    }

    private void draw(Node x, int level) {
        if (x == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        x.p.draw();

        if (level % 2 == 1) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
        }

        draw(x.lb, level+1);
        draw(x.rt, level+1);
    }


    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();

        Stack<Point2D> pointsInRect = new Stack<Point2D>();
        Stack<Node> checkNodes = new Stack<Node>();

        if (isEmpty()) return pointsInRect;

        checkNodes.push(root);

        while(!checkNodes.isEmpty()) {
            Node node = checkNodes.pop();
            if (rect.contains(node.p)) pointsInRect.push(node.p);

            if (node.rect.intersects(rect)) {
                if (node.lb != null) checkNodes.push(node.lb);
                if (node.rt != null) checkNodes.push(node.rt);
            }
        }

        return pointsInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D that) {
        return nearestInNode(root, root.p, that);
    }

    private Point2D nearestInNode(Node node, Point2D nearest, Point2D point) {
        if (node == null) {
            return nearest;
        }
        else {
            if (node.p.distanceTo(point) < nearest.distanceTo(point)) {
                nearest = node.p;
            }

            if (node.lb != null && node.lb.rect.distanceSquaredTo(point) < nearest.distanceTo(point)) {
                nearest =  nearestInNode(node.lb, nearest, point);
            }

            if (node.rt != null && node.rt.rect.distanceSquaredTo(point) < nearest.distanceTo(point)) {
                nearest =  nearestInNode(node.rt, nearest, point);
            }
            return nearest;
        }
    }

    public static void main(String[] args) {
//
//
//        KdTree kd = new KdTree();
//
//        kd.insert(new Point2D(0.4, 0.4));
//
//        kd.insert(new Point2D(0.6, 0.8));
//        kd.insert(new Point2D(0.2, 0.3));
//        kd.insert(new Point2D(0.9, 0.1));
//        kd.insert(new Point2D(0.5, 0.2));
//        kd.insert(new Point2D(0.8, 0.8));
//        kd.insert(new Point2D(0.1, 0.4));
//
//
//        // kd.draw();
//
//        System.out.println(kd.contains(new Point2D(0.1, 0.4)));
//        System.out.println(kd.contains(new Point2D(0,0)));
//
//
//        Iterable<Point2D> points = kd.range(new RectHV(0.015, 0.25, 0.45, 0.45));
//
//
//        Point2D nearest = kd.nearest(new Point2D(0.89, 0.11));
//
//        System.out.println("Done");
    }
}