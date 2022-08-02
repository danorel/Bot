import java.util.*;

public class StochasticBotCleanObservable {

    public static int N = 5;

    private enum Action {
        Left("LEFT"),
        Right("RIGHT"),
        Up("UP"),
        Down("DOWN");

        private String name;

        Action(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static class Node {
        public int[] state;
        public int cost;
        public Node parent;
        public Action action;

        public Node(int[] state) {
            this(state, 0, null, null);
        }

        public Node(int[] state, int cost, Node parent, Action action) {
            this.state = state;
            this.cost = cost;
            this.parent = parent;
            this.action = action;
        }

        public String getState() {
            return Arrays.toString(this.state);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "state=" + Arrays.toString(state) +
                    ", cost=" + cost +
                    ", parent=" + parent +
                    ", action=" + action +
                    '}';
        }
    }

    private static class Board {
        public char[][] squares;

        public Board(char[][] squares) {
            this.squares = squares;
        }
    }

    private static boolean isGoal(Node currentNode, Board board) {
        return board.squares[currentNode.state[0]][currentNode.state[1]] == 'd';
    }

    private static Node transitionModel(Board board, Node currentNode, Action action) {
        switch (action) {
            case Up: {
                if (currentNode.state[0] < 1) {
                    return null;
                }
                return new Node(new int[]{currentNode.state[0] - 1, currentNode.state[1]}, currentNode.cost + 1, currentNode, action);
            }
            case Down: {
                if (currentNode.state[0] >= N - 2) {
                    return null;
                }
                return new Node(new int[]{ currentNode.state[0] + 1, currentNode.state[1] }, currentNode.cost + 1, currentNode, action);
            }
            case Left: {
                if (currentNode.state[1] < 1) {
                    return null;
                }
                return new Node(new int[]{currentNode.state[0], currentNode.state[1] - 1}, currentNode.cost + 1, currentNode, action);
            }
            case Right: {
                if (currentNode.state[1] >= N - 2) {
                    return null;
                }
                return new Node(new int[]{ currentNode.state[0], currentNode.state[1] + 1 }, currentNode.cost + 1, currentNode, action);
            }
            default: {
                return new Node(new int[]{ currentNode.state[0], currentNode.state[1] }, currentNode.cost, currentNode, action);
            }
        }
    }

    private static Node bestFirstSearch(Board board, Node initialNode) {
        PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> o1.cost - o2.cost);
        queue.add(initialNode);

        HashSet<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            if (isGoal(currentNode, board)) {
                return currentNode;
            }
            for (Action action : Action.values()) {
                Node nextNode = transitionModel(board, currentNode, action);
                if (nextNode != null) {
                    String nextState = nextNode.getState();
                    if (!visited.contains(nextState)) {
                        queue.add(nextNode);
                        visited.add(nextState);
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {

        /**
         * 0 0
         * b-ooo
         * -dooo
         * ooooo
         * ooooo
         * ooooo
         */
        String test1 = "0 0 b-ooo -dooo ooooo ooooo ooooo";

        /**
         * 1 0
         * --ooo
         * bdooo
         * --ooo
         * ooooo
         * ooooo
         */
        String test2 = "1 0 --ooo bdooo --ooo ooooo ooooo";

        /**
         * 1 0
         * --ooo
         * b-ooo
         * --ooo
         * ooooo
         * ooooo
         */
        String test3 = "1 0 --ooo boooo --ooo ooooo ooooo";

        Scanner scanner = new Scanner(test3);

        int posr = scanner.nextInt();
        int posc = scanner.nextInt();

        char[][] squares = new char[N][N];

        int i = 0;
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int j = 0; j < N; ++j) {
                char cell = row.charAt(j);
                squares[i][j] = cell;
            }
            ++i;
        }

        Board board = new Board(squares);
        Node initialNode = new Node(new int[]{ posr, posc });
        Node nextNode = bestFirstSearch(board, initialNode);

        if (nextNode != null) {
            if (nextNode == initialNode) {
                System.out.println("CLEAN");
            } else {
                while (nextNode.parent != initialNode) {
                    nextNode = nextNode.parent;
                }
                System.out.println(nextNode.action);
            }
        }
    }
}
