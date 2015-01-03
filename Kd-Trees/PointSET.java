public class PointSET {

	private SET<Point2D> pset; // set of points

	public PointSET()
		// construct an empty set of points
	{
		pset = new SET<Point2D>();
	}
	public boolean isEmpty()
		// is the set empty?
	{
		return (this.size() == 0);
	}
	public int size()
		// number of points in the set
	{
		return this.pset.size();
	}
	public void insert(Point2D p)
		// add the point p to the set (if it is not already in the set)
	{
		if (p == null) 
			throw new NullPointerException("called insert() with a null key");
		if (!this.contains(p))
			this.pset.add(p);
	}
	public boolean contains(Point2D p)
		// does the set contain the point p?
	{
		return this.pset.contains(p);
	}
	public void draw()
		// draw all of the points to standard draw
	{
		for (Point2D p : this.pset)
			p.draw();
	}
	public Iterable<Point2D> range(RectHV rect)
		// all points in the set that are inside the rectangle
	{
		Queue<Point2D> queue = new Queue<Point2D>();
		for (Point2D p : this.pset)
			if (rect.contains(p))
					queue.enqueue(p);
		return queue;
	}
	public Point2D nearest(Point2D p)
		// a nearest neighbor in the set to p; null if set is empty
	{
		if (this.isEmpty()) return null;
		Point2D nearestP = this.pset.min();
		double mindist = p.distanceTo(nearestP);
		for (Point2D candid : this.pset)
		{
			double candidist = p.distanceTo(candid);
			if (candidist < mindist)
			{
				nearestP = candid;
				mindist = candidist;
			}
		}
		return nearestP;
	}
	
	public static void main(String[] args)
	{
		PointSET pointset = new PointSET();
		In in = new In(args[0]);
		while (!in.isEmpty())
		{
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			pointset.insert(p);
		}

		StdDraw.setPenRadius(0.01);
		StdDraw.setPenColor(StdDraw.DARK_GRAY);
		pointset.draw();

		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setPenRadius(0.005);
		RectHV rect = new RectHV(0.1, 0.1, 0.8, 0.8);
		rect.draw();
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setPenRadius(0.01);
		for (Point2D p : pointset.range(rect))
			p.draw();

		StdDraw.setPenColor(StdDraw.RED);
		Point2D origin = new Point2D(0.4, 0.9);
		Point2D nearest = pointset.nearest(origin);
		origin.draw();
		nearest.draw();
	}
}

