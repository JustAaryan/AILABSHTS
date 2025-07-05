#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
using namespace std;

int main() {
    srand(time(0));
    const int samples = 10;
    const double lr = 0.01;
    int n;
    cout<<"Provide no of test cases:\n";
    cin>>n; 

    vector<vector<double>> X(samples, vector<double>(n));
    vector<double> true_weights(n);
    vector<double> Y(samples);
    double true_bias = 5.0;

    
    for (int i = 0; i < n; ++i) true_weights[i] = 2.0 * ((double)rand() / RAND_MAX) - 1.0;
    for (int i = 0; i < samples; ++i) {
        double y = true_bias;
        for (int j = 0; j < n; ++j) {
            X[i][j] = (double)rand() / RAND_MAX;
            y += true_weights[j] * X[i][j];
        }
        Y[i] = y;
    }

    
    vector<double> weights(n, 0.0);
    double bias = 0.0;

    
    for (int epoch = 1; epoch <= 100; ++epoch) {
        double mse = 0.0;
        for (int i = 0; i < samples; ++i) {
            double y_pred = bias;
            for (int j = 0; j < n; ++j) y_pred += weights[j] * X[i][j];
            double error = Y[i] - y_pred;
            mse += error * error;
            for (int j = 0; j < n; ++j)
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
