import java.util.regex.Pattern;
// import java.util.regex.Matcher;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
// import edu.princeton.cs.algs4.StdIn;

// WordNet is a semantic lexicon
// WordNet groups words into sets of synonyms called synsets
//   and describes semantic relationships between them.
// Each vertex v is an integer that represents a synset.
// Each edge connects a hyponym (more specific synset)
//   to a hypernym (more general synset).
// The wordnet digraph is a rooted DAG: 
//   it is acyclic and has one vertex—the root
//   that is an ancestor of every other vertex.
// However, it is not necessarily a tree
//   because a synset can have more than one hypernym.
// The WordNet input files has CVS formats.
// The file synsets.txt lists all the (noun) synsets in WordNet.
//   <id>, <synset>, <gloss>
// The file hypernyms.txt contains the hypernym relationships:
//   The first field is a synset id;
//   subsequent fields are the id numbers of the synset's hypernyms.

public class WordNet {
  
  private static final int INFINITYDIST = 1000;

  private final RedBlackBST<String, Queue<Integer>> synsetbst;
  private final Digraph digraph;
  private final String[] synsetarr;
  private final SAP sap;
  
  // constructor takes the name of the two input files
  public WordNet(String synsets, String hypernyms)
  {
    if (synsets == null || hypernyms == null)
      throw new java.lang.NullPointerException();
    // read buffer from file
    In in = new In(synsets);
    String buffer = in.readAll();
    // extract lines from buffer
    Pattern pattern = Pattern.compile("\\n");
    String[] lines = pattern.split(buffer);
    int cntvert = lines.length;
    synsetarr = new String[cntvert];
    // parse lines of synsets.txt
    Pattern commapatt = Pattern.compile(",");
    String[] fields;
    String[] nouns;
    synsetbst = new RedBlackBST<String, Queue<Integer>>();
    Pattern wordpatt = Pattern.compile("\\p{javaWhitespace}+");

    // fill in synsetbst RedBlackBST
    for (int ind = 0; ind < cntvert; ind++)
    {
      fields = commapatt.split(lines[ind]);
      synsetarr[ind] = fields[1];
      int id = Integer.parseInt(fields[0]);
      // parse nouns
      nouns = wordpatt.split(fields[1]);
      for (int idnoun = 0; idnoun < nouns.length; idnoun++)
      {
        String noun = nouns[idnoun];
        Queue<Integer> ids;
        if (synsetbst.contains(noun))
          ids = synsetbst.get(noun);
        else
          ids = new Queue<Integer>();
        ids.enqueue(id);
        synsetbst.put(noun, ids);
      }
    }

    // construct a Digraph from hypernyms
    digraph = new Digraph(cntvert);
    in = new In(hypernyms);
    while (!in.isEmpty())
    {
      fields = commapatt.split(in.readLine());
      int vert = Integer.parseInt(fields[0]);
      int cntedges = fields.length-1;
      for (int edge = 0; edge < cntedges; edge++) {
        int hypernym = Integer.parseInt(fields[edge+1]);
        digraph.addEdge(vert, hypernym);
        }
      }
    // check whether one root
    if (this.rootscnt() != 1)
      throw new java.lang.IllegalArgumentException();
    // check whether there are a directed cycle
    DirectedCycle dircyc = new DirectedCycle(digraph);
    if (dircyc.hasCycle())
      throw new java.lang.IllegalArgumentException();
    this.sap = new SAP(digraph);
  }
  
  // return 2 if count of roots more than 1
  private int rootscnt()
  {
    int roots = 0;
    for (int v = 0; v < this.digraph.V(); v++)
    {
      int len = 0;
      for (int adj : this.digraph.adj(v)) len++;
      if (len == 0) roots++;
      if (roots > 1)
        return 2;
    }
    return 1;
  }

  // returns all WordNet nouns
  public Iterable<String> nouns()
  {
    return synsetbst.keys();
  }
  
  // is the word a WordNet noun?
  public boolean isNoun(String word)
  {
    if (word == null)
      throw new java.lang.NullPointerException();
    return synsetbst.contains(word);
  }
  
  // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB)
  {
    if (nounA == null || nounB == null)
      throw new java.lang.NullPointerException();
    if (!this.isNoun(nounA) || !this.isNoun(nounB))
      throw new java.lang.IllegalArgumentException();
    Queue<Integer> idsA = synsetbst.get(nounA);
    Queue<Integer> idsB = synsetbst.get(nounB);
    int len = sap.length(idsA, idsB);
    // check whether the path exists
    if (len == -1) return INFINITYDIST;
    else return len;
  }
  
  // a synset that is the common ancestor of nounA and nounB
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB)
  {
    if (nounA == null || nounB == null)
      throw new java.lang.NullPointerException();
    Queue<Integer> idsA = synsetbst.get(nounA);
    Queue<Integer> idsB = synsetbst.get(nounB);
    int synsetid = sap.ancestor(idsA, idsB);
    return synsetarr[synsetid];
  }
  
  // unit testing of this class
  public static void main(String[] args) {
//    WordNet wordnet = new WordNet(
//      "synsets500-subgraph.txt", "hypernyms500-subgraph.txt");
//    StdOut.println(wordnet.distance("amyloid", "DEAE_cellulose"));
//    WordNet wordnet = new WordNet(
//      "synsets3.txt", "hypernyms3InvalidTwoRoots.txt");
//    WordNet wordnet = new WordNet( "synsets3.txt", "hypernyms3InvalidCycle.txt");
//    WordNet wordnet = new WordNet( "synsets3.txt", "hypernyms3.txt");
//    StdOut.println(wordnet.distance("c", "b"));
//    WordNet wordnet = new WordNet( "synsets.txt", "hypernyms.txt");
//    WordNet wordnet = new WordNet(
//      "synsets500-subgraph.txt", "hypernyms500-subgraph.txt");
//    StdOut.println(wordnet.distance("brain_sugar", "pulp"));
//    WordNet wordnet = new WordNet(
//      "synsets6.txt", "hypernyms6TwoAncestors.txt");
//    for (String noun : wordnet.nouns())
//      StdOut.print(noun+' ');
//    StdOut.println(wordnet.isNoun("CC"));
//    TestSuite ts = new TestSuite();
//    ts.assertEQ(wordnet.distance("CC", "b"), 1, "failed test 1");
//    ts.assertEQ(wordnet.distance("b", "e"), 3, "failed test 2");
//    ts.assertEQ(wordnet.distance("B", "f"), 2, "failed test 3");
//    ts.assertEQ(wordnet.distance("ccC", "a"), 4, "failed test 4");
//    ts.assertEQ(wordnet.sap("CC", "b"), "c CC ccC", "failed test 5");
//    ts.assertEQ(wordnet.sap("b", "e"), "a", "failed test 6");
//    ts.assertEQ(wordnet.sap("B", "f"), "a", "failed test 7");
//    ts.assertEQ(wordnet.sap("ccC", "a"), "a", "failed test 8");
//    ts.tally();
  }
}
