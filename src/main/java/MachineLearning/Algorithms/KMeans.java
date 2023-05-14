package MachineLearning.Algorithms;


import MachineLearning.DataProcessing.Row;
import MachineLearning.DataProcessing.Table;
import MachineLearning.Distances.Distance;
import MachineLearning.Distances.DistanceClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class KMeans implements Algorithm<Table, List<Double>, Integer>, DistanceClient {



    //ATTRIBUTES
    private final int numClusters;
    private final int numIterations;
    private final long seed;
    private Map<Row, ArrayList<Row>> clustersMap;
    private Map<Row, Integer> clusterIdentifier;

    private Distance distance;

    //KMEANS CONSTRUCTOR
    public KMeans(int numClusters, int numIterations, long seed, Distance distance) {
        this.numClusters = numClusters;
        this.numIterations = numIterations;
        this.seed = seed;
        this.clustersMap = new HashMap<>();
        this.clusterIdentifier = new HashMap<>();
        this.distance = distance;
    }

    //Get cluster identifier is used in tests to improve visualization
    public Map<Row, Integer> getClusterIdentifier() {
        return clusterIdentifier;
    }

    //===================== TRAIN =====================================================================================
    @Override
    public void train(Table data) throws InvalidClusterSizeException, EmptyPointsListException {

        //We check that the number of clusters is less than the number of rows in the table.
        if (this.numClusters > data.getRows().size()) {
                throw new InvalidClusterSizeException("The number of clusters must be lower than the number of rows.");
        }
        //On each train, reset clusterMap
        this.clustersMap = new HashMap<>();

        List<Row> initialCentrods = initialRepresentatives(this.numClusters, data);
        for (Row centroid : initialCentrods) {
            clustersMap.put(centroid, new ArrayList<>());
        }

        // For each iteration in the training.
        for (int i = 0; i < numIterations; i++) {
            //We reset the data associated with each cluster on each iteration
            clustersReset();
            for (Row row : data.getRows()) {

                //For each row in table, we look for the closest centroid.
                Row closestCentroid = findClosestCentroid(row.getData(), clustersMap);
                ArrayList<Row> cluster = clustersMap.get(closestCentroid);
                cluster.add(row);
            }
            // We recalculate the new centroids from the new clusters.
            clustersMap = recalculatedClusters();
        }
        //we create a map [key=centroid; value=int] to better identify the centroids.
        //PythonPlot package contains the algorithm used to plot the output of this method.
        //An example of the result can be seen in Files > kaggle_basic_trained.png
        setClusterIdentifier();
        //Write training set on "Files > data" in order to use in testing.
        //trainingSetToCSV();
    }

                                                    //TRAIN METHODS

    private void setClusterIdentifier() {
        // Create a map to associate each centroid (Row) with a cluster identifier (Integer)
        clusterIdentifier = new HashMap<>();
        List<Row> centroids = new ArrayList<>(clustersMap.keySet());

        // Loop through the centroids in the clusters map and assign a unique identifier to each centroid
        for (int i = 0; i < centroids.size(); i++) {
            Row centroid = centroids.get(i);
            clusterIdentifier.put(centroid, i);
        }
    }


    //This method is used to reassign the new centroids. We needed to create a new map because we cannot modify the original map while iterating over it.
    private Map<Row, ArrayList<Row>> recalculatedClusters() throws EmptyPointsListException {
        Map<Row, ArrayList<Row>> recalculatedClustersMap = new HashMap<>();
        for (Row centroid : clustersMap.keySet()) {
            Row newCentroid = centroid(clustersMap.get(centroid));
            recalculatedClustersMap.put(newCentroid, clustersMap.get(centroid));
        }
        return recalculatedClustersMap;
    }


    private Row centroid(ArrayList<Row> points) throws EmptyPointsListException {
        if (points.isEmpty()) {
            throw new EmptyPointsListException("The points list is empty.");
        }
        int rowSize = points.get(0).getData().size();
        Double[] newCentroid = new Double[rowSize];
        Arrays.fill(newCentroid, 0.0);
        for (Row point : points) {
            List<Double> newList = point.getData();
            for (int i = 0; i < rowSize; i++) {
                newCentroid[i] += newList.get(i);
            }
        }
        for (int i = 0; i < rowSize; i++) {
            newCentroid[i] /= points.size();
        }
        return new Row(Arrays.asList(newCentroid));
    }

    private void clustersReset() {
        for (Row centroid : clustersMap.keySet()) {
            clustersMap.put(centroid, new ArrayList<>());
        }
    }

    private void trainingSetToCSV() {
        File file = new File("src/main/java/es/uji/Files/trainingSet.csv");
        FileWriter csvWriter = null;
        try {
            csvWriter = new FileWriter(file);
            csvWriter.append("x,y,group");
            csvWriter.append("\n");

            for (Row centroid : clustersMap.keySet()) {
                for (Row r : clustersMap.get(centroid)) {
                    String data = r.getData().toString().replaceAll("\\[|\\]|\\s+", "");
                    String line = data + "," + clusterIdentifier.get(centroid) + "\n";
                    csvWriter.append(line);
                }
            }
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Choose n random centroids
    private List<Row> initialRepresentatives(int numClusters, Table dataset) {
        List<Row> representatives = new ArrayList<>();
        Set<Integer> selectedIndices = new HashSet<>();
        Random random = new Random();
        random.setSeed(this.seed);
        int datasetSize = dataset.getRows().size();
        //check that the selected rows are not repeated.
        while (selectedIndices.size() < numClusters) {
            int rowNum = random.nextInt(datasetSize);
            if (!selectedIndices.contains(rowNum)) {
                selectedIndices.add(rowNum);
                representatives.add(dataset.getRowAt(rowNum));
            }
        }
        return representatives;
    }



    //=====================================ESTIMATE==============================================================
    @Override
    public Integer estimate(List<Double> data) {
        // Find the closest centroid to the input data and return its associated cluster identifier
        return clusterIdentifier.get(findClosestCentroid(data, clustersMap));
    }


    //==============================COMMON METHODS TO TRAIN AND ESTIMATE=========================================


    // This method will decide to which centroid each row of the table will be assigned. It is used in estimate and train.
    private Row findClosestCentroid(List<Double> data, Map<Row, ArrayList<Row>> clusters) {
        double minDistance = 0.0;
        Row minCentroid = null;
        for (Row centroid : clusters.keySet()) {
            if (minCentroid == null) {
                minCentroid = centroid;
                minDistance = distance.calculateDistance(centroid.getData(), data);
            } else {
                double dist = distance.calculateDistance(centroid.getData(), data);
                if (dist < minDistance) {
                    minCentroid = centroid;
                    minDistance = dist;
                }
            }
        }
        return minCentroid;
    }

    @Override
    public void setDistance(Distance distance) {
        this.distance = distance;
    }
}
