package Modelo;

import MachineLearning.Algorithms.*;
import MachineLearning.DataProcessing.Table;
import MachineLearning.DataReader.CSVLabeledFileReader;
import MachineLearning.DataReader.CSVUnlabeledFileReader;
import MachineLearning.DataReader.ReaderTemplate;
import MachineLearning.DataReader.Separator;
import MachineLearning.Distances.Distance;
import MachineLearning.Distances.EuclideanDistance;
import MachineLearning.Distances.ManhattanDistance;
import MachineLearning.RecommendationSystem.RecSys;
import Vista.InformaVista;
import com.rec.recsys.FilesPath;

import java.io.*;
import java.util.*;

public class ImplemetacionModelo implements CambioModelo, InterrogaModelo {

    private InformaVista vista;

    //Classes and variables needed
    RecSys recsys;          //Recommendation system
    Algorithm algorithm;     //Algorithm type KNN o KMeans
    Distance distance;      //Distance type será Euclidean o Manhattan
    Table trainData;        //Train dataset
    Table testData;         //Test dataset (different from train dataset)
    List<String> recommendationsList;
    String currentConfig = "";

    final List<String> SONGS_LIST;


    //Kmeans configurations
    final int KMEANS_CLUSTERS = 15;
    final int KMEANS_NITERATIONS = 200;
    final long KMEANS_SEED = 1001;


    public ImplemetacionModelo() {

        //Initialize names list
        try {
            String path = Separator.pathConverter(FilesPath.FILE_SONGS_NAMES);
            SONGS_LIST = readNames(path);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setVista(InformaVista vista) {
        this.vista = vista;
    }

    @Override
    public void generateRecommendation(String targetSong, String desiredAlgorithm, String desiredDistance, int nRecommendations) {

        //System.out.println("Modelo: Solicitud del controlador recibida, generando recomendación ....");

        String newConfig = desiredAlgorithm + desiredDistance;

        ReaderTemplate readerTemplate;
        //Set distance type
        if (desiredDistance.equals("euclidean")) {
            distance = new EuclideanDistance();
        } else if (desiredDistance.equals("manhattan")) {
            distance = new ManhattanDistance();
        }

        //Set algorithm type
        if (desiredAlgorithm.equals("knn")) {
            algorithm = new KNN(distance);
            readerTemplate = new CSVLabeledFileReader(Separator.pathConverter(FilesPath.KNN_TRAIN)); //train file
            trainData = readerTemplate.readTableFromSource();
            readerTemplate = new CSVLabeledFileReader(Separator.pathConverter(FilesPath.KNN_TEST)); //test file
            testData = readerTemplate.readTableFromSource();

        } else if (desiredAlgorithm.equals("kmeans")) {
            algorithm = new KMeans(KMEANS_CLUSTERS, KMEANS_NITERATIONS, KMEANS_SEED, distance);
            readerTemplate = new CSVUnlabeledFileReader(Separator.pathConverter(FilesPath.KMEANS_TRAIN)); //train file
            trainData = readerTemplate.readTableFromSource();
            readerTemplate = new CSVUnlabeledFileReader(Separator.pathConverter(FilesPath.KMEANS_TEST)); //test file
            testData = readerTemplate.readTableFromSource();
        }

        //Initialice or configure recsys
        if (recsys == null) recsys = new RecSys(algorithm);
        else {
            recsys.setAlgorithm(algorithm);
        }

        //Train data only if selected algorithm and distance is different from current config
        if (!currentConfig.equals(newConfig)) {
            currentConfig = newConfig;
            try {
                recsys.train(trainData);
                recsys.run(testData, SONGS_LIST);
            } catch (InvalidClusterSizeException e) {
                throw new RuntimeException(e);
            } catch (EmptyPointsListException e) {
                throw new RuntimeException(e);
            }
        }

        //Returns recommendations
        recommendationsList = recsys.recommend(targetSong, nRecommendations);
        vista.updateRecomendations();

    }

    @Override
    public List<String> getRecommendationsList() {
        //System.out.println("Modelo: la vista me ha solicitado los datos para actualizar");
        return recommendationsList;
    }


    private List<String> readNames(String fileOfItemNames) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileOfItemNames));
        String line;
        List<String> names = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            names.add(line);
        }
        br.close();
        return names;
    }


}
