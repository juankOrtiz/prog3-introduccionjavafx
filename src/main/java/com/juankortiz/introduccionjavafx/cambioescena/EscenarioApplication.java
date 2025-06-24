package com.juankortiz.introduccionjavafx.cambioescena;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EscenarioApplication extends Application {

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        primaryStage.setTitle("Escenario con multiples escenas");
        cargarEscena("vista-principal.fxml");
        primaryStage.show();
    }

    public static void cargarEscena(String nombreArchivo) throws IOException {
        FXMLLoader loader = new FXMLLoader(EscenarioApplication.class.getResource(nombreArchivo));
        Scene escena = new Scene(loader.load());
        primaryStage.setScene(escena);
    }
}
