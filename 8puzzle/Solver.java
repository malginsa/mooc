// Solver for 8 puzzle

import java.util.NoSuchElementException;

public class Solver
{
	private SNode root; // initial board;
	private SNode twroot; // initial twin board;
	private boolean solvable = false; // is this puzzle siolvable?
	private int totalmoves; // number of moves made to solve the puzzle
	private SNode goal; // leaf of solved puzzle
	
	private class SNode implements Comparable<SNode>
	{
		private Board board; // search node of the game tree 
		private int manhattan; // manhattan priority of this snode
		private int moves; // moves made so far
		private int priority; // priority of this snode = manhattan + moves
		private SNode parent; // previous search node
		
		public SNode(Board board, SNode parent) // constructor for search node
		{
			this.board = board;
			this.parent = parent;
			this.manhattan = board.manhattan();
			if (parent == null) this.moves = 0;
			else this.moves = parent.moves + 1;
			this.priority = this.manhattan + this.moves;
		}
		
		public String toString()
			// string representation of the board
		{
			String ret = "";
			ret += this.board;
			ret += "manhattan = " + this.manhattan;
			ret += "\nmoves = " + this.moves;
			ret += "\npriority = " + this.priority;
			ret += "\nparent -> \n" + this.parent;
			return ret;
		}
		
		public int compareTo(SNode that)
		{
			if (this.priority < that.priority) return -1;
			if (this.priority > that.priority) return 1;
			return 0;
		}
	}
	
	public Solver(Board initial)
		// find a solution to the initial board (using the A* algorithm)
	{
		root = new SNode(initial, null);
		MinPQ<SNode> pq = new MinPQ<SNode>();
		pq.insert(root);

		twroot = new SNode(initial.twin(), null);
		MinPQ<SNode> twpq = new MinPQ<SNode>();
		twpq.insert(twroot);

		SNode candid = pq.delMin();
		SNode twcandid = twpq.delMin();
		while (!candid.board.isGoal() && !twcandid.board.isGoal())
		{
//			if (pq.size()>10000)
//				throw new NoSuchElementException("MinPQ size reached 10000");
//			if (twpq.size()>10000)
//				throw new NoSuchElementException("twin MinPQ size reached 10000");

			for (Board neib : candid.board.neighbors())
			{
				SNode leaf = new SNode(neib, candid);
				if (candid.parent == null)
					pq.insert(leaf);
				else if (!leaf.board.equals(candid.parent.board))
						pq.insert(leaf);
			}
			candid = pq.delMin();

			for (Board neib : twcandid.board.neighbors())
			{
				SNode leaf = new SNode(neib, twcandid);
				if (twcandid.parent == null)
					twpq.insert(leaf);
				else if (!leaf.board.equals(twcandid.parent.board))
						twpq.insert(leaf);
			}
			twcandid = twpq.delMin();
		}
		this.solvable = candid.board.isGoal();
		if (this.solvable)
		{
			this.totalmoves = candid.moves;
			this.goal = candid;
		}
		else
			this.totalmoves = -1;
/*		if (this.solvable)
		{
			StdOut.println(candid);
			StdOut.println("-----\nsize of MinPQ = " + pq.size());
			StdOut.println("total moves = " + this.moves() + "\n-----");
		}
		else
		{
			StdOut.println("puzzle unsolvable");
			StdOut.println("solution for the twin puzzle:");
			StdOut.println(twcandid);
			StdOut.println("-----\nsize of MinPQ = " + twpq.size());
			StdOut.println("total moves = " + this.moves() + "\n-----");
		}
*/	}
	public boolean isSolvable()
		// is the initial board solvable?
	{
		return this.solvable;
	}
	public int moves()
		// min number of moves to solve initial board; -1 if no solution
	{
		return this.totalmoves;
	}
	public Iterable<Board> solution()
		// sequence of boards in a shortest solution; null if no solution
	{
		Stack<Board> stack = new Stack<Board>();
		SNode snode = goal;
		while (snode != null)
		{
			stack.push(snode.board);
			snode = snode.parent;
		}
		if (this.solvable) 	return stack;
		else return null;
	}
	
	public static void main(String[] args)
		// solve a slider puzzle
	{
	// create initial board from file
	In in = new In(args[0]);
	int N = in.readInt();
	int[][] blocks = new int[N][N];
	for (int i = 0; i < N; i++)
		for (int j = 0; j < N; j++)
			blocks[i][j] = in.readInt();
	Board initial = new Board(blocks);
	
	// solve the puzzle
	Solver solver = new Solver(initial);

	// print solution to standard output
	if (!solver.isSolvable())
		StdOut.println("No solution possible");
	else {
		StdOut.println("Minimum number of moves = " + solver.moves());
		for (Board board : solver.solution())
			StdOut.println(board);
	}
	}
}

