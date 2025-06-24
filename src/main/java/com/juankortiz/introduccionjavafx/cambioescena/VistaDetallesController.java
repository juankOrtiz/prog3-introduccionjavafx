package com.juankortiz.introduccionjavafx.cambioescena;

import javafx.event.ActionEvent;

import java.io.IOException;

public class VistaDetallesController {
    public void mostrarVistaPrincipal(ActionEvent actionEvent) {
        try {
            EscenarioApplication.cargarEscena("vista-principal.fxml");
        } catch (IOException ex) {
            System.err.println("Error al cargar la vista: " + ex.getMessage());
        }
    }
}
