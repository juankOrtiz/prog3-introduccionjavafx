package com.juankortiz.introduccionjavafx.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Cargar el archivo FXML del dashboard
        FXMLLoader loader = new FXMLLoader(DashboardApplication.class.getResource("vista-dashboard.fxml"));
        // Crear una escena con el contenido del archivo
        Scene escena = new Scene(loader.load(), 900, 600);
        // Configurar el titulo de la ventana
        stage.setTitle("Dashboard");
        // Establecer la escena dentro de la ventana (Stage)
        stage.setScene(escena);
        // Mostrar la ventana
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
