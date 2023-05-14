package com.rec.recsys;

public interface FilesPath {

    String FILES_PATH = "src/main/java/MachineLearning/Data/";
    String FILE_SONGS_NAMES = FILES_PATH + "songs_test_names.csv";
    String KNN_TRAIN = FILES_PATH  + "songs_train.csv";
    String KNN_TEST = FILES_PATH + "songs_test.csv";
    String KMEANS_TRAIN = FILES_PATH + "songs_train_withoutnames.csv";
    String KMEANS_TEST = FILES_PATH + "songs_test_withoutnames.csv";
}
