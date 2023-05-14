package Vista.scenes;

import Controlador.Controlador;
import Modelo.InterrogaModelo;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class IniScene extends Scene {

    public IniScene(Stage primaryStage, MainScene mainScene) {
        super(new StackPane(), 600, 300);

        //aplico estilo a escena
        this.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Cargar el archivo CSS

        //image

        Image logo = new Image(getClass().getResource("/images/logo.png").toExternalForm());

        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        ImageView logoView = new ImageView(logo);


        //texto
        Label welcomeText = new Label("SpotyFake");
        welcomeText.getStyleClass().add("custom-label");

        //boton
        Button startButton = new Button("Start");
        startButton.getStyleClass().add("custom-button");

        //añado elementos al contenedor
        container.getChildren().addAll(logoView, welcomeText, startButton);

        //------------------

        //añado contenedor al stackpane
        StackPane stackPane = (StackPane) this.getRoot();
        stackPane.getChildren().add(container);



        //---------------------

        primaryStage.getIcons().add(logo);

        //que pasa si pulso el boton de inicio:
        startButton.setOnAction(event -> {

            //simbolo de carga
            ProgressIndicator loadingIndicator = new ProgressIndicator();
            stackPane.getChildren().add(loadingIndicator);

            //una pausa para parecer cool
            PauseTransition pause = new PauseTransition(Duration.seconds(1));

            //cambio de escena
            pause.setOnFinished(e -> {
                primaryStage.setScene(mainScene);
                primaryStage.show();
            });
            pause.play();
        });
    }

    public static IniScene createIniScene(Stage primaryStage, MainScene mainScene) {
        return new IniScene(primaryStage, mainScene);
    }
}