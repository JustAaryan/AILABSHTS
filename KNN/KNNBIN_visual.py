import numpy as np
import matplotlib.pyplot as plt
from sklearn.neighbors import KNeighborsClassifier

# Dataset: height (cm), weight (kg), class (1=Pass, 0=Fail)
X = np.array([
    [165, 60],
    [170, 65],
    [160, 55],
    [175, 70],
    [155, 50],
    [168, 62],
    [162, 58],
    [172, 68],
    [158, 53],
    [167, 61]
])
y = np.array([1, 1, 0, 1, 0, 1, 0, 1, 0, 1])

# Train kNN
k = 3
knn = KNeighborsClassifier(n_neighbors=k)
knn.fit(X, y)

# Grid for background coloring
h = 1
x_min, x_max = X[:, 0].min() - 5, X[:, 0].max() + 5
y_min, y_max = X[:, 1].min() - 5, X[:, 1].max() + 5
xx, yy = np.meshgrid(np.arange(x_min, x_max, h), np.arange(y_min, y_max, h))
Z = knn.predict(np.c_[xx.ravel(), yy.ravel()])
Z = Z.reshape(xx.shape)

# Plot
plt.figure(figsize=(10, 6))
plt.contourf(xx, yy, Z, alpha=0.3, cmap=plt.cm.RdYlBu)
plt.scatter(X[y==0, 0], X[y==0, 1], color='red', label='Fail (0)', edgecolor='k')
plt.scatter(X[y==1, 0], X[y==1, 1], color='blue', label='Pass (1)', edgecolor='k')
plt.title(f"kNN Decision Boundary (k={k})")
plt.xlabel("Height (cm)")
plt.ylabel("Weight (kg)")
plt.legend()
plt.grid(True)
plt.show()
