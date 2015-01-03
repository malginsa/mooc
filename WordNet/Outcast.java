// outcast detection of semantic relatedness from WordNet list
public class Outcast {

  private final WordNet wordnet;

  // constructor takes a WordNet object
  public Outcast(WordNet wordnet)
  {
    this.wordnet = wordnet;
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns)
  {
    int[] dist = new int[nouns.length];
    for (int ind = 0; ind < nouns.length; ind++)
    {
      dist[ind] = 0;
      for (int jnd = 0; jnd < nouns.length; jnd++)
        dist[ind] += wordnet.distance(nouns[ind], nouns[jnd]);
    }
    int idmax = 0;
    for (int ind = 1; ind < nouns.length; ind++)
      if (dist[ind] > dist[idmax])
        idmax = ind;
    return nouns[idmax];
  }

  public static void main(String[] args)
  {
    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++)
    {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
