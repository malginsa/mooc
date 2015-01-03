public class PercolationStats {
    private double [] stat; // computational statistics
    private int trials;  // number of trials
    public PercolationStats(int N, int T) { 
    // perform T independent computational experiments
    // on an N-by-N grid
        if ((N <= 0) || (T <= 0))
            throw new java.lang.IllegalArgumentException();
        trials = T;
        stat = new double [T];
        for (int trial = 0; trial < T; trial++) {
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                for (;;) {
                    int i = StdRandom.uniform(1, N + 1);
                    int j = StdRandom.uniform(1, N + 1);
                    if (!perc.isOpen(i, j)) {
                        perc.open(i, j);
                        break;
                    }
                }
            }
            stat[trial] = 0.0;
            for (int i = 1; i <= N; i++)
                for (int j = 1; j <= N; j++)
                    if (perc.isOpen(i, j)) stat[trial]++;
            stat[trial] /= (N*N);
        }
    }
    public double mean() {
    // sample mean of percolation threshold
        return StdStats.mean(stat); 
    }
    public double stddev() {
    // sample standard deviation of percolation threshold
        return StdStats.stddev(stat); 
    }
    public double confidenceLo() {
    // returns lower bound of the 95% confidence interval
        return StdStats.mean(stat) 
        - 1.96 * StdStats.stddev(stat) / Math.sqrt(trials);
    }
    public double confidenceHi() {
    // returns upper bound of the 95% confidence interval
        return StdStats.mean(stat) 
        + 1.96 * StdStats.stddev(stat) / Math.sqrt(trials);
    }
    public static void main(String[] args) {
    // test client, described below
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pstat = new PercolationStats(N, T);
        StdOut.println("mean                    = " + pstat.mean());
        StdOut.println("stddev                  = " + pstat.stddev());
        StdOut.println("95% confidence interval = " 
        + pstat.confidenceLo() + ", " + pstat.confidenceHi());
    }
}