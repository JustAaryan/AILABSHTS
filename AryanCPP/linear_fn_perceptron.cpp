#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
using namespace std;

int main() {
    srand(time(0));
    const int samples = 10;
    const double lr = 0.01;
    vector<vector<double>> X(samples, vector<double>(3));
    vector<double> Y(samples);

    
    for (int i = 0; i < samples; ++i) {
        X[i][0] = (double)rand() / RAND_MAX;
        X[i][1] = (double)rand() / RAND_MAX;
        X[i][2] = (double)rand() / RAND_MAX;
        Y[i] = 2 * X[i][0] + 3 * X[i][1] - 1 * X[i][2] + 5;
    }


    vector<double> weights(3, 0.0);
    double bias = 0.0;

    
    for (int epoch = 1; epoch <= 100; ++epoch) {
        double mse = 0.0;
        for (int i = 0; i < samples; ++i) {
            double y_pred = bias;
            for (int j = 0; j < 3; ++j) y_pred += weights[j] * X[i][j];
            double error = Y[i] - y_pred;
            mse += error * error;
            for (int j = 0; j < 3; ++j)
                weights[j] += lr * error * X[i][j];
            bias += lr * error;
        }
        mse /= samples;
        cout << "Epoch " << epoch << " - MSE: " << mse << endl;
    }

    cout << "Final Weights: ";
    for (double w : weights) cout << w << " ";
    cout << "| Bias: " << bias << endl;
    return 0;
}
