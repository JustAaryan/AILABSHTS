import java.util.*;

class MultiSample {
    double[] features;
    int label; // Class label: 0, 1, 2

    MultiSample(double[] features, int label) {
        this.features = features;
        this.label = label;
    }
}

public class kNNMulti {
    static double distance(MultiSample a, MultiSample b) {
        double sum = 0;
        for (int i = 0; i < a.features.length; i++) {
            sum += Math.pow(a.features[i] - b.features[i], 2);
        }
        return Math.sqrt(sum);
    }

    static int classify(List<MultiSample> trainingSet, MultiSample test, int k) {
        PriorityQueue<MultiSample> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> distance(s, test)));
        pq.addAll(trainingSet);

        Map<Integer, Integer> classCount = new HashMap<>();
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            int label = pq.poll().label;
            classCount.put(label, classCount.getOrDefault(label, 0) + 1);
        }

        return classCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .get()
            .getKey();
    }

    public static void main(String[] args) {
        List<MultiSample> data = List.of(
            new MultiSample(new double[]{5.1, 3.5, 1.4}, 0), new MultiSample(new double[]{4.9, 3.0, 1.3}, 0),
            new MultiSample(new double[]{5.0, 3.4, 1.5}, 0), new MultiSample(new double[]{7.0, 3.2, 4.7}, 1),
            new MultiSample(new double[]{6.4, 3.2, 4.5}, 1), new MultiSample(new double[]{6.9, 3.1, 4.9}, 1),
            new MultiSample(new double[]{5.5, 2.3, 4.0}, 2), new MultiSample(new double[]{6.5, 2.8, 4.6}, 2),
            new MultiSample(new double[]{5.7, 2.8, 4.1}, 2), new MultiSample(new double[]{6.3, 3.3, 6.0}, 2),
            new MultiSample(new double[]{5.8, 2.7, 5.1}, 2), new MultiSample(new double[]{6.1, 3.0, 4.8}, 2)
        );

        // Shuffle and split
        List<MultiSample> shuffled = new ArrayList<>(data);
        Collections.shuffle(shuffled);
        int splitIndex = (int) (shuffled.size() * 0.8);
        List<MultiSample> train = shuffled.subList(0, splitIndex);
        List<MultiSample> test = shuffled.subList(splitIndex, shuffled.size());

        int k = 5;
        int correct = 0;
        for (MultiSample s : test) {
            int predicted = classify(train, s, k);
            if (predicted == s.label) correct++;
            System.out.println("Test Point " + Arrays.toString(s.features) + " -> Predicted: " + predicted + ", Actual: " + s.label);
        }

        double accuracy = (double) correct / test.size() * 100;
        System.out.printf("\nAccuracy: %.2f%%\n", accuracy);
    }
}
