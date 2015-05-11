public class PathsGenerator
{
	private Graph graph;
	private boolean[] visited;
	private int N ; // rows
	private int M ; // columns
	Stack<Integer> stack;
	int cnt_path = 0;	// count of stacks
	private Queue<Integer> path;

	// Generates paths among board of size NxM
	public PathsGenerator(int N, int M)
	{
		this.N = N;
		this.M = M;
		visited = new boolean[N * M];
		create_graph();

		clear_visited();
		StdOut.print("Count of all possible paths from every verts = ");
		stack = new Stack<Integer>();
//		generate( 0 );
		for (int j = 0; j < M; j++)
			for (int i = 0; i < N; i++)
				generate( i + j * N );
		StdOut.println( this.cnt_path );
	}

	private void generate( int v )
	{
		visited[ v ] = true;
		stack.push( v );
		for( int neib : graph.adj( v ) )
			if (!visited[ neib ])
				generate( neib );
		if (stack.size() > 2) 
//			prn_stack();
			this.cnt_path++;
		stack.pop();
		visited[ v ] = false;
	}

	private void prn_stack()
	{
		Stack<Integer> tmp = new Stack<Integer>();
		while ( !stack.isEmpty() )
			tmp.push( stack.pop() );
		while ( !tmp.isEmpty() )
		{
			int v = tmp.pop();
			StdOut.print( v + " " );
			stack.push( v );
		}
		StdOut.println();
	}

	private void create_graph()
	{
		graph = new Graph(N * M);
		for (int j = 0; j < M; j++)
			for (int i = 0; i < N; i++)
			{
				if (i < N - 1) // eastern neib
					graph.addEdge(i + j * N, i + j * N + 1);
				if (i < N - 1 && j < M - 1 ) // south-eastern neib
					graph.addEdge(i + j * N, i + (j + 1) * N + 1);
				if (j < M - 1) // southern neib
					graph.addEdge(i + j * N, i + (j + 1) * N);
				if (i > 0 && j < M - 1) // south-western neib
					graph.addEdge(i + j * N, i + (j + 1) * N - 1);
			}
	}

	public void prn_graph()
	{
//		StdOut.print(graph);
		for (int j = 0; j < M; j++)
		{
			for (int i = 0; i < N; i++)
				StdOut.print(visited[i + j * N] + " ");
			StdOut.println();
		}
	}

	private void clear_visited()
	{
		for (int j = 0; j < M; j++)
			for (int i = 0; i < N; i++)
				visited[i + j * N] = false;
	}
	
	// Returns the set of all valid paths
//	public Iterable<String> paths()
	
	public static void main(String[] args)
	{
			if (args.length != 2) return;
//			StdOut.print("N = ");
//			StdOut.println(Integer.parseInt(args[0]));
			PathsGenerator pg = new PathsGenerator(
				Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//			pg.prn_graph();
	}
}
