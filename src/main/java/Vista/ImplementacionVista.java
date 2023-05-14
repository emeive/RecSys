package Vista;

import Controlador.Controlador;
import Modelo.InterrogaModelo;
import Vista.scenes.IniScene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ImplementacionVista {

    private final Stage stage;
    private Controlador controlador;
    private InterrogaModelo modelo;

    public ImplementacionVista(final Stage stage) {
        this.stage = stage;
    }


    public void setModelo(final InterrogaModelo modelo){ this.modelo = modelo;}

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void creaGUI(Stage primaryStage){
        //primaryStage.setScene(IniScene.createIniScene(stage, controlador, modelo));
        primaryStage.show();
    }



}
