public class GraphEdge implements Comparable<GraphEdge>
{
	private final Integer vert1;
	private final Integer vert2;

	public GraphEdge(Integer vertex1, Integer vertex2)
	{
		if (vertex1 == vertex2) 
			throw new java.lang.IllegalArgumentException("loop edge prohibited");
		this.vert1 = Math.min(vertex1, vertex2);
		this.vert2 = Math.max(vertex1, vertex2);
	}
	public int compareTo(GraphEdge that)
	{
		int cmp = this.vert1.compareTo(that.vert1);
		if (cmp != 0)
			return cmp;
		else
			return this.vert2.compareTo(that.vert2);
	}
	public String toString()
	{
		String ret = this.vert1 + " " + this.vert2; 
		return ret;
	}

	public static void main(String[] args)
	{
		GraphEdge edge1 = new GraphEdge(5, 8);
		GraphEdge edge2 = new GraphEdge(7, 8);
		StdOut.println(edge2.compareTo(edge1));
	}
}

