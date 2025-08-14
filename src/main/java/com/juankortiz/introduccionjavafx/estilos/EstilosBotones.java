package com.juankortiz.introduccionjavafx.estilos;
import com.juankortiz.introduccionjavafx.util.Recursos;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class EstilosBotones extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Creamos los 3 botones
        Button botonSi = new Button("Si");
        Button botonNo = new Button("No");
        Button botonCancelar = new Button("Cancelar");
        // Creamos el elemento root o raiz
        HBox root = new HBox();
        root.getChildren().addAll(botonSi, botonNo, botonCancelar);
        // Crea la escena que contiene root y todos los elementos
        Scene escena = new Scene(root);
        // Agregar una clase al boton no para distinguirlo del boton por defecto
        botonNo.getStyleClass().add("boton-secundario");
        // Agregar ID al boton cancelar (el ID es unico entre todos los elementos)
        botonCancelar.setId("botonCancelar");
        // Cargamos los estilos para los botones
        String urlCss = Recursos.obtenerRecurso("css/estilosbotones.css");
        escena.getStylesheets().add(urlCss);
        // Configurar Stage
        primaryStage.setScene(escena);
        primaryStage.setTitle("Botones con estilos");
        primaryStage.show();
    }
}
