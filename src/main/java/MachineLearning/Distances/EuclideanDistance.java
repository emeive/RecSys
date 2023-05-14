package MachineLearning.Distances;

import java.util.List;

public class EuclideanDistance implements Distance{
    public double calculateDistance(List<Double> row , List<Double> data ) {
        int i = 0;
        double sum = 0;
        for (Double x: row) {
            double z = data.get(i);
            sum += Math.pow(z - x, 2);
            i++;
        }
        return Math.sqrt(sum);
    }
}
