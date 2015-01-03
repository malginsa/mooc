// zip 8Puzzle.zip Board.java Solver.java
// board for solver 8 puzzle

public class Board {

	private int N; // size of the board
	private int[][] board; // blocks of the puzzle

	public Board(int[][] blocks) 
		// construct a board from an N-by-N array of blocks
		// (where blocks[i][j] = block in row i, column j)
	{
		this.N = blocks[0].length;
		board = new int[this.N][this.N];
		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				this.board[i][j] = blocks[i][j];
	}
	public int dimension() // board dimension N
	{
		return this.N;
	}

	public int hamming() // number of blocks out of place
	{
		int score = 0;
		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				if (this.board[i][j] != 0)
					if (this.board[i][j] != (i * this.N + j + 1))
						score += 1;
		return score;
	}

	public int manhattan() // sum of Manhattan distances between blocks and goal
	{
		int score = 0;
		int goalrow; // row of the goal block 
		int goalcol; // col of the goal block 
		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				if (this.board[i][j] != 0)
				{
					goalrow = (this.board[i][j] - 1) / this.N;
					goalcol = (this.board[i][j] - 1) % this.N;
					score += Math.abs(i - goalrow) + Math.abs(j - goalcol);
				}
		return score;
	}

	public boolean isGoal() // is this board the goal board?
	{
		return (this.hamming() == 0);
	}

/*	public boolean isGoalold() // is this board the goal board?
	{
		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				if (i == (this.N - 1) && j == (this.N - 1))
				{
					if (this.board[i][j] != 0)
						return false;
				}
				else
				{
					if (this.board[i][j] != (i * this.N + j + 1))
						return false;
				}
		return true;
	}
*/
	public Board twin()
		// a board obtained by exchanging two adjacent blocks in the same row
	{
		Board twin = new Board(this.board);
		if ((this.board[0][0] == 0) || (this.board[0][1] == 0))
		{
			int tmp = twin.board[1][0]; 
			twin.board[1][0] = twin.board[1][1];
			twin.board[1][1] = tmp;
		}
		else
		{
			int tmp = twin.board[0][0]; 
			twin.board[0][0] = twin.board[0][1];
			twin.board[0][1] = tmp;
		}
		return twin;
	}

	public boolean equals(Object y) // does this board equal y?
	{
		if (this == y) return true;
		if (y == null) return false;
		if (this.getClass() != y.getClass()) return false;
		Board yy = (Board) y;
		if (this.N != yy.N) return false;
		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				if (this.board[i][j] != yy.board[i][j])
					return false;
		return true;
	}

	public Iterable<Board> neighbors() // all neighboring boards
	{
		int zerorow = 0; // row of the zero block
		int zerocol = 0; // col of the zero block
		for (int i = 0; i < this.N; i++)
			for (int j = 0; j < this.N; j++)
				if (this.board[i][j] == 0)
				{
					zerorow = i;
					zerocol = j;
				}
		Queue<Board> queue = new Queue<Board>();
		if (zerorow > 0)
			this.addneib(zerorow, zerocol, zerorow - 1, zerocol, queue);
		if (zerorow < this.N - 1)
			this.addneib(zerorow, zerocol, zerorow + 1, zerocol, queue);
		if (zerocol > 0)
			this.addneib(zerorow, zerocol, zerorow, zerocol - 1, queue);
		if (zerocol < this.N - 1)
			this.addneib(zerorow, zerocol, zerorow, zerocol + 1, queue);
		return queue;
	}

	private void addneib(int zerorow, int zerocol, 
						 int row, int col, 
						 Queue<Board> queue)
		// add neib to the queue
	{
		Board neib = new Board(this.board);
		neib.board[zerorow][zerocol] = neib.board[row][col];
		neib.board[row][col] = 0;
		queue.enqueue(neib);
	}

	public String toString()
		// string representation of the board
	{
		if (this == null) return "";
		String ret = this.N + "\n";
		for (int i = 0; i < this.N; i++)
		{
			for (int j = 0; j < this.N; j++)
				ret += this.board[i][j] + " ";
			ret += "\n";
		}
		return ret;
	}

	public static void main(String[] args)
	{
/*		TestSuite test = new TestSuite();

		int[][] blocks = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
		Board board = new Board(blocks);
		test.assertEqual(board.dimension(), 3, "dimension test");
		test.assertEqual(board.equals(board.twin()), false, "twin test");
		test.assertEqual(board.hamming(), 0, "hamming test");
		test.assertEqual(board.manhattan(), 0, "hamming test");
		test.assertEqual(board.isGoal(), true, "goal test");

		int[][] twinblocks = {{2, 1, 3}, { 4, 5, 6}, {7, 8, 0}};
		Board twin = new Board(twinblocks);
		test.assertEqual(twin.equals(board.twin()), true, "twin test");
		test.assertEqual(twin.isGoal(), false, "goal test");

		int[][] blocks2 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};
		board = new Board(blocks2);
		test.assertEqual(board.hamming(), 4, "hamming test");
		test.assertEqual(board.manhattan(), 1+1+1+1, "manhattan test");
		test.assertEqual(board.isGoal(), false, "goal test");

		int[][] twinblocks2 = {{0, 1, 3}, {2, 4, 5}, {7, 8, 6}};
		twin = new Board(twinblocks2);
		test.assertEqual(twin.equals(board.twin()), true, "twin test");

		test.tally();

		int[][] blocks3 = {{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};
		board = new Board(blocks3);
		StdOut.println(board);
		StdOut.println(board.neighbors());
*/
	}
}

