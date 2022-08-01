import java.util.*;

public class DeterministicBotCleanLarge {

    private enum Action {
        Right("RIGHT"),
        Left("LEFT"),
        Up("UP"),
        Down("DOWN"),
        Clean("CLEAN");

        private String name;

        Action(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    private static class Room {
        public char[][] squares;
        public int width;
        public int height;

        public Room(char[][] squares, int height, int width) {
            this.squares = squares;
            this.height = height;
            this.width = width;
        }
    }

    private static class Node {
        private final int[] state;
        private final double cost;
        private final Node parent;
        private final Action action;

        public Node(int[] state) {
            this(state, 0, null, Action.Clean);
        }

        public Node(int[] state, double cost, Node parent, Action action) {
            this.state = state;
            this.cost = cost;
            this.parent = parent;
            this.action = action;
        }

        public String getState() {
            return state[0] + "," + state[1];
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

    private static boolean isGoal(Room room, Node currentNode) {
        return room.squares[currentNode.state[0]][currentNode.state[1]] == 'd';
    }

    private static double costFunction(Node currentNode, Node nextNode) {
        return nextNode.cost - currentNode.cost;
    }

    private static Node transitionModel(Room room, Node currentNode, Action action) {
        switch (action) {
            case Right:
                if (currentNode.state[1] > room.width - 2) {
                    return null;
                }
                return new Node(new int[]{currentNode.state[0], currentNode.state[1] + 1}, currentNode.cost + 1, currentNode, action);
            case Left:
                if (currentNode.state[1] < 1) {
                    return null;
                }
                return new Node(new int[]{currentNode.state[0], currentNode.state[1] - 1}, currentNode.cost + 1, currentNode, action);
            case Up:
                if (currentNode.state[0] < 1) {
                    return null;
                }
                return new Node(new int[]{currentNode.state[0] - 1, currentNode.state[1]}, currentNode.cost + 1, currentNode, action);
            case Down:
                if (currentNode.state[0] > room.height - 2) {
                    return null;
                }
                return new Node(new int[]{currentNode.state[0] + 1, currentNode.state[1]}, currentNode.cost + 1, currentNode, action);
            case Clean:
                return new Node(new int[]{currentNode.state[0], currentNode.state[1]}, currentNode.cost + 1, currentNode, action);
            default:
                return new Node(new int[]{currentNode.state[0], currentNode.state[1]}, currentNode.cost, currentNode, action);
        }
    }

    private static ArrayList<Node> expand(Room room, Node currentNode) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (Action action : Action.values()) {
            Node nextNode = transitionModel(room, currentNode, action);
            if (nextNode != null) {
                double cost = currentNode.cost + costFunction(currentNode, nextNode);
                nodes.add(nextNode);
            }
        }
        return nodes;
    }

    private static Node bestFirstSearch(Room room, Node initialNode) {
        PriorityQueue<Node> frontier = new PriorityQueue<>((o1, o2) -> (int) (o1.cost - o2.cost));
        frontier.add(initialNode);

        HashMap<String, Node> reached = new HashMap<>();
        reached.put(initialNode.getState(), initialNode);

        while (frontier.size() > 0) {
            Node currentNode = frontier.poll();
            if (isGoal(room, currentNode)) {
                return currentNode;
            }
            for (Node childNode : expand(room, currentNode)) {
                String childState = childNode.getState();
                if (!reached.containsKey(childState) || childNode.cost < reached.get(childState).cost) {
                    reached.put(childState, childNode);
                    frontier.add(childNode);
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        /**
         * 1 0
         * 5 5
         * -d---
         * bd---
         * --d--
         * --d--
         * --d-d
         */
        String test1 = "1 0 5 5 -d--- bd--- ---d- ---d- --d-d";

        /**
         *
         * 0 0
         * 5 5
         * b---d
         * -d--d
         * --dd-
         * --d--
         * ----d
         */
        String test2 = "0 0 5 5 b---d -d--d --dd- --d-- ----d";

        /**
         *
         * 1 0
         * 5 5
         * ----d
         * bd--d
         * --dd-
         * --d--
         * ----d
         */
        String test3 = "1 0 5 5 ----d bd--d --dd- --d-- ----d";

        /**
         * 0 1
         * 5 5
         * -d---
         * -d---
         * ---d-
         * ---d-
         * --d-d
         */
        String test4 = "0 1 5 5 -d--- -d--- ---d- ---d- --d-d";

        Scanner scanner = new Scanner(test4);

        int posr = scanner.nextInt();
        int posc = scanner.nextInt();

        int height = scanner.nextInt();
        int width = scanner.nextInt();

        char[][] squares = new char[height][width];

        int i = 0;
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int j = 0; j < width; ++j) {
                char cell = row.charAt(j);
                squares[i][j] = cell;
            }
            ++i;
        }

        Room room = new Room(squares, height, width);
        Node initialNode = new Node(new int[]{posr, posc});
        Node nextNode = bestFirstSearch(room, initialNode);
        if (nextNode != null) {
            System.out.println(nextNode);
            if (nextNode == initialNode) {
                System.out.println(initialNode.action);
            } else {
                while (nextNode.parent != initialNode) {
                    nextNode = nextNode.parent;
                }
                System.out.println("Node: " + nextNode);
                System.out.println("Action: " + nextNode.action);
            }
        }
    }
}
