import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;


public class Percolation {

    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF antiBackwashGrid;
    private int size;
    private boolean[] openSites;
    private int numberOfOpenSites;

    // Create n by n grid and initialize it.
    public Percolation(int n) {

        // Save size for later
        size = n;
        openSites = new boolean[n*n+2];
        Arrays.fill(openSites, Boolean.FALSE);
        numberOfOpenSites = 0;

        // Validate parameters
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("Grid size can't be 0 or less, right?");
        }

        // Create the grid and store it to the instance variable
        this.grid = new WeightedQuickUnionUF((n*n) + 2);
        this.antiBackwashGrid = new WeightedQuickUnionUF((n*n) + 2);

        // Connect the first and last vertice with first and last vertices row
        for (int i = 1; i < n+1; i++) {
            grid.union(0, i);
            grid.union(n * n + 1, n * n + 1 - i);
            antiBackwashGrid.union(0, i);
        }
    }


    // Private Methods

    // Converts position (row, col) to grid index
    private int positionToIndex(int row, int col) {
        return ((row-1) * this.size + (col-1) + 1);
    }

    private void validateIndex(int row, int col) {
        if ((row > this.size) || (col > this.size) || (row < 1) || (col < 1)) {
            throw new java.lang.IndexOutOfBoundsException("Row or column out of bounds");
        }
    }


    // Checks if alreay open and opens a position if not open.
    public void open(int row, int col) {

        this.validateIndex(row, col);

        if (isOpen(row, col)) {
            return;
        }
        else {

            this.openSites[this.positionToIndex(row, col)] = true;
            this.numberOfOpenSites++;

            if (row > 1 && this.isOpen(row-1, col)) {
                grid.union(this.positionToIndex(row, col), this.positionToIndex(row-1, col));
                antiBackwashGrid.union(this.positionToIndex(row, col), this.positionToIndex(row-1, col));
            }

            if (row < this.size && this.isOpen(row+1, col)) {
                grid.union(this.positionToIndex(row, col), this.positionToIndex(row+1, col));
                antiBackwashGrid.union(this.positionToIndex(row, col), this.positionToIndex(row+1, col));
            }

            if (col < this.size && this.isOpen(row, col+1)) {
                grid.union(this.positionToIndex(row, col), this.positionToIndex(row, col+1));
                antiBackwashGrid.union(this.positionToIndex(row, col), this.positionToIndex(row, col+1));
            }

            if (col > 1 && this.isOpen(row, col-1)) {
                grid.union(this.positionToIndex(row, col), this.positionToIndex(row, col-1));
                antiBackwashGrid.union(this.positionToIndex(row, col), this.positionToIndex(row, col-1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        this.validateIndex(row, col);
        return this.openSites[this.positionToIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        this.validateIndex(row, col);
        return this.antiBackwashGrid.connected(0, this.positionToIndex(row, col)) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return this.numberOfOpenSites > 0 && grid.connected(0, (size*size + 1));
    }

    public static void main(String[] args) {

        // Percolation test for n = 1
        // Percolation perc = new Percolation(1);
        // System.out.print(String.format("Open: %s, full: %s, percolates: %s",
        //         perc.isOpen(1,1), perc.isFull(1, 1), perc.percolates()));

        Percolation p = new Percolation(20);

        // Check isFull issue at score 48
        // System.out.print(String.format("%s", p.isFull(1, 1)));

        System.out.print(String.format("isOpen: %s\n", p.isOpen(1, 1)));

        while (!p.percolates()) {
            int row = StdRandom.uniform(20) + 1;
            int col = StdRandom.uniform(20) + 1;

            System.out.print(String.format("Opening (%s, %s) / %s \n", row, col, p.positionToIndex(row, col)));
            p.open(row, col);
        }

        System.out.print(String.format("Percolated at %s open positions. \n", p.numberOfOpenSites()));

    }

}
