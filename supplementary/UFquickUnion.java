public class UFquickUnion {
// Weaited Quick Union algorithm for dynamic connectivity problem
  private int[] id; // parent link
  private int[] sz; // size of tree
  private int count;
  public UFquickUnion(int N) {
    count = N;
    id = new int[N]; // parents
    sz = new int[N]; // size of tree
    for (int i = 0; i < N; i++) {
      id[i] = i;
      sz[i] = 1;
    }
  }
  public int count() { return count; }
  public boolean connected(int p, int q) { return find(p) == find(q); }
  public int find(int p) { // follows links till root id
    while(p != id[p]) p = id[p];
    return p;
  }
  public void union(int p, int q) {
    int proot = find(p);
    int qroot = find(q);
    if (proot == qroot) return;
    if (sz[proot] < sz[qroot]) { id[proot] = qroot; sz[qroot] += sz[proot]; }
    else                       { id[qroot] = proot; sz[proot] += sz[qroot]; }
    count--;
  }
  public void prn_id() {
    StdOut.println("id: ");
    for (int i = 0; i < id.length; i++)
      StdOut.print(id[i] + " ");
    StdOut.println();
  }
  public void prn_sz() {
    StdOut.println("sz: ");
    for (int i = 0; i < id.length; i++)
      StdOut.print(sz[i] + " ");
    StdOut.println();
  }

  public static void main(String[] args) {
    int N = StdIn.readInt();
    UFquickUnion uf = new UFquickUnion(N);
    Stopwatch sw = new Stopwatch();
    while (!StdIn.isEmpty()) {
      int p = StdIn.readInt();
      int q = StdIn.readInt();
      if (uf.connected(p, q)) continue;
      uf.union(p, q);
//      StdOut.println(p + "-" + q);
    }
    StdOut.println("elapsed time: " + sw.elapsedTime());
    StdOut.println("total: " + uf.count() + " components");
//    uf.prn_id();
//    uf.prn_sz();
  }
}
