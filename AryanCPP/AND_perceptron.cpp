#include <iostream>
#include <vector>
using namespace std;

class Perceptron {
    vector<double> weights;
    double bias;
    double lr;
    int epochs;

public:
    Perceptron(double learning_rate = 0.1, int max_epochs = 100)
        : lr(learning_rate), epochs(max_epochs), weights(2, 0.0), bias(0.0) {}

    int predict(const vector<int>& x) {
        double sum = weights[0] * x[0] + weights[1] * x[1] + bias;
        return sum >= 0 ? 1 : 0;
    }

    void train(const vector<vector<int>>& X, const vector<int>& Y) {
        for (int epoch = 1; epoch <= epochs; ++epoch) {
            bool all_correct = true;
            for (size_t i = 0; i < X.size(); ++i) {
                int y_pred = predict(X[i]);
                int error = Y[i] - y_pred;

                if (error != 0) all_correct = false;

                for (int j = 0; j < 2; ++j)
                    weights[j] += lr * error * X[i][j];
                bias += lr * error;
            }
            cout << "Epoch " << epoch << " - Weights: " << weights[0] << ", " << weights[1] << " | Bias: " << bias << endl;
            if (all_correct) break;
        }
    }

    void test(const vector<vector<int>>& X, const vector<int>& Y) {
        int correct = 0;
        for (size_t i = 0; i < X.size(); ++i) {
            int output = predict(X[i]);
            cout << "Input: [" << X[i][0] << ", " << X[i][1] << "] -> Output: " << output << " | Expected: " << Y[i] << endl;
            if (output == Y[i]) correct++;
        }
        cout << "Accuracy: " << (double)correct / X.size() * 100 << "%\n";
    }
};

int main() {
    vector<vector<int>> X = {{0,0}, {0,1}, {1,0}, {1,1}};
    vector<int> Y = {0, 0, 0, 1}; //Truth Table Stored

    Perceptron p;
    cout << "Training Perceptron for AND Gate...\n";
    p.train(X, Y);

    cout << "\nTesting...\n";
    p.test(X, Y);

    return 0;
}