import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Simple extends JPanel implements ActionListener {
    static final int GRID = 6, CELL = 100;
    enum State { CLEAN, DIRTY }
    private State[][] grid = new State[GRID][GRID];
    private int agentX, agentY;
    private javax.swing.Timer timer;

    public Simple() {
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
                grid[y][x] = new Random().nextBoolean() ? State.DIRTY : State.CLEAN;
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
            }
        }
        g.setColor(Color.BLUE);
        int cx = agentX * CELL + CELL / 2;
        int cy = agentY * CELL + CELL / 2;
        g.fillOval(cx - CELL/4, cy - CELL/4, CELL/2, CELL/2);
    }

    private void clean() {
        if (grid[agentY][agentX] == State.DIRTY)
            grid[agentY][agentX] = State.CLEAN;
    }

    private void moveRandom() {
        int[] dx = {0, -1, 1, 0};
        int[] dy = {-1, 0, 0, 1};
       java.util.List<Point> dirs = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nx = agentX + dx[i], ny = agentY + dy[i];
            if (nx >= 0 && nx < GRID && ny >= 0 && ny < GRID)
                dirs.add(new Point(nx, ny));
        }
        if (!dirs.isEmpty()) {
            Point p = dirs.get(new Random().nextInt(dirs.size()));
            agentX = p.x;
            agentY = p.y;
        }
    }

    public void actionPerformed(ActionEvent e) {
        clean();
        moveRandom();
        repaint();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Simple Reflex Agent");
        f.add(new Simple());
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
