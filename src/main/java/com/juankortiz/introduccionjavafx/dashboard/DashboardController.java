package com.juankortiz.introduccionjavafx.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {
    @FXML
    private Label labContenido;

    public void clickInicio(ActionEvent actionEvent) {
        labContenido.setText("Estas en la pagina inicio");
    }

    public void clickVentas(ActionEvent actionEvent) {
        labContenido.setText("Estas en la pagina ventas");
    }

    public void clickAjustes(ActionEvent actionEvent) {
        labContenido.setText("Estas en la pagina ajustes");
    }
}
