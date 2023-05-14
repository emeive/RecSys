package Modelo;

public interface CambioModelo {

    //esto recibe cancion, alg
    void generateRecommendation(String targetSong, String desiredAlgorithm, String desiredDistance, int nRecommendations);



}
