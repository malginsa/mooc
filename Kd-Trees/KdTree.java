public class KdTree {

	private static final boolean LAYERX = true;
	private final static boolean LAYERY = false;

	private final static boolean LEFT  = true;
	private final static boolean RIGHT = false;

	private Node root;
	private class Node
	{
		private Point2D point;
		private Node left, right;
		private RectHV rect; // corresponding rectangle
		private boolean layer; // x or y
		private int N; // size of the subtree

		public Node(Point2D point, RectHV rect, boolean layer)
		{
			this.point = point;
			this.left = null;
			this.right = null;
			this.rect = rect;
			this.layer = layer;
			this.N = 1;
		}
		public String toString()
			// string representation
		{
			if (this == null) return "";
			String ret = "";
			ret += this.point;
			ret += " " + this.N;
			if (this.layer == LAYERX) ret += " X";
			else 					  ret += " Y";
			ret += " " + this.rect + "\n";
			if (this.right != null) 
				ret = this.right + ret;
			if (this.left != null) 
				ret = this.left + ret;
			return ret;
		}
		public void draw()
		{
			this.point.draw();
			if (this.left != null) this.left.draw();
			if (this.right != null) this.right.draw();
		}
	}

	public KdTree()
		// construct an empty set of points
	{
		root = null;
	}
	
	public boolean isEmpty()
		// is the set empty?
	{
		return root == null;
	}
	
	public int size()
		// number of points in the set
	{
		if (root == null) return 0;
		else return root.N;
	}
	
	public void insert(Point2D p)
		// add the point p to the set (if it is not already in the set)
	{
		if (this.contains(p)) return;
		if (root == null) root = new Node(p, new RectHV(0, 0, 1, 1), LAYERX);
		else 			  insert(root, p, null, LEFT);
		return;
	}
	
	private void insert(Node node, Point2D point, Node parent, boolean side)
		// add the point to the subtree node
	{
		if (node == null)
		{
			boolean layer;
			if (parent.layer == LAYERX) layer = LAYERY;
			else layer = LAYERX;

			double xmin = parent.rect.xmin();
			double xmax = parent.rect.xmax();
			double ymin = parent.rect.ymin();
			double ymax = parent.rect.ymax();
			if (side == LEFT)
			{
				if (layer == LAYERY) xmax = parent.point.x();
				else ymax = parent.point.y();
				parent.left = new Node(point, 
					new RectHV(xmin, ymin, xmax, ymax), layer);
			}
			else
			{
				if (layer == LAYERY) xmin = parent.point.x();
				else ymin = parent.point.y();
				parent.right = new Node(point, 
					new RectHV(xmin, ymin, xmax, ymax), layer);
			}
			return;
		}
		
		node.N++;
		int cmp = kdcompare(point, node);
//		if (cmp == 0) return; // point already in the tree
		if (cmp < 0) insert(node.left, point, node, LEFT);
		else		 insert(node.right, point, node, RIGHT);

		return;
	}
	
	private int kdcompare(Point2D point, Node node)
		// compare accoring to node's layer
	{
		if (node.layer == LAYERX)
			return Point2D.X_ORDER.compare(point, node.point);
		else
			return Point2D.Y_ORDER.compare(point, node.point);
	}

	public boolean contains(Point2D p)
		// does the set contain the point p?
	{
		if (p == null) return false;
		Node node = root;
		while (node != null)
		{
			if (node.point.equals(p)) return true;
			int cmp = kdcompare(p, node);
			if (cmp < 0) node = node.left;
			else		 node = node.right;
		}
		return false;
	}
	
	public void draw()
		// draw all of the points to standard draw
	{
		if (this.size() == 0) return;
		root.draw();
		return;
	}
	public Iterable<Point2D> range(RectHV rect)
		// all points in the set that are inside the rectangle
	{
		Queue<Point2D> queue = new Queue<Point2D>();
		if (this.size() == 0) return queue;
		this.addToQueue(root, queue, rect);
		return queue;
	}
	private void addToQueue(Node node, Queue<Point2D> queue, RectHV rect)
		// add node.point and both subtrees.points to the queue
	{
		if (rect.contains(node.point)) queue.enqueue(node.point);
		if (node.left != null)
			if (node.left.rect.intersects(rect))
				this.addToQueue(node.left, queue, rect);
		if (node.right != null)
			if (node.right.rect.intersects(rect))
				this.addToQueue(node.right, queue, rect);
	}
	public Point2D nearest(Point2D p)
		// a nearest neighbor in the set to p; null if set is empty
	{
		if (this.size() == 0) return null;
		return this.nearest(root, p);
	}
	private Point2D nearest(Node node, Point2D point)
		// a nearest neighbor in the node-subtree to point
	{
		Point2D near = node.point;
		if (this.kdcompare(point, node) < 0)
			// which of the subtree look through first?
		{
			near = this.nearestSubTree(near, node.left, point);
			near = this.nearestSubTree(near, node.right, point);
		}
		else
		{
			near = this.nearestSubTree(near, node.right, point);
			near = this.nearestSubTree(near, node.left, point);
		}
		return near;
	}
	private Point2D nearestSubTree(Point2D candid, Node child, Point2D point)
		// nearest point among near and child-subtree to point
	{
		Point2D nearest = candid;
		if (child != null)
		{
			if (child.rect.distanceSquaredTo(point)
				< nearest.distanceSquaredTo(point))
			{
				Point2D childnearest = this.nearest(child, point);
				if (childnearest.distanceSquaredTo(point)
					< nearest.distanceSquaredTo(point))
					nearest = childnearest;
			}
		}
		return nearest;
	}

/*	public String toString()
		// string representation
	{
		return "" + root;
	}
*/
	public static void main(String[] args)
		// test suite
	{
/*		TestSuite test = new TestSuite();

		Point2D p1 = new Point2D(.7, .2);
		Point2D p2 = new Point2D(.5, .4);
		test.assertEqual(Point2D.X_ORDER.compare(p1, p2), 1, "cmp test 1");
		test.assertEqual(Point2D.X_ORDER.compare(p1, p1), 0, "cmp test 2");
		test.assertEqual(Point2D.Y_ORDER.compare(p1, p2), -1, "cmp test 3");

		KdTree kdtree = new KdTree();

		kdtree.insert( p1 );
		test.assertEqual(kdtree.contains(p1), true, "contains test 1");
		test.assertEqual(kdtree.contains(p2), false, "contains test 2");
		kdtree.insert( p2 );
		Point2D p3 = new Point2D(.4, .7);
		kdtree.insert( p3 );
		test.assertEqual(kdtree.contains(p2), true, "contains test 3");
		Point2D p4 = new Point2D(.2, .3);
		test.assertEqual(kdtree.contains(p4), false, "contains test 4");
		kdtree.insert( p4 );
		Point2D p5 = new Point2D(.9, .6);
		kdtree.insert( p5 );
		Point2D p6 = new Point2D(.6, .8);
		kdtree.insert( p6 );
		Point2D p7 = new Point2D(.6, .6);
		test.assertEqual(kdtree.contains(p7), false, "contains test 5");
		kdtree.insert( p7 );
		test.assertEqual(kdtree.contains(p7), true, "contains test 6");

		test.assertEqual(kdtree.nearest(new Point2D(.1, .1)),
			p4, "nearest test p4");
		test.assertEqual(kdtree.nearest(new Point2D(.7, .6)),
			p7, "nearest test p7");
		test.assertEqual(kdtree.nearest(new Point2D(.5, .9)),
			p6, "nearest test p6");

		test.tally();
*/
//		StdOut.println(kdtree);
//		for(Point2D p : kdtree.range(new RectHV(.4, 0, 1, .6)))
//			StdOut.println(p);
//		StdOut.println("\n" + kdtree.nearest(new Point2D(.5, .9)));
//		kdtree.draw();
	}
}

