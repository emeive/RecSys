package MachineLearning.Algorithms;


// This class only looks for the nearest neighbor.

import MachineLearning.DataProcessing.Row;
import MachineLearning.DataProcessing.RowWithLabel;
import MachineLearning.DataProcessing.TableWithLabels;
import MachineLearning.Distances.Distance;
import MachineLearning.Distances.DistanceClient;

import java.util.List;
import java.util.TreeMap;

public class KNN  implements Algorithm<TableWithLabels, List<Double>, Integer>, DistanceClient {
    private TableWithLabels trainData;
    private Distance distance;

    public KNN(Distance distance) {
        this.distance = distance;
    }


    @Override
    public void train(TableWithLabels data) {
        this.trainData = data;
    }


    @Override
    public Integer estimate(List<Double> data) {

        TableWithLabels dataTable = trainData;
        TreeMap<Double,Integer> results = new TreeMap<>();
        for (Row f: dataTable.getRows()) {
            RowWithLabel row = (RowWithLabel) f;
            double distanceMetric = distance.calculateDistance(row.getData(), data);
            results.put(distanceMetric, row.getNumberClass());
        }
        return results.firstEntry().getValue();
    }


    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}