import java.util.*;

class PuzzleState {
    int[][] board;
    String path;
    int blankX, blankY;

    PuzzleState(int[][] board, String path) {
        this.board = new int[3][3];
        for (int i = 0; i < 3; i++)
            this.board[i] = board[i].clone();
        this.path = path;
        findBlank();
    }

    void findBlank() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == 0) {
                    blankX = i;
                    blankY = j;
                    return;
                }
    }

    boolean isGoal() {
        int[][] goal = {{1,2,3},{4,5,6},{7,8,0}};
        return Arrays.deepEquals(this.board, goal);
    }

    int manhattanDistance() {
        int dist = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int val = board[i][j];
                if (val != 0) {
                    int goalX = (val - 1) / 3;
                    int goalY = (val - 1) % 3;
                    dist += Math.abs(i - goalX) + Math.abs(j - goalY);
                }
            }
        }
        return dist;
    }

    List<PuzzleState> getNeighbors() {
        List<PuzzleState> neighbors = new ArrayList<>();
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        String[] moves = {"Up", "Down", "Left", "Right"};

        for (int d = 0; d < 4; d++) {
            int newX = blankX + directions[d][0];
            int newY = blankY + directions[d][1];
            if (newX >= 0 && newX < 3 && newY >= 0 && newY < 3) {
                int[][] newBoard = new int[3][3];
                for (int i = 0; i < 3; i++)
                    newBoard[i] = board[i].clone();
                newBoard[blankX][blankY] = newBoard[newX][newY];
                newBoard[newX][newY] = 0;
                neighbors.add(new PuzzleState(newBoard, path + moves[d] + " -> "));
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PuzzleState) {
            return Arrays.deepEquals(board, ((PuzzleState) o).board);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}

public class EightPuzzle{
    public static void main(String[] args) {
        int[][] startBoard = {{1,2,3},{4,0,5},{7,8,6}};
        PuzzleState start = new PuzzleState(startBoard, "");

        Queue<PuzzleState> queue = new LinkedList<>();
        Set<PuzzleState> visited = new HashSet<>();

        queue.add(start);

        while (!queue.isEmpty()) {
            PuzzleState current = queue.poll();
            visited.add(current);

            System.out.println("Exploring state (h=" + current.manhattanDistance() + "):");
            for (int[] row : current.board) System.out.println(Arrays.toString(row));
            System.out.println();

            if (current.isGoal()) {
                System.out.println("Reached Goal!");
                System.out.println("Path: " + current.path);
                break;
            }

            for (PuzzleState neighbor : current.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }
    }
}
