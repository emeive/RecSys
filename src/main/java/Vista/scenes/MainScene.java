package Vista.scenes;

import Controlador.*;
import MachineLearning.DataReader.Separator;
import Modelo.InterrogaModelo;
import Vista.InformaVista;
import Vista.InterrogaVista;
import com.rec.recsys.FilesPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.layout.*;

public class MainScene extends Scene implements InterrogaVista, InformaVista {

    private Controlador controlador;
    private InterrogaModelo modelo;


    //GUI elements that returns required data
    Label selectedItemLabel;                    //Selected song
    Spinner<Integer> recommendationSpinner;     //Recommendations number
    ToggleGroup algorithmToogle;                //Algorithm selected
    ToggleGroup distanceToggle;                 //Distance selected
    ListView<String> recomendationList;         //Recommendation list

    Button recommendButton;



    public MainScene() {
        super(new SplitPane(), 800, 600);

        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        SplitPane splitPane = (SplitPane) getRoot();
        splitPane.setDividerPositions(0.3);

        ListView<String> listView = new ListView<>();
        try {
            File csvFile = new File(Separator.pathConverter(FilesPath.FILE_SONGS_NAMES));
            Scanner scanner = new Scanner(csvFile);
            while (scanner.hasNextLine()) {
                listView.getItems().add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        VBox rightPane = new VBox(10);
        rightPane.setPadding(new Insets(20));
        rightPane.setAlignment(Pos.CENTER);

        Label songsListLabel = new Label("Songs list");
        songsListLabel.setStyle("-fx-font-weight: bold;");

        VBox leftPane = new VBox(10, songsListLabel, listView);
        leftPane.setPadding(new Insets(20));

        Label selectedSongLabel = new Label("Search for songs similar to...");
        selectedSongLabel.setWrapText(true);
        selectedSongLabel.setMaxWidth(300);

        selectedItemLabel = new Label();
        selectedItemLabel.getStyleClass().add("custom-label2");
        selectedItemLabel.setWrapText(true);
        selectedItemLabel.setMaxWidth(300);
        selectedItemLabel.setStyle("-fx-font-weight: bold;");

        VBox searchColumn1 = new VBox(selectedSongLabel, selectedItemLabel);

        recommendButton = new Button("Recommend me");
        recommendButton.getStyleClass().add("custom-button");
        recommendButton.setDisable(true);
        VBox searchColumn2 = new VBox(recommendButton);

        HBox searchBox = new HBox(20, searchColumn1, searchColumn2);
        searchBox.setAlignment(Pos.CENTER);

        Label algorithmLabel = new Label("Recommend songs based on...");
        algorithmToogle = new ToggleGroup();
        RadioButton knnButton = new RadioButton("song features");
        knnButton.setAccessibleText("knn");
        RadioButton kmeansButton = new RadioButton("guessed genre");
        kmeansButton.setAccessibleText("kmeans");

        knnButton.setToggleGroup(algorithmToogle);
        kmeansButton.setToggleGroup(algorithmToogle);
        knnButton.setSelected(true);

        VBox algorithmBox = new VBox(10, algorithmLabel, knnButton, kmeansButton);

        Label distanceLabel = new Label("I am seeking for...");
        distanceToggle = new ToggleGroup();
        RadioButton euclideanButton = new RadioButton("most similar songs");
        euclideanButton.setAccessibleText("euclidean");
        RadioButton manhattanButton = new RadioButton("familiar yet fresh songs");
        manhattanButton.setAccessibleText("manhattan");

        euclideanButton.setToggleGroup(distanceToggle);
        manhattanButton.setToggleGroup(distanceToggle);
        euclideanButton.setSelected(true);

        VBox distanceBox = new VBox(10, distanceLabel, euclideanButton, manhattanButton);

        HBox settingsBox = new HBox(20, algorithmBox, distanceBox);
        settingsBox.setAlignment(Pos.CENTER);

        Label recommendationsLabel = new Label("Recomendaciones");
        recommendationSpinner = new Spinner<>(1, 100, 10);

        recomendationList = new ListView<>();


        VBox recommendationsBox = new VBox(10, recommendationsLabel, recommendationSpinner, recomendationList);
        recommendationsBox.setAlignment(Pos.CENTER);

        rightPane.getChildren().addAll(searchBox, settingsBox, recommendationsBox);

        splitPane.getItems().addAll(leftPane, rightPane);


        //Listeners

        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue != null) {
                selectedItemLabel.setText(newValue.toString());
                recommendButton.setDisable(false);
            }
        });

        recommendButton.setOnAction(event -> {
            newRecommendation();
        });

        listView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                newRecommendation();
            }
        });

        recommendationSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                if (!selectedItemLabel.getText().equals(""))
                    newRecommendation();
            }
        });
    }

    //Create scene
    public static MainScene createMainScene(Controlador controlador, InterrogaModelo modelo) {
        MainScene mainScene = new MainScene();
        mainScene.setControlador(controlador);
        mainScene.setModelo(modelo);
        return new MainScene();
    }

    //Set modelo y controlador


    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void setModelo(InterrogaModelo modelo) {
        this.modelo = modelo;
    }

    public void newRecommendation(){
        //System.out.println("Vista: Solicitando recomendacion al controlador ...");
        controlador.newRecommendation();
    }

    //InterrogaVista métodos
    @Override
    public void updateRecomendations() {
        //System.out.println("Main scene: Soy la vista y he recibido la solicitud de actualizar la lista de recomendaciones...");
        //Clear current data
        recomendationList.getItems().clear();
        // Add all elements from your list
        recomendationList.getItems().addAll(modelo.getRecommendationsList());

    }


    //Informa vista métodos
    @Override
    public String getSong() {
        return selectedItemLabel.getText();
    }

    @Override
    public String getAlgorithm() {
        return ((RadioButton) algorithmToogle.getSelectedToggle()).getAccessibleText();
    }

    @Override
    public String getDistance() {
        return ((RadioButton) distanceToggle.getSelectedToggle()).getAccessibleText();
    }
    @Override
    public int getnRecommendations() {
        return recommendationSpinner.getValue();
    }
}
