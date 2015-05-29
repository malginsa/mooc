// Dictioanry Symbol Table is a Trie Symbol Table, adopted for dictionary of Boggle game
public class DictST
{
	private static final int R = 26;	// possible indexes == number of capital letter
	private static int[] id;	// char to index correspondence
	private Node root;	// root of trie
	private Node curr_ptr;	// current pointer correspondes current prefix
	private int N;	// number of keys in trie

	private static class Node
	{
		private boolean is_word = false; // whether this Node is a last char in a word?
		private Node[] next = new Node[R];
		private Node parent = null;
	}

	public DictST() {
		// inizialize id
		id = new int [(int)'Z' + 1];
		for (int i = (int)'A'; i <= (int)'Z'; i++)
			id[i] = i - (int)'A';
	}

    public boolean contains(String key)
		{	return contains(root, key, 0); }

		private boolean contains(Node x, String key, int d)
		{
			if (x == null) return false;
			if (d == key.length()) return x.is_word;
			char c = key.charAt(d);
			return contains(x.next[id[c]], key, d+1);
		}

		public void set_up_ptr()
		{ curr_ptr = root; }

		public void move_up_ptr() 
		{
			if (curr_ptr == null)
				throw new java.lang.NullPointerException();
			curr_ptr = curr_ptr.parent;
!!!!!!			if (curr_ptr.parent)
		}

		public void move_down_ptr(char ch)
		{
			if (curr_ptr == null)
				throw new java.lang.NullPointerException();
			curr_ptr = curr_ptr.next[id[ch]];
			if (ch == 'Q')
				curr_ptr = curr_ptr.next[id['U']];
		}

		public boolean contains_pref(char ch)
		{ 
			if (curr_ptr == null)
				return false;
			if (curr_ptr.next[id[ch]] == null)
				return false;
			// check 'Q+U' case
			if (ch == 'Q')
			{
				StdOut.println( curr_ptr.next[id['Q']].next[id['U']] );
				return curr_ptr.next[id['Q']].next[id['U']] != null;
			}
			return true;
		}

		// return status of current prefix (current pointer points to)
		// -1  key is not a pref, neither word
		//  0  key is a pref only
		//  1  key is a word (and hence key is a pref)
		public int status()
		{ 
			if (curr_ptr == null) return -1;
			if (curr_ptr.is_word)
				return 1;
			else
				return 0;
		}

//		public int status(String key)
//		{	return status(root, key, 0);	}
//
//		private int status(Node x, String key, int d)
//		{
//			if (x == null) return -1;
//			if (d == key.length())
//				if (x.is_word)
//					return 1;
//				else
//					return 0;
//			char ch = key.charAt(d);
//			return status(x.next[id[ch]], key, d+1);
//		}

    public void put(String key)
		{ root = put(root, key, 0, null); }

    private Node put(Node x, String key, int d, Node parent)
		{
        if (x == null)
				{
					x = new Node();
					x.parent = parent;
				}
        if (d == key.length())
				{
					if (!x.is_word) N++;
					x.is_word = true;
					return x;
        }
        char c = key.charAt(d);
        x.next[ id[c] ] = put(x.next[ id[c] ], key, d+1, x);
        return x;
    }


	private static void unittests()
	{
		TestSuite ts = new TestSuite();
		In in = new In("boggle-testing/dictionary-yawl.txt");
		String[] dictionary = in.readAllStrings();
		DictST dict = new DictST();
		for (int i = 0; i < dictionary.length; i++)
			dict.put(dictionary[i]);
		dict.set_up_ptr();

		dict.move_down_ptr('E');
		dict.move_down_ptr('Q');
		ts.assertEQ(dict.contains_pref('A'), true, "test 1");

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
			DictST dict = new DictST();
			for (int i = 0; i < dictionary.length; i++)
				dict.put(dictionary[i]);
			dict.set_up_ptr();

			dict.move_down_ptr('E');
    	StdOut.println( dict.contains_pref('Q') );
		}
	}
}
