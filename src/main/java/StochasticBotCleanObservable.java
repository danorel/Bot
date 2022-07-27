import java.util.ArrayList;
import java.util.Scanner;

public class StochasticBotCleanObservable {

    private static final int N = 5;

    private static boolean isDirtPoint(char[][] board, int posr, int posc) {
        if (posr < 0 || posr >= N || posc < 0 || posc >= N) {
            return false;
        }
        return board[posr][posc] == 'd';
    }

    private static ArrayList<int[]> findDirtPoints(char[][] board, int posr, int posc) {
        ArrayList<int[]> dirtPoints = new ArrayList<>();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (isDirtPoint(board, posr + i, posc + j)) {
                    dirtPoints.add(new int[] { posr + i, posc + j });
                };
            }
        }

        return dirtPoints;
    }

    private static int[] findClosestDirtPoint(ArrayList<int[]> dirtPoints, int posr, int posc) {
        int[] closestDirtPoint = new int[] { posr, posc };

        int minDirtScore = Integer.MAX_VALUE;

        for (int[] dirtPoint : dirtPoints) {
            int dirtScore = findScore(posr, posc, dirtPoint[0], dirtPoint[1]);
            if (dirtScore < minDirtScore) {
                minDirtScore = dirtScore;
                closestDirtPoint[0] = dirtPoint[0];
                closestDirtPoint[1] = dirtPoint[1];
            }
        }

        return closestDirtPoint;
    }

    private static int findScore(int fromX, int fromY, int toX, int toY) {
        return Math.abs(fromX - toX) + Math.abs(fromY - toY);
    }

    private static void nextMove(char[][] board, int posr, int posc) {
        ArrayList<int[]> dirtPoints = findDirtPoints(board, posr, posc);

        int[] closestDirtPoint = findClosestDirtPoint(dirtPoints, posr, posc);

        int dx = closestDirtPoint[0] - posr;
        int dy = closestDirtPoint[1] - posc;

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

        Scanner scanner = new Scanner(test2);

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
