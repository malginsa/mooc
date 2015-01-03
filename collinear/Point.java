// Point class

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare pels by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private class SlopeOrder implements Comparator<Point>
    {
        public int compare(Point p1, Point p2) 
        {
            double relat = slopeTo(p1) - slopeTo(p2);
            if (relat < 0) return -1;
            if (relat > 0) return 1;
            return 0;
        }
    }

    private final int x; // x coordinate
    private final int y; // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
      StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (this.y == that.y && this.x == that.x) 
            return Double.NEGATIVE_INFINITY;
        if (this.y == that.y) 
            return 0;
        if (this.x == that.x) 
            return Double.POSITIVE_INFINITY;
        return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        int thiiis;
        int thaaat;
        if (this.y == that.y) {
            thiiis = this.x;
            thaaat = that.x;
        } else {
            thiiis = this.y;
            thaaat = that.y;
        }
        if (thiiis < thaaat) return -1;
        if (thiiis > thaaat) return 1;
        return 0; // thiiis == thaaat
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

/*    public static void test1() {
        Point[] pels = new Point[8];
        pels[0] = new Point(10,10);
        pels[1] = new Point(20,20);
        pels[2] = new Point(30,30);
        pels[3] = new Point(40,40);
        pels[4] = new Point(50,50);
        pels[5] = new Point(10,20);
        pels[6] = new Point(20,10);
        pels[7] = new Point(50,50);

        TestSuite test = new TestSuite();
        test.assertEqual( pels[0].compareTo( pels[1] ), -1, "test 1");
        test.assertEqual( pels[1].compareTo( pels[0] ), 1, "test 2");
        test.assertEqual( pels[0].compareTo( pels[0] ), 0, "test 3");

        test.assertEqual( pels[0].SLOPE_ORDER.compare(pels[1],
            pels[2]), 0, "test 4" );
        test.assertEqual( pels[0].SLOPE_ORDER.compare(pels[5],
            pels[1]), 1, "test 5" );
        test.assertEqual( pels[0].SLOPE_ORDER.compare(pels[1],
            pels[5]), -1, "test 6" );
        test.tally();
    }
*/
    // unit test
    public static void main(String[] args) {
//        test1();
    }
}