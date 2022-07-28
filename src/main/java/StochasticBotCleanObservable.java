import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class StochasticBotCleanObservable {

    private static final int N = 5;

    private static final boolean[][] visited = new boolean[N][N];

    private static boolean isTargetPoint(char[][] board, int posr, int posc, char target) {
        if (posr < 0 || posr >= N || posc < 0 || posc >= N) {
            return false;
        }
        return board[posr][posc] == target;
    }

    private static void findInvisiblePoints(ArrayList<int[]> invisiblePoints, char[][] board, boolean[][] cache, int posr, int posc) {
        if (posr < 0 || posr >= N || posc < 0 || posc >= N) {
            return;
        }
        if (cache[posr][posc]) {
            return;
        }
        cache[posr][posc] = true;
        if (isTargetPoint(board, posr, posc, 'o')) {
            invisiblePoints.add(new int[] { posr, posc });
            return;
        }
        findInvisiblePoints(invisiblePoints, board, cache, posr - 1, posc);
        findInvisiblePoints(invisiblePoints, board, cache, posr + 1, posc);
        findInvisiblePoints(invisiblePoints, board, cache, posr, posc - 1);
        findInvisiblePoints(invisiblePoints, board, cache, posr, posc + 1);
    }

    private static ArrayList<int[]> findDirtPoints(char[][] board, int posr, int posc) {
        ArrayList<int[]> targetPoints = new ArrayList<>();

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (isTargetPoint(board, posr + i, posc + j, 'd')) {
                    targetPoints.add(new int[] { posr + i, posc + j });
                };
            }
        }

        return targetPoints;
    }

    private static int[] findClosestTargetPoint(ArrayList<int[]> targetPoints, int posr, int posc) {
        int[] closestTargetPoint = new int[] { posr, posc };

        int minTargetScore = Integer.MAX_VALUE;

        for (int[] targetPoint : targetPoints) {
            int targetScore = findScore(posr, posc, targetPoint[0], targetPoint[1]);
            if (targetScore < minTargetScore) {
                minTargetScore = targetScore;
                closestTargetPoint[0] = targetPoint[0];
                closestTargetPoint[1] = targetPoint[1];
            }
        }

        return closestTargetPoint;
    }

    private static int findScore(int fromX, int fromY, int toX, int toY) {
        return Math.abs(fromX - toX) + Math.abs(fromY - toY);
    }

    private static void nextMove(char[][] board, int posr, int posc) {
        ArrayList<int[]> dirtPoints = findDirtPoints(board, posr, posc);

        int dx, dy;

        if (dirtPoints.size() == 0) {
            ArrayList<int[]> invisiblePoints = new ArrayList<>();

            boolean[][] cache = new boolean[N][N];
            Arrays.stream(cache).forEach(row -> Arrays.fill(row, false));

            findInvisiblePoints(invisiblePoints, board, cache, posr, posc);

            int[] closestInvisiblePoint = findClosestTargetPoint(invisiblePoints, posr, posc);

            dx = closestInvisiblePoint[0] - posr;
            dy = closestInvisiblePoint[1] - posc;
        } else {
            int[] closestDirtPoint = findClosestTargetPoint(dirtPoints, posr, posc);

            dx = closestDirtPoint[0] - posr;
            dy = closestDirtPoint[1] - posc;
        }

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

        Arrays.stream(visited).forEach(row -> Arrays.fill(row, false));

        nextMove(board, posr, posc);
    }
}
