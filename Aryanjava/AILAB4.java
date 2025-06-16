package Aryanjava;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AILAB4 extends JPanel implements ActionListener {
    static  int GRID_SIZE = 6;
    static  int CELL_SIZE = 100;

    enum State { CLEAN, DIRTY }

    private State[][] grid = new State[GRID_SIZE][GRID_SIZE];
    private boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
    private int agentX, agentY;
    private javax.swing.Timer timer;

    public AILAB4() {
        setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        initializeGrid();
        randomizeStartPosition();
        timer = new javax.swing.Timer(500, this);
        timer.start();
    }

    private void initializeGrid() {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                grid[y][x] = State.DIRTY;
                visited[y][x] = false;
            }
        }
    }

    private void randomizeStartPosition() {
        Random rand = new Random();
        agentX = rand.nextInt(GRID_SIZE);
        agentY = rand.nextInt(GRID_SIZE);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawAgent(g);
    }

    private void drawGrid(Graphics g) {
        for (int y = 0; y < GRID_SIZE; y++) {
            for (int x = 0; x < GRID_SIZE; x++) {
                if (grid[y][x] == State.DIRTY)
                    g.setColor(Color.LIGHT_GRAY);
                else
                    g.setColor(Color.WHITE);

                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                g.setColor(Color.BLACK);
                g.drawRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

                if (visited[y][x]) {
                    g.setColor(new Color(0, 255, 0, 60));
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }

    private void drawAgent(Graphics g) {
        int cx = agentX * CELL_SIZE + CELL_SIZE / 2;
        int cy = agentY * CELL_SIZE + CELL_SIZE / 2;
        int radius = CELL_SIZE / 4;
        g.setColor(Color.BLUE);
        g.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
    }

    private void cleanCurrentCell() {
        grid[agentY][agentX] = State.CLEAN;
        visited[agentY][agentX] = true;
    }

    private boolean allVisited() {
        for (boolean[] row : visited)
            for (boolean cell : row)
                if (!cell)
                    return false;
        return true;
    }

    private java.util.List<Point> getUnvisitedNeighbors(int x, int y) {
        int[] dx = {0, -1, 1, 0}; // N, W, E, S Directions
        int[] dy = {-1, 0, 0, 1};

        java.util.List<Point> list = new ArrayList<>();
        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i], ny = y + dy[i];
            if (nx >= 0 && nx < GRID_SIZE && ny >= 0 && ny < GRID_SIZE && !visited[ny][nx]) {
                list.add(new Point(nx, ny));
            }
        }
        return list;
    }

    private void moveAgent() {
        cleanCurrentCell();

        java.util.List<Point> neighbors = getUnvisitedNeighbors(agentX, agentY);
        if (!neighbors.isEmpty()) {
            Point next = neighbors.get(0);
            agentX = next.x;
            agentY = next.y;
        } else {
            // BFS implemented
            Point next = bfsNextUnvisited(agentX, agentY);
            if (next != null) {
                agentX = next.x;
                agentY = next.y;
            }
        }
    }

    private Point bfsNextUnvisited(int x, int y) {
        boolean[][] seen = new boolean[GRID_SIZE][GRID_SIZE];
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));
        seen[y][x] = true;

        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};

        while (!queue.isEmpty()) {
            Point curr = queue.poll();
            for (int i = 0; i < dx.length; i++) {
                int nx = curr.x + dx[i];
                int ny = curr.y + dy[i];
                if (nx >= 0 && nx < GRID_SIZE && ny >= 0 && ny < GRID_SIZE && !seen[ny][nx]) {
                    if (!visited[ny][nx]) return new Point(nx, ny);
                    queue.add(new Point(nx, ny));
                    seen[ny][nx] = true;
                }
            }
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (allVisited()) {
            timer.stop();
            repaint();
            JOptionPane.showMessageDialog(this, "All cells cleaned");
            return;
        }
        moveAgent();
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vacuum Cleaner Agent");
        AILAB4 panel = new AILAB4();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
