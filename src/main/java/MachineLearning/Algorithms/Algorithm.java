package MachineLearning.Algorithms;


import MachineLearning.DataProcessing.Table;

public interface Algorithm<T extends Table, U, V> {
    void train(T data) throws InvalidClusterSizeException, EmptyPointsListException;
    V estimate(U data);
}

//
