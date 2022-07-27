import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DeterministicBotCleanLarge {

    private static void findDirtPoints(ArrayList<int[]> dirtPoints, char[][] board, int height, int width, boolean[][] cache, int posr, int posc) {
        if (posr < 0 || posr >= height || posc < 0 || posc >= width) {
            return;
        }
        if (cache[posr][posc]) {
            return;
        }
        if (board[posr][posc] == 'd') {
            dirtPoints.add(new int[] { posr, posc });
        }
        cache[posr][posc] = true;
        findDirtPoints(dirtPoints, board, height, width, cache, posr - 1, posc);
        findDirtPoints(dirtPoints, board, height, width, cache, posr + 1, posc);
        findDirtPoints(dirtPoints, board, height, width, cache, posr, posc - 1);
        findDirtPoints(dirtPoints, board, height, width, cache, posr, posc + 1);
    }

    private static int[] findClosestDirtPoint(ArrayList<int[]> dirtPoints, int posr, int posc) {
        int[] closestDirtPoint = new int[] { posr, posc, -1 };

        int minDirtScore = Integer.MAX_VALUE;

        for (int next = 0; next < dirtPoints.size(); ++next) {
            int[] dirtPoint = dirtPoints.get(next);
            int dirtScore = findScore(posr, posc, dirtPoint[0], dirtPoint[1]);
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

    private static void nextMove(char[][] board, int height, int width, int posr, int posc) {
        boolean[][] cache = new boolean[height][width];
        Arrays.stream(cache).forEach(row -> Arrays.fill(row, false));

        ArrayList<int[]> dirtPoints = new ArrayList<>();
        findDirtPoints(dirtPoints, board, height, width, cache, posr, posc);

        int[] dirt = findClosestDirtPoint(dirtPoints, posr, posc);

        int dx = dirt[0] - posr;
        int dy = dirt[1] - posc;

        System.out.println("bot: " + posr + ", " + posc);
        System.out.println("dirt: " + dirt[0] + ", " + dirt[1]);
        System.out.println("diff: " + dx + ", " + dy);

        if (dy < 0) {
            System.out.printf("LEFT%n");
        } else if (dy > 0) {
            System.out.printf("RIGHT%n");
        } else if (dx < 0) {
            System.out.printf("UP%n");
        } else if (dx > 0) {
            System.out.printf("DOWN%n");
        } else {
            System.out.printf("CLEAN%n");
            dirtPoints.remove(dirt[2]);
        }
    }

    private static void play(char[][] board, int height, int width, int posr, int posc) {
        boolean[][] cache = new boolean[height][width];
        Arrays.stream(cache).forEach(row -> Arrays.fill(row, false));

        ArrayList<int[]> dirtPoints = new ArrayList<>();
        findDirtPoints(dirtPoints, board, height, width, cache, posr, posc);

        for (int[] dirtPoint : dirtPoints) {
            System.out.println(Arrays.toString(dirtPoint));
        }

        int x = posr;
        int y = posc;

        while (dirtPoints.size() != 0) {
            int[] dirt = findClosestDirtPoint(dirtPoints, x, y);

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

        Scanner scanner = new Scanner(test2);

        int posr = scanner.nextInt();
        int posc = scanner.nextInt();

        int height = scanner.nextInt();
        int width = scanner.nextInt();

        char[][] board = new char[height][width];

        int i = 0;
        while (scanner.hasNext()) {
            String row = scanner.next();
            for (int j = 0; j < width; ++j) {
                char cell = row.charAt(j);
                board[i][j] = cell;
            }
            ++i;
        }

        nextMove(board, height, width, posr, posc);
    }
}
