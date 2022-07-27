import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DeterministicBotClean {

    private static void findDirtPoints(ArrayList<int[]> dirtPoints, char[][] board, boolean[][] cache, int i, int j) {
        if (i < 0 || i >= 5 || j < 0 || j >= 5) {
            return;
        }
        if (cache[i][j]) {
            return;
        }
        if (board[i][j] == 'd') {
            dirtPoints.add(new int[] { i, j });
        }

        cache[i][j] = true;

        findDirtPoints(dirtPoints, board, cache, i - 1, j);
        findDirtPoints(dirtPoints, board, cache, i + 1, j);
        findDirtPoints(dirtPoints, board, cache, i, j - 1);
        findDirtPoints(dirtPoints, board, cache, i, j + 1);
    }

    private static int[] findClosestDirtPoint(ArrayList<int[]> dirtPoints, char[][] board, boolean[][] cache, int i, int j) {
        int[] closestDirtPoint = new int[] { i, j, -1 };

        int minDirtScore = Integer.MAX_VALUE;

        for (int next = 0; next < dirtPoints.size(); ++next) {
            int[] dirtPoint = dirtPoints.get(next);
            int dirtScore = findScore(i, j, dirtPoint[0], dirtPoint[1]);
            if (dirtScore < minDirtScore) {
                minDirtScore = dirtScore;
                closestDirtPoint[0] = dirtPoint[0];
                closestDirtPoint[1] = dirtPoint[1];
                closestDirtPoint[2] = next;
            }
        }

        return closestDirtPoint;
    }

    private static int findScore(int fromX, int fromY, int toX, int toY) {
        return Math.abs(fromX - toX) + Math.abs(fromY - toY);
    }

    private static void play(char[][] board, int posr, int posc) {
        boolean[][] cache = new boolean[5][5];
        Arrays.stream(cache).forEach(row -> Arrays.fill(row, false));

        ArrayList<int[]> dirtPoints = new ArrayList<>();
        findDirtPoints(dirtPoints, board, cache, posr, posc);

        for (int[] dirtPoint : dirtPoints) {
            System.out.println(Arrays.toString(dirtPoint));
        }

        int x = posr;
        int y = posc;

        while (dirtPoints.size() != 0) {
            int[] dirt = findClosestDirtPoint(dirtPoints, board, cache, x, y);

            if (dirt[2] == -1) {
                break;
            }

            int dx = dirt[0] - x;
            int dy = dirt[1] - y;

            System.out.println("bot: " + x + ", " + y);
            System.out.println("dirt: " + dirt[0] + ", " + dirt[1]);
            System.out.println("diff: " + dx + ", " + dy);

            if (dy < 0) {
                System.out.printf("DOWN%n");
                --y;
            } else if (dy > 0) {
                System.out.printf("UP%n");
                ++y;
            } else if (dx < 0) {
                System.out.printf("RIGHT%n");
                --x;
            } else if (dx > 0) {
                System.out.printf("LEFT%n");
                ++x;
            } else {
                System.out.printf("CLEAN%n");
                dirtPoints.remove(dirt[2]);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        Scanner scanner = new Scanner(test2);

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

        play(board, posr, posc);
    }
}
