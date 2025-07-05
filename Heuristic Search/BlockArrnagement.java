import java.util.*;

class BlockState {
    List<Character> blocks;
    String path;

    BlockState(List<Character> blocks, String path) {
        this.blocks = new ArrayList<>(blocks);
        this.path = path;
    }

    int heuristic() {
        char[] goal = {'A','B','C','D'};
        int h = 0;
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) != goal[i]) h++;
        }
        return h;
    }

    List<BlockState> getNeighbors() {
        List<BlockState> neighbors = new ArrayList<>();
        for (int i = 0; i < blocks.size() - 1; i++) {
            List<Character> newBlocks = new ArrayList<>(blocks);
            Collections.swap(newBlocks, i, i+1);
            neighbors.add(new BlockState(newBlocks, path + "Swap " + blocks.get(i) + " and " + blocks.get(i+1) + " -> "));
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BlockState) {
            return blocks.equals(((BlockState) o).blocks);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return blocks.hashCode();
    }
}

public class BlockArrnagement{
    public static void main(String[] args) {
        List<Character> initial = Arrays.asList('C','A','D','B');
        BlockState current = new BlockState(initial, "");
        System.out.println("Initial State: " + current.blocks);

        while (true) {
            List<BlockState> neighbors = current.getNeighbors();
            BlockState best = current;
            for (BlockState neighbor : neighbors) {
                if (neighbor.heuristic() < best.heuristic()) {
                    best = neighbor;
                }
            }
            if (best == current) {
                System.out.println("Stuck at local optimum or reached goal.");
                break;
            }
            current = best;
            System.out.println("Moved to: " + current.blocks + " | h = " + current.heuristic());
        }
        System.out.println("Path: " + current.path);
    }
}

