// Dictioanry Symbol Table is a Trie Symbol Table, adopted for dictionary
public class DictST
{
	private static final int R = 26;	// possible indexes == number of capital letter
	private static int[] id;	// char to index correspondence
	private Node root;	// root of trie
	private int N;	// number of keys in trie

	private static class Node
	{
		private boolean is_word = false; // whether this Node is a last char in a word?
		private Node[] next = new Node[R];
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

		// -1 key is no pref, neither word
		//  0 key is a pref only
		//  1 key is a word (and hence is a pref)
		public int pref_or_word(String key)
		{	return pref_or_word(root, key, 0);	}

		private int pref_or_word(Node x, String key, int d)
		{
			if (x == null) return -1;
			if (d == key.length())
				if (x.is_word)
					return 1;
				else
					return 0;
			char ch = key.charAt(d);
			return pref_or_word(x.next[id[ch]], key, d+1);
		}

    public void put(String key)
		{ root = put(root, key, 0); }

    private Node put(Node x, String key, int d)
		{
        if (x == null)
				{
					x = new Node();
//					x.is_word = false;
				}
        if (d == key.length())
				{
            if (!x.is_word) N++;
            x.is_word = true;
            return x;
        }
        char c = key.charAt(d);
        x.next[ id[c] ] = put(x.next[ id[c] ], key, d+1);
        return x;
    }

}
