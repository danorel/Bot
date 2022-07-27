import java.util.Arrays;
import java.util.Scanner;

public class StochasticBotClean {

    private static final int N = 5;

    private static boolean findDirt(int[] dirt, char[][] board, boolean[][] cache, int posr, int posc) {
        if (posr < 0 || posr >= N || posc < 0 || posc >= N) {
            return false;
        }
        if (cache[posr][posc]) {
            return false;
        }
        if (board[posr][posc] == 'd') {
            dirt[0] = posr;
            dirt[1] = posc;
            return true;
        }
        cache[posr][posc] = true;
        boolean isUp = findDirt(dirt, board, cache, posr - 1, posc);
        boolean isDown = findDirt(dirt, board, cache, posr + 1, posc);
        boolean isLeft = findDirt(dirt, board, cache, posr, posc - 1);
        boolean isRight = findDirt(dirt, board, cache, posr, posc + 1);
        return isUp || isDown || isLeft || isRight;
    }

    private static void nextMove(char[][] board, int posr, int posc) {
        int[] dirt = new int[2];

        boolean[][] cache = new boolean[N][N];
        Arrays.stream(cache).forEach(row -> Arrays.fill(row, false));

        boolean isFound = findDirt(dirt, board, cache, posr, posc);
        if (!isFound) {
            return;
        }

        int dx = dirt[0] - posr;
        int dy = dirt[1] - posc;

        if (dx < 0) {
            System.out.println("UP");
        } else if (dx > 0) {
            System.out.println("DOWN");
        } else if (dy < 0) {
            System.out.println("LEFT");
        } else if (dy > 0) {
            System.out.println("RIGHT");
        } else {
            System.out.println("CLEAN");
        }
    }

    public static void main(String[] args) {
        /**
         * 0 0
         * b---d
         * -----
         * -----
         * -----
         * -----
         */
        String test = "0 0 b---d ----- ----- ----- -----";

        Scanner scanner = new Scanner(test);

        int posr = scanner.nextInt();
        int posc = scanner.nextInt();

        char[][] board = new char[N][N];

        int i = 0;
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int j = 0; j < N; ++j) {
                char cell = row.charAt(j);
                board[i][j] = cell;
            }
            ++i;
        }

        nextMove(board, posr, posc);
    }
}