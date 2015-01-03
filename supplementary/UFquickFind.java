public class UFquickFind {
  private int[] id;
  private int count;
  public UFquickFind(int N) {
    count = N;
    id = new int[N];
    for (int i = 0; i < N; i++) 
      id[i] = i;
  }
  public int count() { return count; }
  public boolean connected(int p, int q) { return find(p) == find(q); }
  public int getid(int i) { return id[i]; }
  public int get_length_id(int i) { return id.length; }
  public int find(int p) { return id[p]; }
  public void union(int p, int q) {
    int pID = find(p);
    int qID = find(q);
    if (pID == qID) return;
    for (int i = 0; i < id.length; i++) 
      if (id[i] == pID) id[i] = qID;
    count--;
  }
  public static void main(String[] args) {
    int N = StdIn.readInt();
    UFquickFind uf = new UFquickFind(N);
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
//    for (int i = 0; i < uf.get_length_id(i); i++)
//      StdOut.print(uf.getid(i) + " ");
//    StdOut.println();
  }
}
