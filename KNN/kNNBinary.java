import java.util.*;

class Sample {
    double height;
    double weight;
    int label; // 1 = Pass, 0 = Fail

    Sample(double height, double weight, int label) {
        this.height = height;
        this.weight = weight;
        this.label = label;
    }
}

public class kNNBinary {
    static double distance(Sample a, Sample b) {
        return Math.sqrt(Math.pow(a.height - b.height, 2) + Math.pow(a.weight - b.weight, 2));
    }

    static int classify(List<Sample> trainingSet, Sample test, int k) {
        PriorityQueue<Sample> pq = new PriorityQueue<>(Comparator.comparingDouble(s -> distance(s, test)));
        pq.addAll(trainingSet);

        int passCount = 0, failCount = 0;
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            Sample neighbor = pq.poll();
            if (neighbor.label == 1) passCount++;
            else failCount++;
        }
        return passCount >= failCount ? 1 : 0;
    }

    public static void main(String[] args) {
        List<Sample> data = List.of(
            new Sample(165, 60, 1), new Sample(170, 65, 1), new Sample(160, 55, 0),
            new Sample(175, 70, 1), new Sample(155, 50, 0), new Sample(168, 62, 1),
            new Sample(162, 58, 0), new Sample(172, 68, 1), new Sample(158, 53, 0),
            new Sample(167, 61, 1)
        );

        // Shuffle and split
        List<Sample> shuffled = new ArrayList<>(data);
        Collections.shuffle(shuffled);
        int splitIndex = (int)(shuffled.size() * 0.8);
        List<Sample> train = shuffled.subList(0, splitIndex);
        List<Sample> test = shuffled.subList(splitIndex, shuffled.size());

        int k = 3;
        int correct = 0;
        for (Sample s : test) {
            int predicted = classify(train, s, k);
            if (predicted == s.label) correct++;
            System.out.println("Test Point (" + s.height + ", " + s.weight + ") -> Predicted: " + predicted + ", Actual: " + s.label);
        }

        double accuracy = (double) correct / test.size() * 100;
        System.out.printf("\nAccuracy: %.2f%%\n", accuracy);
    }
}
