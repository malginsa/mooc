// Shortest Ancestral Path

public class SAP {

  private final int PROHIBITEDLEN = -6000;
  private final int INFINITYLEN = 4000;

  private final Digraph G;

  private class Lenanc
  { // structure of length and ancestor
    private int len; 
    private int anc;
    private Lenanc(int len, int anc)
    {
      this.len = len;
      this.anc = anc;
    }
  }

  private class Vertbrd
  { // vertex stored in border queue
    private int id;
    private int set;
    private int len;
    private Vertbrd(int id, int set, int len)
    {
      this.id = id;
      this.set = set;
      this.len = len;
    }
    public String toString()
    {
      return "v:" + this.id
          + " s:" + this.set
          + " l:" + this.len + " ";
    }
  }

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    if (G == null)
      throw new java.lang.NullPointerException();
    this.G = new Digraph(G);
  }
  
  private void prndiag(Iterable<Vertbrd> border,
      int[] set, int[] length, Lenanc candid) {
  // print internal variables 
    StdOut.print("border: ");
    StdOut.println(border);
    StdOut.print("vertex:");
    for (int i = 0; i < this.G.V(); i++)
      StdOut.print("\t"+i);
    StdOut.print("\nset:   ");
    for (int i = 0; i < set.length; i++)
      StdOut.print("\t"+set[i]);
    StdOut.print("\nlength:");
    for (int i = 0; i < length.length; i++)
      StdOut.print("\t"+length[i]);
    StdOut.printf("\ncandid  len:%2d  anc:%2d\n\n", candid.len, candid.anc);
  }

  // length of shortest ancestral path
  // between any vertex in v and any vertex in w;
  // and a common ancestor that participates in shortest ancestral path;
  // -1 if no such path
  private Lenanc lenandanc(Iterable<Integer> v, Iterable<Integer> w)
  {
    // this values returned if path doesn't exist
    Lenanc lenanc = new Lenanc(-1, -1); 
    // check for common elements in v and w
    for (int vv : v)
      for (int ww : w)
        if (vv == ww)
        {
          lenanc.len = 0;
          lenanc.anc = vv;
          return lenanc;
        }
    // id of set v or w sets
    int[] set = new int[this.G.V()];
    // lengths to original v or w sets
    int[] length = new int[this.G.V()];
    // expanding v and w borer-sets
    Queue<Vertbrd> border = new Queue<Vertbrd>();
    for (int i = 0; i < this.G.V(); i++)
    {
      set[i] = 0;
      length[i] = PROHIBITEDLEN; // for simple catching an algorithmics error
    }
    for (int vert : v)
    {
      set[vert] = 1;
      length[vert] = 0;
      border.enqueue(new Vertbrd(vert, 1, 0));
    }
    for (int vert : w)
    {
      set[vert] = 2;
      length[vert] = 0;
      border.enqueue(new Vertbrd(vert, 2, 0));
    }
    if (border.isEmpty())
      return lenanc;
    //
    Lenanc candid = new Lenanc(INFINITYLEN, -1);
    //
    while (!border.isEmpty()) 
    {
//      prndiag(border, set, length, candid);
      Vertbrd vertbrd = border.dequeue();
      // There can be better potential length than candid
      if ((vertbrd.len + 1) < candid.len)
        for (int adj : this.G.adj(vertbrd.id))
        {
          if (set[adj] == 0)
          { // unvisited vertex
            set[adj] = set[vertbrd.id];
            length[adj] = length[vertbrd.id] + 1;
            border.enqueue(new Vertbrd(adj, set[adj], length[adj]));
          }
          else if (set[adj] != vertbrd.set)
            { // met the vertex from the other set
            int len = vertbrd.len + length[adj] + 1;
            if (len < candid.len)
            {
              candid.len = len;
              candid.anc = adj;
            }
            if ((vertbrd.len + 1) < candid.len)
              border.enqueue(new Vertbrd(adj, vertbrd.set, vertbrd.len + 1));
          }
        }
    }
    // whether path has not been found?
    if (candid.len == INFINITYLEN)
      candid.len = -1;
    return candid;
  }

  // length of shortest ancestral path between v and w;
  // -1 if no such path
  public int length(int v, int w)
  {
    if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
      throw new java.lang.IndexOutOfBoundsException();
    if (v == w) return 0;
    Queue<Integer> vset = new Queue<Integer>();
    Queue<Integer> wset = new Queue<Integer>();
    vset.enqueue(v);
    wset.enqueue(w);
    Lenanc lenanc = this.lenandanc(vset, wset);
    return lenanc.len;
  }
  
  // a common ancestor of v and w that participates 
  // in a shortest ancestral path;
  // -1 if no such path
  public int ancestor(int v, int w)
  {
    if (v < 0 || v >= G.V() || w < 0 || w >= G.V())
      throw new java.lang.IndexOutOfBoundsException();
    if (v == w) return v;
    Queue<Integer> vset = new Queue<Integer>();
    Queue<Integer> wset = new Queue<Integer>();
    vset.enqueue(v);
    wset.enqueue(w);
    Lenanc lenanc = this.lenandanc(vset, wset);
    return lenanc.anc;
  }
  
  // length of shortest ancestral path
  // between any vertex in v and any vertex in w;
  // -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w)
  {
    if (v == null || w == null)
      throw new java.lang.NullPointerException();
    for (int vvert : v)
      if (vvert < 0 || vvert >= G.V())
        throw new java.lang.IndexOutOfBoundsException();
    for (int wvert : w)
      if (wvert < 0 || wvert >= G.V())
        throw new java.lang.IndexOutOfBoundsException();
    Lenanc lenanc = this.lenandanc(v, w);
    return lenanc.len;
  }
  
  // a common ancestor that participates in shortest ancestral path;
  // -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
  {
    if (v == null || w == null)
      throw new java.lang.NullPointerException();
    for (int vvert : v)
      if (vvert < 0 || vvert >= G.V())
        throw new java.lang.IndexOutOfBoundsException();
    for (int wvert : w)
      if (wvert < 0 || wvert >= G.V())
        throw new java.lang.IndexOutOfBoundsException();
    Lenanc lenanc = this.lenandanc(v, w);
    return lenanc.anc;
  }
  
  // do unit testing of this class
  public static void main(String[] args)
  {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty())
    {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
