import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Utility extends JPanel implements ActionListener {
    static final int GRID = 6, CELL = 100;
    enum State { CLEAN, DIRTY }
    private State[][] grid = new State[GRID][GRID];
    private boolean[][] visited = new boolean[GRID][GRID];
    private int agentX, agentY;
    private javax.swing.Timer timer;

    public Utility() {
        setPreferredSize(new Dimension(GRID * CELL, GRID * CELL));
        initGrid();
        agentX = new Random().nextInt(GRID);
        agentY = new Random().nextInt(GRID);
        timer = new javax.swing.Timer(500, this);
        timer.start();
    }

    private void initGrid() {
        for (int y = 0; y < GRID; y++)
            for (int x = 0; x < GRID; x++)
                grid[y][x] = new Random().nextInt(100) < 60 ? State.DIRTY : State.CLEAN;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < GRID; y++) {
            for (int x = 0; x < GRID; x++) {
                g.setColor(grid[y][x] == State.DIRTY ? Color.LIGHT_GRAY : Color.WHITE);
                g.fillRect(x * CELL, y * CELL, CELL, CELL);
                g.setColor(Color.BLACK);
                g.drawRect(x * CELL, y * CELL, CELL, CELL);
                if (visited[y][x]) {
                    g.setColor(new Color(0, 255, 0, 50));
                    g.fillRect(x * CELL, y * CELL, CELL, CELL);
                }
            }
        }
        g.setColor(Color.RED);
        int cx = agentX * CELL + CELL / 2;
        int cy = agentY * CELL + CELL / 2;
        g.fillOval(cx - CELL/4, cy - CELL/4, CELL/2, CELL/2);
    }

    private void clean() {
        grid[agentY][agentX] = State.CLEAN;
        visited[agentY][agentX] = true;
    }

    private void moveToBestUtility() {
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};
        Point best = null;
        int maxUtility = -1;

        for (int i = 0; i < 4; i++) {
            int nx = agentX + dx[i], ny = agentY + dy[i];
            if (nx >= 0 && nx < GRID && ny >= 0 && ny < GRID) {
                int utility = 0;
                if (grid[ny][nx] == State.DIRTY) utility += 10;
                if (!visited[ny][nx]) utility += 1;
                if (utility > maxUtility) {
                    maxUtility = utility;
                    best = new Point(nx, ny);
                }
            }
        }

        if (best != null) {
            agentX = best.x;
            agentY = best.y;
        }
    }

    public void actionPerformed(ActionEvent e) {
        clean();
        moveToBestUtility();
        repaint();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Utility-Based Agent");
        f.add(new Utility());
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
