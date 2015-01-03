public class Percolation {
    private int side;  // side of the square grid = N
    private int sites;  // number of sites = NxN
    private int[] grid;
    private int OPEN = 1;
    private int BLOCKED = 0;
    private int VIRTTOP;
    private int VIRTBOTTOM;
    private WeightedQuickUnionUF uf; 
    private WeightedQuickUnionUF suf; // uf w/o VIRTBOTTOM
    public Percolation(int N) {  // create N-by-N grid
        if (N < 1) throw new java.lang.IllegalArgumentException();
        side = N;
        sites = N * N;
        grid = new int [sites];
        for (int i = 0; i < sites; i++)  // all sites initially blocked
                grid[i] = BLOCKED;
        uf = new WeightedQuickUnionUF(sites + 2);
        suf = new WeightedQuickUnionUF(sites + 1);
        VIRTTOP = sites;
        VIRTBOTTOM = sites + 1;
   }
    private void chkConnect(int i1, int  j1, int  i2, int  j2) {
        if ((i2 < 1)  || (i2 > side) 
            || (j2 < 1) || (j2 > side)) return; // out of index bound
        if (isOpen(i2, j2)) {
            uf.union(((i1 - 1) * side + j1 - 1), 
                            ((i2 - 1) * side + j2 - 1));
            suf.union(((i1 - 1) * side + j1 - 1), 
                            ((i2 - 1) * side + j2 - 1));
        }
    }
    public void open(int i, int j) {
    // open site (row i, column j)
        if ((i < 1) || (i > side) || (j < 1) || (j > side))
            throw new java.lang.IndexOutOfBoundsException();
        if (grid[(i - 1) * side + (j - 1)] == OPEN) return;
        grid[(i - 1) * side + (j - 1)] = OPEN;
        // connect with opened neighbours
        chkConnect(i, j, i + 1, j);
        chkConnect(i, j, i - 1, j);
        chkConnect(i, j, i, j - 1);
        chkConnect(i, j, i, j + 1);
        if (i == 1) {
            uf.union((j - 1), VIRTTOP);
            suf.union((j - 1), VIRTTOP);
        }
        if (i == side) uf.union(((i - 1) * side + j - 1), VIRTBOTTOM);
    }
    public boolean isOpen(int i, int j) {
    // is site (row i, column j) open?
        if ((i < 1) || (i > side) || (j < 1) || (j > side))
            throw new java.lang.IndexOutOfBoundsException();
        return grid[(i - 1) * side + (j - 1)] == OPEN;
    }
    public boolean isFull(int i, int j) {
    // is site (row i, column j) full?
        return (isOpen(i, j)
            && suf.connected((i - 1) * side + (j - 1), VIRTTOP));
    }
    public boolean percolates() {
    // does the system percolate?
        return uf.connected(VIRTTOP, VIRTBOTTOM);
    }
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        StdOut.print("N=3 " + perc.percolates());
        perc.open(1, 1); 
        perc.open(2, 1); 
        perc.open(3, 1); 
        perc.open(2, 3); 
        perc.open(3, 3); 
        StdOut.print("  backwash test: " + perc.isFull(3, 3));
        perc.open(1, 3);
        StdOut.println(" " + perc.isFull(3, 3));
    }
}
