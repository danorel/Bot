import java.util.Arrays;
import java.util.Scanner;

public class BotClean {
    private static int[] findClosestDirt(char[][] board, boolean[][] cache, int i, int j) {
        if (i < 0 || i >= 5 || j < 0 || j >= 5) {
            return new int[0];
        }
        if (cache[i][j]) {
            return new int[0];
        }
        if (board[i][j] == 'd') {
            return new int[] { i, j };
        }

        cache[i][j] = true;

        int[] up = findClosestDirt(board, cache, i - 1, j);
        if (up.length > 0) {
            return up;
        }

        int[] down = findClosestDirt(board, cache, i + 1, j);
        if (down.length > 0) {
            return down;
        }

        int[] left = findClosestDirt(board, cache, i, j - 1);
        if (left.length > 0) {
            return left;
        }

        int[] right = findClosestDirt(board, cache, i, j + 1);
        if (right.length > 0) {
            return right;
        }

        return new int[0];
    }

    private static void nextMove(char[][] board, int posr, int posc) {
        boolean[][] cache = new boolean[5][5];
        Arrays.stream(cache).forEach(row -> Arrays.fill(row, false));

        int[] dirt = findClosestDirt(board, cache, posr, posc);
        System.out.println(Arrays.toString(dirt));
        System.out.println(posr + ", " + posc);

        int dy = dirt[0] - posr;
        int dx = dirt[1] - posc;

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
        if (dx == 0 && dy == 0) {
            System.out.printf("CLEAN%n");
        }
    }

    public static void main(String[] args) {
        /**
         * 1 0
         * -d---
         * bd---
         * --d--
         * --d--
         * --d-d
         */
        String test1 = "1 0 -d--- bd--- ---d- ---d- --d-d";

        /**
         *
         * 4 0
         * -d---
         * -d---
         * ---d-
         * ---d-
         * b-d-d
         */
        String test2 = "4 0 -d--- -d--- ---d- ---d- b-d-d";

        Scanner scanner = new Scanner(test1);

        int posr = scanner.nextInt();
        int posc = scanner.nextInt();

        char[][] board = new char[5][5];
        int i = 0;
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int j = 0; j < 5; ++j) {
                char cell = row.charAt(j);
                board[i][j] = cell;
            }
            ++i;
        }

        nextMove(board, posr, posc);
    }
}
