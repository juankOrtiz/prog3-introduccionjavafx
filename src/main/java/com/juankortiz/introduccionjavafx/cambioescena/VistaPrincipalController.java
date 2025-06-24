package com.juankortiz.introduccionjavafx.cambioescena;

import javafx.event.ActionEvent;

import java.io.IOException;

public class VistaPrincipalController {
    public void mostrarVistaDetalles(ActionEvent actionEvent) {
        try {
            EscenarioApplication.cargarEscena("vista-detalles.fxml");
        } catch (IOException ex) {
            System.err.println("Error al cargar la vista: " + ex.getMessage());
        }
    }
}
