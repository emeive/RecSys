package Controlador;

import Modelo.CambioModelo;
import Vista.InterrogaVista;

public class ImplementacionControlador implements Controlador {

    private InterrogaVista vista;
    private CambioModelo modelo;
    public ImplementacionControlador() {}

    public void setModelo(CambioModelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(InterrogaVista vista) {
        this.vista = vista;
    }


    //(2) Controlador le pide a la vista los datos necesarios para hacer la recomendacion
    @Override
    public void newRecommendation() {
        //System.out.println("Controlador: recibo solicitud de recomendacion desde la vista y solicita al modelo que la genere...");
        String algorithm = vista.getAlgorithm();
        String distance = vista.getDistance();
        String song = vista.getSong();
        int nRecommendations = vista.getnRecommendations();
        modelo.generateRecommendation(song, algorithm, distance, nRecommendations);
    }













}
