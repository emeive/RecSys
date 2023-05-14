package MachineLearning.RecommendationSystem;

import MachineLearning.Algorithms.Algorithm;
import MachineLearning.Algorithms.EmptyPointsListException;
import MachineLearning.Algorithms.InvalidClusterSizeException;
import MachineLearning.DataProcessing.Table;

import java.nio.channels.spi.AbstractSelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecSys {

    //ATRIBUTES
    private Algorithm algorithm;
    private Map<String, Integer> estimateLabelsMap = new HashMap<>();


    //CONSTRUCTOR
    public RecSys(Algorithm algorithm){
        this.algorithm = algorithm;
    }


    //METHODS
    public void train(Table trainData) throws InvalidClusterSizeException, EmptyPointsListException {
        algorithm.train(trainData);
    }


    public void run(Table testData, List<String> testItemNames){
    //label is estimated
        for (int i = 0; i < testData.getRows().size() ; i++) {
            //save estimated label
            int label = (Integer) algorithm.estimate(testData.getRowAt(i).getData());
            //assign songname and label to the map
            estimateLabelsMap.put(testItemNames.get(i), label);
        }
    }
    public List<String> recommend(String nameLikedItem, int numRecommendations){

        List<String> recomendedSongs = new ArrayList<>();
        Integer label = estimateLabelsMap.get(nameLikedItem);

        //If the song in not found, launch exception
        if (label == null){
            try {
                throw new NoSuchFieldException("The song was not found in the list.");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }

        //We are looking for song names (keys) in the map whose associated values are equal to the found label.
        for (String songName: estimateLabelsMap.keySet()) {
            if (estimateLabelsMap.get(songName).equals(label) && !songName.equals(nameLikedItem)) recomendedSongs.add(songName);
            if (recomendedSongs.size() == numRecommendations) break;
        }

        return recomendedSongs;
    }

    public void setAlgorithm(Algorithm algorithm){
        this.algorithm = algorithm;
    }
}
