
import java.util.*;

class Point2D {
    double x, y;
    int cluster;

    Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distanceTo(Point2D p) {
        return Math.sqrt(Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2));
    }
}

public class KMeans2 {
    static List<Point2D> dataset = Arrays.asList(
        new Point2D(1.0, 1.5), new Point2D(1.5, 1.8), new Point2D(1.2, 1.3),
        new Point2D(1.4, 1.7), new Point2D(5.0, 5.5), new Point2D(5.5, 5.8),
        new Point2D(5.2, 5.3), new Point2D(5.4, 5.7), new Point2D(2.0, 2.1),
        new Point2D(1.8, 1.9), new Point2D(5.1, 5.4), new Point2D(5.3, 5.6)
    );

    public static void main(String[] args) {
        int k = 2;
        List<Point2D> centroids = new ArrayList<>();
        Random rand = new Random();

        // Initialize centroids randomly from data
        for (int i = 0; i < k; i++) {
            centroids.add(new Point2D(dataset.get(rand.nextInt(dataset.size())).x,
                                      dataset.get(rand.nextInt(dataset.size())).y));
        }

        boolean changed;
        do {
            changed = false;

            // Assign clusters
            for (Point2D p : dataset) {
                double minDist = Double.MAX_VALUE;
                int assignedCluster = -1;
                for (int i = 0; i < k; i++) {
                    double dist = p.distanceTo(centroids.get(i));
                    if (dist < minDist) {
                        minDist = dist;
                        assignedCluster = i;
                    }
                }
                if (p.cluster != assignedCluster) {
                    changed = true;
                    p.cluster = assignedCluster;
                }
            }

            // Update centroids
            for (int i = 0; i < k; i++) {
                double sumX = 0, sumY = 0;
                int count = 0;
                for (Point2D p : dataset) {
                    if (p.cluster == i) {
                        sumX += p.x;
                        sumY += p.y;
                        count++;
                    }
                }
                if (count > 0) {
                    centroids.set(i, new Point2D(sumX / count, sumY / count));
                }
            }

        } while (changed);

        // Display results
        for (int i = 0; i < dataset.size(); i++) {
            Point2D p = dataset.get(i);
            System.out.printf("Point (%.1f, %.1f) -> Cluster %d\n", p.x, p.y, p.cluster);
        }

        for (int i = 0; i < centroids.size(); i++) {
            System.out.printf("Centroid %d -> (%.2f, %.2f)\n", i, centroids.get(i).x, centroids.get(i).y);
        }

        // WCSS
        double wcss = 0.0;
        for (Point2D p : dataset) {
            wcss += Math.pow(p.distanceTo(centroids.get(p.cluster)), 2);
        }
        System.out.printf("\nWCSS: %.4f\n", wcss);
    }
}