import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;


public class Solver {

    private Stack<Board> path;
    private MinPQ<SearchNode> minPQ, twinMinPQ;
    private boolean isSolvable;
    private int moves;

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();

        path = new Stack<Board>();

        SearchNode node = new SearchNode(initial, null);
        minPQ = new MinPQ<>();
        minPQ.insert(node);

        SearchNode twinNode = new SearchNode(initial, null);
        twinMinPQ = new MinPQ<>();
        twinMinPQ.insert(twinNode);

        int i = 0;

        // FIXME this is a shitty idea.
        while( true ) {
            i++;

            // Delete a minimal priority node from the MinPQ
            node = minPQ.delMin();
            twinNode = twinMinPQ.delMin();

            if (node.board.isGoal() || twinNode.board.isGoal())
                break;

            for (Board b :node.board.neighbors()) {
                if (!(node.prev != null && b.equals(node.prev.board))) {
                    minPQ.insert(new SearchNode(b, node));
                }
            }

            for (Board b :twinNode.board.neighbors()) {
                if (!(twinNode.prev != null && b.equals(twinNode.prev.board))) {
                    twinMinPQ.insert(new SearchNode(b, twinNode));
                }
            }
        }

        if (node.board.isGoal()) {
            isSolvable = true;
            path.push(node.board);
            while (node.prev != null) {
                moves++;
                node = node.prev;
                path.push(node.board);
            }
        }
        else {

            isSolvable = false;
            moves = -1;
        }
    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        if (isSolvable()) {
            return path;
        }
        else {
            return null;
        }
    }


    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prev;
        private int moves;
        private int priority;

        public SearchNode(Board board, SearchNode prev) {
            this.prev = prev;
            this.board = board;
            this.moves = -1;
            this.priority = -1;
        }

        public int priority() {
            if (this.priority != -1)
                return this.priority;

            this.priority = this.moves() + this.board.manhattan();
            return this.priority;
        }

        // FIXME this is also a shitty idea, this can be calculated outside of the class.
        public int moves() {
            if (this.moves != -1)
                return this.moves;

            this.moves = 0;
            SearchNode n = this;
            while(n.prev != null) {
                this.moves++;
                n = n.prev;
            }

            return this.moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }
    }




    public static void main(String[] args) {

    }
}