public class Generate
{
	public static void main(String[] args)
	{
		if (args.length != 2)
			throw new java.lang.IllegalArgumentException("\n\nparams: " +
				"<number of vertex> <number of edges>\n");
		int N = Integer.parseInt( args[0] );
		int M = Integer.parseInt( args[1] );
		SET<GraphEdge> set = new SET<GraphEdge>();
		int m = 0;
		int maxm = 0;
		while (m < M)
		{
			Integer n1 = StdRandom.uniform(N);
			Integer n2 = StdRandom.uniform(N);
			if (n1 == n2) continue;
			GraphEdge edge = new GraphEdge(n1, n2);
			if (!set.contains(edge))
			{
				set.add(edge);
//				StdOut.println(edge + " added");
				m++;
			}
			maxm++;
			if (maxm > 1000000)
				throw new java.lang.IllegalArgumentException("too small vertex");
		}
		StdOut.println(N);
		for (GraphEdge edge : set)
			StdOut.println(edge);
//		StdOut.println("total edges: " + set.size());
	}
}

