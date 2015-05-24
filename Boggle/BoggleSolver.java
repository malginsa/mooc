public class BoggleSolver
{
	private DictST dict;	// dictionary
	private TST<Integer> words;	// set of all valid words founded in board
	private Graph graph;	// graph representation of board
	private boolean[][] visited;	// element of bord/graph visited marks
	private StringBuilder pref;	// current prefix
	private BoggleBoard board;

	// Initializes the data structure using the given array of strings
	// as the dictionary. (You can assume each word in the dictionary
	// contains only the uppercase letters A through Z.)
	public BoggleSolver(String[] dictionary)
	{
		dict = new DictST();
		for (int i = 0; i < dictionary.length; i++)
			dict.put(dictionary[i]);
	}
	
	// Returns the set of all valid words in the given Boggle board,
	// as an Iterable.
	public Iterable<String> getAllValidWords(BoggleBoard boardp)
	{
		this.board = boardp;
		words = new TST<Integer>();
		this.creategraph(board.rows(), board.cols());
		visited = new boolean[board.rows()][board.cols()];
		this.clearvisited();
		pref = new StringBuilder();
		for (int i = 0; i < board.rows(); i++)
			for (int j = 0; j < board.cols(); j++)
				generate(i, j);
		return words.keys();
	}
	
	private void generate(int i, int j)
	{
		visited[ i ][ j ] = true;
		char letter = board.getLetter(i, j);
		pref.append(letter);
		if (letter == 'Q')
			pref.append('U');
		if (check_pref_in_dict())
			for (int neib : graph.adj(j + i * board.cols()))
			{
				int neibi = neib / board.cols();
				int neibj = neib % board.cols();
				if (!visited[ neibi ][ neibj ])
						generate(neibi, neibj);
			}
		pref.deleteCharAt(pref.length() - 1);
		if (pref.length() > 0)
			if (pref.charAt(pref.length() - 1) == 'Q')
				pref.deleteCharAt(pref.length() - 1);
		visited[ i ][ j ] = false;
	}

	private boolean check_pref_in_dict()
	{
		String str = pref.toString();	// ! to optimize
		switch (dict.pref_or_word(str))
		{
			case -1: 
				return false;
			case 0:
				return true;
			case 1: 
			{
				if (str.length() > 2)
					words.put(str, 1);
				return true;
			}
			default:
		}
		return true;
	}

	private void creategraph(int M, int N)
	{
		graph = new Graph(M * N);
		for (int i = 0; i < M; i++)
			for (int j = 0; j < N; j++)
			{
				if (j < N - 1) // eastern neib exists
					graph.addEdge(j + i * N, j + i * N + 1);
				if (j < N - 1 && i < M - 1) // south-eastern neib
					graph.addEdge(j + i * N, j + 1 + (i + 1) * N);
				if (i < M - 1) // southern neib exists
					graph.addEdge(j + i * N, j + (i + 1) * N);
				if (j > 0 && i < M - 1) // south-western neib exists
					graph.addEdge(j + i * N, j - 1 + (i + 1) * N);
			}
	}

	private void clearvisited()
	{
		for (int i = 0; i < board.rows(); i++)
			for (int j = 0; j < board.cols(); j++)
				visited[ i ][ j ] = false;
	}

	// Returns the score of the given word if it is in the dictionary,
	// zero otherwise. (You can assume the word contains only
	// the uppercase letters A through Z.)
	public int scoreOf(String word)
	{
		if (!dict.contains(word))
			return 0;
		int len = word.length();
//		int qcount = 0;
//		for (int i = 0; i < word.length(); i++)
//			if (word.charAt(i) == 'Q')
//				qcount++;
//		len += qcount;
		switch (len)
		{
			case 0 : return 0;
			case 1 : return 0;
			case 2 : return 0;
			case 3 : return 1;
			case 4 : return 1;
			case 5 : return 2;
			case 6 : return 3;
			case 7 : return 5;
			default : return 11;
		}
	}

	// Takes the filename of a dictionary and the filename of a Boggle board
	// as command-line arguments and prints out all valid words
	// for the given board using the given dictionary

	private static void unittests()
	{
		TestSuite ts = new TestSuite();
		In in = new In("boggle-testing/dictionary-yawl.txt");
		String[] dictionary = in.readAllStrings();
		BoggleSolver solver = new BoggleSolver(dictionary);

		BoggleBoard board = new BoggleBoard("boggle-testing/board-points0.txt");
		int score = 0;
		for (String word : solver.getAllValidWords(board))
			score += solver.scoreOf(word);
		ts.assertEQ(score, 0, "test 1");

		board = new BoggleBoard("boggle-testing/board-points1000.txt");
		score = 0;
		for (String word : solver.getAllValidWords(board))
			score += solver.scoreOf(word);
		ts.assertEQ(score, 1000, "test 2");

		board = new BoggleBoard("boggle-testing/board-points100.txt");
		score = 0;
		for (String word : solver.getAllValidWords(board))
			score += solver.scoreOf(word);
		ts.assertEQ(score, 100, "test 3");

		board = new BoggleBoard("boggle-testing/board-points1250.txt");
		score = 0;
		for (String word : solver.getAllValidWords(board))
			score += solver.scoreOf(word);
		ts.assertEQ(score, 1250, "test 4");

		board = new BoggleBoard("boggle-testing/board-points1500.txt");
		score = 0;
		for (String word : solver.getAllValidWords(board))
			score += solver.scoreOf(word);
		ts.assertEQ(score, 1500, "test 5");

		ts.tally();
	}

	public static void main(String[] args)
	{
		if (args.length == 0)
			unittests();
		else
		{
	    In in = new In(args[0]);
	    String[] dictionary = in.readAllStrings();
	    BoggleSolver solver = new BoggleSolver(dictionary);

	    BoggleBoard board = new BoggleBoard(args[1]);
	    int score = 0;
			int count = 0;
	    for (String word : solver.getAllValidWords(board))
	    {
	        StdOut.println(word);
	        score += solver.scoreOf(word);
					count++;
	    }
	    StdOut.println("Score = " + score);
	    StdOut.println("Count = " + count);
		}
	}
}

