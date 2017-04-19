import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by xnutsive on 19/03/2017.
 */
public class PercolationStats {

    private double[] openPositions;
    private int trials;

    public PercolationStats(int n, int trials) {

        if (trials < 1) {
            throw new java.lang.IllegalArgumentException("Number of trials can't be 0 or less.");
        }

        if (n < 1) {
            throw new java.lang.IllegalArgumentException("Field dimentions can't be 0 or less.");
        }

        this.openPositions = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                p.open(row, col);
            }

            this.openPositions[i] = (double) p.numberOfOpenSites() / (n * n);

            // System.out.print(String.format("Iteration %s percolated at %s\n",
            //      i, this.openPositions[i]));
        }
    }

    public double mean() {
        return StdStats.mean(this.openPositions);
    }

    public double stddev() {
        return StdStats.stddev(this.openPositions);
    }

    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev()) / Math.sqrt((double) this.trials);
    }

    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev()) / Math.sqrt((double) this.trials);
    }

    public static void main(String[] args) {

        Stopwatch watch = new Stopwatch();

        int size = 200;
        int trials = 500;

        if (args.length > 1) {
            size = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }

        PercolationStats stats = new PercolationStats(size, trials);

        System.out.print(String.format("Done in %s seconds.\n", watch.elapsedTime()));

        System.out.print(String.format("Mean: %s\nStddev: %s\n96 confidence levels: [%s, %s]",
                stats.mean(), stats.stddev(), stats.confidenceLo(), stats.confidenceHi()));

    }
}

