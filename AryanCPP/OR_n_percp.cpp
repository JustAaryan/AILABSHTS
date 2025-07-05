#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

class Perceptron {
    vector<double> weights;
    double bias;
    double lr;
    int epochs;

public:
    Perceptron(int input_size, double learning_rate = 0.1, int max_epochs = 100)
        : weights(input_size, 0.0), bias(0.0), lr(learning_rate), epochs(max_epochs) {}

    int predict(const vector<int>& x) {
        double sum = bias;
        for (size_t i = 0; i < weights.size(); ++i)
            sum += weights[i] * x[i];
        return sum >= 0 ? 1 : 0;
    }

    void train(const vector<vector<int>>& X, const vector<int>& Y) {
        for (int epoch = 1; epoch <= epochs; ++epoch) {
            bool all_correct = true;
            for (size_t i = 0; i < X.size(); ++i) {
                int y_pred = predict(X[i]);
                int error = Y[i] - y_pred;
                if (error != 0) all_correct = false;
                for (size_t j = 0; j < weights.size(); ++j)
                    weights[j] += lr * error * X[i][j];
                bias += lr * error;
            }
            cout << "Epoch " << epoch << " - Weights: ";
            for (double w : weights) cout << w << " ";
            cout << "| Bias: " << bias << endl;
            if (all_correct) break;
        }
    }

    void test(const vector<vector<int>>& X, const vector<int>& Y) {
        int correct = 0;
        for (size_t i = 0; i < X.size(); ++i) {
            int output = predict(X[i]);
            cout << "Input: ";
            for (int x : X[i]) cout << x << " ";
            cout << "-> Output: " << output << " | Expected: " << Y[i] << endl;
            if (output == Y[i]) correct++;
        }
        cout << "Accuracy: " << (double)correct / X.size() * 100 << "%\n";
    }
};

void generate_or_truth_table(int n, vector<vector<int>>& X, vector<int>& Y) {
    int rows = pow(2, n);
    for (int i = 0; i < rows; ++i) {
        vector<int> row(n);
        int val = i, or_result = 0;
        for (int j = n - 1; j >= 0; --j) {
            row[j] = val % 2;
            val /= 2;
            or_result |= row[j];
        }
        X.push_back(row);
        Y.push_back(or_result);
    }
}

int main() {
    int n ;
    cout<<"Provide no of input\n";
    cin>>n;
    vector<vector<int>> X;
    vector<int> Y;
    generate_or_truth_table(n, X, Y);

    Perceptron p(n);
    cout << "Training Perceptron for " << n << "-input OR Gate...\n";
    p.train(X, Y);

    cout << "\nTesting...\n";
    p.test(X, Y);

    return 0;
}