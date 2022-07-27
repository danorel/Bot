import java.util.*;

public class StochasticBotSavesPrincess {

    private static int[] findPrincess(int N, char[][] grid, boolean[][] cache, int i, int j) {
        if ((i < 0 || i >= N) || (j < 0 || j >= N)) {
            return new int[0];
        }
        if (cache[i][j]) {
            return new int[0];
        }
        if (grid[i][j] == 'p') {
            return new int[] { i, j };
        }

        cache[i][j] = true;

        int[] up = findPrincess(N, grid, cache, i - 1, j);
        if (up.length > 0) {
            return up;
        }

        int[] down = findPrincess(N, grid, cache, i + 1, j);
        if (down.length > 0) {
            return down;
        }

        int[] left = findPrincess(N, grid, cache, i, j - 1);
        if (left.length > 0) {
            return left;
        }

        int[] right = findPrincess(N, grid, cache, i, j + 1);
        if (right.length > 0) {
            return right;
        }

        return new int[0];
    }

    private static void displayPathtoPrincess(int N, char[][] grid, int[] bot) {
        int center = (int) Math.floor((double)N / 2);

        boolean[][] cache = new boolean[N][N];
        Arrays.stream(cache).forEach(cell -> Arrays.fill(cell, false));

        int[] princess = StochasticBotSavesPrincess.findPrincess(N, grid, cache, center, center);

        int dy = princess[0] - bot[0];
        int dx = princess[1] - bot[1];

        while (dy > 0) {
            System.out.printf("DOWN%n");
            --dy;
        }
        while (dy < 0) {
            System.out.printf("UP%n");
            ++dy;
        }
        while (dx > 0) {
            System.out.printf("RIGHT%n");
            --dx;
        }
        while (dx < 0) {
            System.out.printf("LEFT%n");
            ++dx;
        }
    }

    public static void main(String[] args) {
        /**
         * ---
         * -m-
         * p--
         */
        String test1 = "3 1 1 --- -m- p--";

        /**
         * ---p
         * --m-
         * ----
         * ----
         */
        String test2 = "4 1 2 ---p --m- ---- ----";

        /**
         * ---p
         * ----
         * -m--
         * ----
         */
        String test3 = "4 2 1 ---p ---- -m-- ----";

        /**
         * ----
         * ----
         * -m--
         * ---p
         */
        String test4 = "4 2 1 ---- ---- -m-- ---p";

        Scanner scanner = new Scanner(test1);

        int N = scanner.nextInt();

        int[] bot = new int[] { scanner.nextInt(), scanner.nextInt() };

        char[][] grid = new char[N][N];

        int i = 0;
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int j = 0; j < N; ++j) {
                char cell = row.charAt(j);
                grid[i][j] = cell;
            }
            ++i;
        }

        displayPathtoPrincess(N, grid, bot);
    }
}