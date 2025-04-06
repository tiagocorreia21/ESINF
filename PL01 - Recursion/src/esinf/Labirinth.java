package esinf;

public class Labirinth {

    /**
     * @param actual the labyrinth in its actual (marked) form
     * @param x      coordinate x in the labyrinth
     * @param y      coordinate y in the labyrinth
     * @return the marked labyrinth or null if there is no way
     */
    public static int[][] check(int[][] actual, int x, int y) {
        if (findPath(actual, x, y, 0, 0)) {
            return actual;
        } else {
            return null;
        }
    }

    private static boolean findPath(int[][] labyrinth, int destX, int destY, int x, int y) {
        // Check if out of bounds or if it's a wall
        if (x < 0 || y < 0 || x >= labyrinth.length || y >= labyrinth[0].length || labyrinth[x][y] == 0) {
            return false;
        }

        // Check if destination is reached
        if (x == destX && y == destY) {
            labyrinth[x][y] = 9;
            return true;
        }

        // Check if the cell is already part of the path or visited
        if (labyrinth[x][y] == 9 || labyrinth[x][y] == 2) {
            return false;
        }

        // Mark the current cell as part of the path
        labyrinth[x][y] = 9;

        // Explore the four possible directions: North, East, South, West
        if (findPath(labyrinth, destX, destY, x - 1, y) || // North
                findPath(labyrinth, destX, destY, x, y + 1) || // East
                findPath(labyrinth, destX, destY, x + 1, y) || // South
                findPath(labyrinth, destX, destY, x, y - 1)) { // West
            return true;
        }

        // Backtrack: mark the cell as visited but not part of the solution
        labyrinth[x][y] = 2;
        return false;
    }
}