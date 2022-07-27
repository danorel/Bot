import java.util.*;

public class DeterministicBotSavesPrincess {

    private static int[] findPrincess(int N, char[][] grid) {
        if (grid[0][0] == 'p') {
            return new int[] { 0, 0 };
        }
        int last = N - 1;
        if (grid[0][last] == 'p') {
            return new int[] { 0, last };
        }
        if (grid[last][0] == 'p') {
            return new int[] { last, 0 };
        }
        if (grid[last][last] == 'p') {
            return new int[] { last, last };
        }
        return new int[0];
    }

    private static int[] findBot(int N, char[][] grid) {
        int center = (int) Math.floor((double)N / 2);
        if (N % 2 == 0) {
            if (grid[center][center] == 'm') {
                return new int[] { center, center };
            }
            if (grid[center - 1][center] == 'm') {
                return new int[] { center - 1, center };
            }
            if (grid[center][center - 1] == 'm') {
                return new int[] { center, center - 1 };
            }
            if (grid[center - 1][center - 1] == 'm') {
                return new int[] { center - 1, center - 1 };
            }
        }
        return new int[] { center, center };
    }

    private static void displayPathtoPrincess(int N, char[][] grid) {
        int[] princess = DeterministicBotSavesPrincess.findPrincess(N, grid);
        int[] bot = DeterministicBotSavesPrincess.findBot(N, grid);

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
        String test1 = "3 --- -m- p--";

        /**
         * ---p
         * --m-
         * ----
         * ----
         */
        String test2 = "4 ---p --m- ---- ----";

        /**
         * ---p
         * ----
         * -m--
         * ----
         */
        String test3 = "4 ---p ---- -m-- ----";

        /**
         * ----
         * ----
         * -m--
         * ---p
         */
        String test4 = "4 ---- ---- -m-- ---p";

        Scanner scanner = new Scanner(test4);

        int N = scanner.nextInt();

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

        displayPathtoPrincess(N, grid);
    }
}