package com.rec.recsys;

import Controlador.ImplementacionControlador;
import Modelo.ImplemetacionModelo;
import Vista.ImplementacionVista;
import Vista.InformaVista;
import Vista.InterrogaVista;
import Vista.scenes.IniScene;
import Vista.scenes.MainScene;
import javafx.application.Application;
import javafx.stage.Stage;

//Clase Main
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        ImplementacionControlador controlador = new ImplementacionControlador();
        ImplemetacionModelo modelo = new ImplemetacionModelo();
        MainScene vista = new MainScene();

        modelo.setVista( vista);
        controlador.setVista(vista);
        controlador.setModelo(modelo);
        vista.setModelo(modelo);
        vista.setControlador(controlador);

        primaryStage.setScene(IniScene.createIniScene(primaryStage, vista));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}