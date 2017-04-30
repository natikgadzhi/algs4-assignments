import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    // Grid parameters
    private int[][] blocks;
    private int n;

    // Manhattan and Hamming weights
    private int h;
    private int m;

    public Board(int[][] blocks) {
        if (blocks == null)
            throw new NullPointerException();

        this.blocks = blocks;
        n = this.blocks.length;

        h = -1;
        m = -1;
    }


    private int[][] switchBlocks(int fromI, int fromJ, int toI, int toJ) {
        int[][] blocksCopy = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocksCopy[i][j] = blocks[i][j];
            }
        }

        blocksCopy[toI][toJ] = blocksCopy[fromI][fromJ];
        blocksCopy[fromI][fromJ] = blocks[toI][toJ];

        return blocksCopy;
    }

    public int dimension() {
        return n;
    }

    // Calculate Hamming weights
    // Return cached value if calculated previously.
    public int hamming() {
        if (h != -1)
            return h;

        h = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0)
                    continue;

                if (blocks[i][j] != (n * i + j + 1))
                    h += 1;
            }
        }

        return h;
    }

    // Calculate Manhattan weights
    // Return immediately if calculated previously.
    public int manhattan() {
        if (m != -1)
            return m;

        m = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0)
                    continue;

                int goalI = (blocks[i][j] - 1) / n;
                int goalJ = (blocks[i][j] - 1) % n;

                m = m + Math.abs(i - goalI) + Math.abs(j - goalJ);
            }
        }

        return m;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {
                if (blocks[i][j] != 0 && blocks[i][j+1] != 0) {
                    return new Board(switchBlocks(i, j, i, j+1));
                }
            }
        }

        return null;
    }

    public boolean equals(Object x) {
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;

        Board that = (Board) x;
        if (this.n != that.n) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != that.blocks[i][j]) return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Queue<Board> queue = new Queue<Board>();

        int blankI = 0;
        int blankJ = 0;

        // First search for the blank element in the grid.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                   blankI = i;
                   blankJ = j;
                   break;
                }
            }
        }

        // Possible switches _with blank element_
        // i-1, j; i+1, j; i, j-1; i, j+1;
        if (blankI - 1 >= 0) {
            queue.enqueue(new Board(this.switchBlocks(blankI, blankJ, blankI-1, blankJ)));
        }

        if (blankI + 1 < n) {
            queue.enqueue(new Board(this.switchBlocks(blankI, blankJ, blankI+1, blankJ)));
        }

        if (blankJ - 1 >= 0) {
            queue.enqueue(new Board(this.switchBlocks(blankI, blankJ, blankI, blankJ-1)));
        }

        if (blankJ + 1 < n) {
            queue.enqueue(new Board(this.switchBlocks(blankI, blankJ, blankI, blankJ+1)));
        }

        return queue;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        int[][] blocks = new int[3][3];

        for (int i = 0; i < 3; i++) {
           for (int j = 0; j < 3; j++) {
               blocks[i][j] = i*3 + j + 1;
           }
        }
        blocks[2][2] = 0;

        Board board = new Board(blocks);

        System.out.println(board.toString());

        System.out.println(board.hamming());
        System.out.println(board.manhattan());

        for (Board brd:
             board.neighbors()) {
            System.out.println(brd.toString());
        }

    }
}