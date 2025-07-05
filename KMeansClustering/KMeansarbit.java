import java.util.*;

class PointND {
    double[] features;
    int cluster;

    PointND(double[] features) {
        this.features = features;
    }

    double distanceTo(PointND other) {
        double sum = 0;
        for (int i = 0; i < features.length; i++) {
            sum += Math.pow(features[i] - other.features[i], 2);
        }
        return Math.sqrt(sum);
    }
}

public class KMeansarbit {
    public static void main(String[] args) {
        List<PointND> dataset = List.of(
            new PointND(new double[]{1.0, 1.5, 0.8, 1.2}), new PointND(new double[]{1.2, 1.7, 0.9, 1.1}),
            new PointND(new double[]{1.1, 1.4, 0.7, 1.3}), new PointND(new double[]{1.3, 1.6, 0.85, 1.15}),
            new PointND(new double[]{4.0, 4.5, 3.8, 4.2}), new PointND(new double[]{4.2, 4.7, 3.9, 4.1}),
            new PointND(new double[]{4.1, 4.4, 3.7, 4.3}), new PointND(new double[]{4.3, 4.6, 3.85, 4.15}),
            new PointND(new double[]{2.0, 2.2, 1.8, 2.1}), new PointND(new double[]{2.1, 2.3, 1.9, 2.0}),
            new PointND(new double[]{4.5, 4.8, 4.0, 4.4}), new PointND(new double[]{4.4, 4.9, 3.95, 4.35})
        );

        int k = 3;
        int dims = dataset.get(0).features.length;
        List<PointND> centroids = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            centroids.add(new PointND(Arrays.copyOf(dataset.get(rand.nextInt(dataset.size())).features, dims)));
        }

        boolean changed;
        do {
            changed = false;
            for (PointND p : dataset) {
                double minDist = Double.MAX_VALUE;
                int assigned = -1;
                for (int i = 0; i < k; i++) {
                    double d = p.distanceTo(centroids.get(i));
                    if (d < minDist) {
                        minDist = d;
                        assigned = i;
                    }
                }
                if (p.cluster != assigned) {
                    p.cluster = assigned;
                    changed = true;
                }
            }

            for (int i = 0; i < k; i++) {
                double[] sum = new double[dims];
                int count = 0;
                for (PointND p : dataset) {
                    if (p.cluster == i) {
                        for (int j = 0; j < dims; j++) sum[j] += p.features[j];
                        count++;
                    }
                }
                if (count > 0) {
                    for (int j = 0; j < dims; j++) sum[j] /= count;
                    centroids.set(i, new PointND(sum));
                }
            }
        } while (changed);

        for (PointND p : dataset) {
            System.out.println("Point " + Arrays.toString(p.features) + " -> Cluster " + p.cluster);
        }

        for (int i = 0; i < centroids.size(); i++) {
            System.out.println("Centroid " + i + " -> " + Arrays.toString(centroids.get(i).features));
        }

        double wcss = 0.0;
        for (PointND p : dataset) {
            wcss += Math.pow(p.distanceTo(centroids.get(p.cluster)), 2);
        }
        System.out.printf("\nWCSS: %.4f\n", wcss);
    }
}
