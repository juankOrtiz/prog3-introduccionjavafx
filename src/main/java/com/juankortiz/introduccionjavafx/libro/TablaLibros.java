package com.juankortiz.introduccionjavafx.libro;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class TablaLibros extends Application {

    private final ObservableList<Libro> datosOriginales = FXCollections.observableArrayList(
            new Libro(101, "Harry Potter y la piedra filosofal", "J.K. Rowling", Arrays.asList("Fantasía", "Aventura"), 1997),
            new Libro(102, "Juego de Tronos", "George R.R. Martin", Arrays.asList("Fantasía", "Aventura"), 1996),
            new Libro(103, "It", "Stephen King", Arrays.asList("Horror", "Misterio"), 1986),
            new Libro(104, "Diez negritos", "Agatha Christie", Arrays.asList("Misterio"), 1939),
            new Libro(105, "El resplandor", "Stephen King", Arrays.asList("Horror"), 1977),
            new Libro(106, "El Hobbit", "J.R.R. Tolkien", Arrays.asList("Fantasía", "Aventura"), 1937)
    );

    private FilteredList<Libro> datosFiltrados;
    private TextField campoBusqueda;
    private final List<CheckBox> checkBoxesGeneros = new ArrayList<>();
    private ComboBox<Integer> comboAnioInicio;
    private ComboBox<Integer> comboAnioFin;
    private List<Integer> aniosDisponibles;

    @Override
    public void start(Stage primaryStage) {
        // 1. Crear la tabla y las columnas
        TableView<Libro> tablaLibros = new TableView<>();

        // crear las 5 columnas: id, titulo, autor, generos, año publicacion
        TableColumn<Libro, Integer> idColumna = new TableColumn<>("ID");
        idColumna.setMinWidth(50);
        idColumna.setCellValueFactory(new PropertyValueFactory<>("idLibro"));

        TableColumn<Libro, String> tituloColumna = new TableColumn<>("Titulo");
        tituloColumna.setMinWidth(200);
        tituloColumna.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Libro, String> autorColumna = new TableColumn<>("Autor");
        autorColumna.setMinWidth(200);
        autorColumna.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn<Libro, List<String>> generosColumna = new TableColumn<>("Generos");
        generosColumna.setMinWidth(200);
        generosColumna.setCellValueFactory(new PropertyValueFactory<>("generos"));
        generosColumna.setCellFactory(column -> new TableCell<Libro, List<String>>() {
            @Override
            protected void updateItem(List<String> generos, boolean empty) {
                super.updateItem(generos, empty);
                if (empty || generos == null) {
                    setText(null);
                } else {
                    String generosStr = String.join(", ", generos);
                    setText(generosStr);
                }
            }
        });

        TableColumn<Libro, String> anioColumna = new TableColumn<>("Anio");
        anioColumna.setMinWidth(200);
        anioColumna.setCellValueFactory(new PropertyValueFactory<>("anioPublicacion"));

        // agregar columnas a la tabla
        tablaLibros.getColumns().addAll(idColumna, tituloColumna, autorColumna, generosColumna, anioColumna);

        // 2. Personalizar el mensaje de la tabla vacía
        tablaLibros.setPlaceholder(new Label("No existen libros en la vista"));

        // 3. Añadir el evento de doble clic
        tablaLibros.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2 && !tablaLibros.getSelectionModel().isEmpty()) {
                Libro libroSeleccionado = tablaLibros.getSelectionModel().getSelectedItem();
                mostrarDetallesLibro(libroSeleccionado, primaryStage);
            }
        });

        // 4. Inicializar la lista filtrada y vincularla a la tabla
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tablaLibros.setItems(datosFiltrados);

        // 5. Controles de búsqueda
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por título o autor...");
        Button botonBuscar = new Button("Buscar");
        botonBuscar.setOnAction(e -> actualizarFiltros());
        campoBusqueda.setOnAction(e -> actualizarFiltros());

        // 6. Filtros de género
        HBox filtrosGenero = new HBox(10);
        List<String> generosUnicos = Arrays.asList("Fantasía", "Misterio", "Aventura", "Horror");
        for (String genero : generosUnicos) {
            // crear un nuevo checkbox por cada genero existente
            CheckBox checkBox = new CheckBox(genero);
            checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> actualizarFiltros());
            checkBoxesGeneros.add(checkBox);
            filtrosGenero.getChildren().add(checkBox);
        }

        // 7. Filtros de año de publicación
        HBox filtrosAnio = new HBox(10);

        // Obtener los años únicos y ordenados de la lista de libros
        Set<Integer> conjuntoAnios = datosOriginales.stream()
                .map(Libro::getAnioPublicacion)
                .collect(Collectors.toCollection(TreeSet::new));
        aniosDisponibles = new ArrayList<>(conjuntoAnios);

        comboAnioInicio = new ComboBox<>();
        comboAnioInicio.getItems().add(null);
        comboAnioInicio.getItems().addAll(aniosDisponibles);
        comboAnioInicio.setPromptText("Año inicio");

        comboAnioFin = new ComboBox<>();
        comboAnioFin.getItems().add(null);
        comboAnioFin.getItems().addAll(aniosDisponibles);
        comboAnioFin.setPromptText("Año fin");

        Button botonFiltrarAnio = new Button("Filtrar por Año");
        botonFiltrarAnio.setOnAction(e -> actualizarFiltros());

        filtrosAnio.getChildren().addAll(new Label("Rango de Años:"), comboAnioInicio, comboAnioFin, botonFiltrarAnio);

        // Listener para actualizar el combo box de fin cuando cambia el de inicio
        comboAnioInicio.valueProperty().addListener((obs, oldVal, newVal) -> {
            Integer anioFinActual = comboAnioFin.getValue(); // Guardar la selección actual

            // Limpiar y repoblar el ComboBox de fin
            comboAnioFin.getItems().clear();
            comboAnioFin.getItems().add(null);

            if(newVal != null) {
                // Si el anio inicio es un anio puntual, agregar al anio fin solo los anios mayores o iguales al de inicio
                for(Integer anio: aniosDisponibles) {
                    if(anio >= newVal) {
                        comboAnioFin.getItems().add(anio);
                    }
                }
                // Si el anio de fin actual es anterior al nuevo anio de inicio, lo reseteamos
                if(anioFinActual != null && anioFinActual < newVal) {
                    comboAnioFin.setValue(null);
                }
            } else {
                // Si el anio inicio es null, agregamos nuevamente todos los anios
                comboAnioFin.getItems().addAll(aniosDisponibles);
            }
        });

        // 8. Botón de reseteo
        Button botonReset = new Button("Resetear Filtros");
        botonReset.setOnAction(e -> resetearFiltros());

        // 9. Crear un contenedor VBox para todos los controles de filtro
        VBox contenedorFiltros = new VBox(10);
        contenedorFiltros.setPadding(new Insets(10));
        contenedorFiltros.getChildren().addAll(
                new HBox(10, new Label("Búsqueda:"), campoBusqueda, botonBuscar),
                new Label("Géneros:"), filtrosGenero,
                filtrosAnio, botonReset
        );

        // 10. Crear el TitledPane y establecer el VBox como su contenido
        TitledPane titledPaneFiltros = new TitledPane("Filtros", contenedorFiltros);
        // TODO: configurar TitledPane

        // 11. Configurar el layout principal
        VBox root = new VBox(10, titledPaneFiltros, tablaLibros);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 750, 450);

        primaryStage.setTitle("Ejemplo de TableView con Filtros");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void actualizarFiltros() {
        // Lógica de filtro por género
        List<String> generosSeleccionados = checkBoxesGeneros.stream()
                .filter(CheckBox::isSelected)
                .map(CheckBox::getText)
                .toList();

        // Lógica de filtro por año
        Integer anioInicio = comboAnioInicio.getValue();
        Integer anioFin = comboAnioFin.getValue();

        datosFiltrados.setPredicate(libro -> {
            // Predicado 1: Búsqueda difusa por título o autor
            String textoBusqueda = campoBusqueda.getText().toLowerCase();
            boolean coincideBusqueda = textoBusqueda.isEmpty() || libro.getTitulo().toLowerCase().contains(textoBusqueda) || libro.getAutor().toLowerCase().contains(textoBusqueda);

            // Predicado 2: Filtrado por género (lógica "AND")
            boolean coincideGenero = generosSeleccionados.isEmpty() || libro.getGeneros().containsAll(generosSeleccionados);

            // Predicado 3: Filtrado por rango de años
            boolean coincideAnio = true;
            if(anioInicio != null && anioFin != null) {
                coincideAnio = libro.getAnioPublicacion() >= anioInicio && libro.getAnioPublicacion() <= anioFin;
            } else if(anioInicio != null) {
                coincideAnio = libro.getAnioPublicacion() >= anioInicio;
            } else if(anioFin != null) {
                coincideAnio = libro.getAnioPublicacion() <= anioFin;
            }

            return coincideBusqueda && coincideGenero && coincideAnio;
        });
    }

    /**
     * Resetea todos los filtros de la interfaz a su estado inicial.
     */
    private void resetearFiltros() {
        // 1. Borrar el texto del campo de búsqueda
        campoBusqueda.clear();

        // 2. Deseleccionar todos los checkboxes de género'
        for(CheckBox cb : checkBoxesGeneros) {
            cb.setSelected(false);
        }

        // 3. Limpiar la selección de los combo boxes de años
        comboAnioInicio.setValue(null);
        comboAnioFin.setValue(null);
        comboAnioFin.setPromptText("Año fin");

        // 4. Volver a aplicar los filtros para mostrar todos los libros
        actualizarFiltros();
    }

    private void mostrarDetallesLibro(Libro libro, Stage ownerStage) {
        Stage ventanaModal = new Stage();
        ventanaModal.initModality(Modality.APPLICATION_MODAL);
        ventanaModal.initOwner(ownerStage);
        ventanaModal.setTitle("Detalles del Libro");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        layout.setAlignment(Pos.CENTER_LEFT);

        // Crear etiquetas para cada dato del libro seleccionado
        Label lbId = new Label("ID: " + libro.getIdLibro());
        Label lbTitulo = new Label("Titulo: " + libro.getTitulo());
        Label lbAutor = new Label("Autor: " + libro.getAutor());
        Label lbGeneros = new Label("Generos: " + String.join(", ", libro.getGeneros()));
        Label lbAnioPublicacion = new Label("Anio Publicacion: " + libro.getAnioPublicacion());

        Button btnCerrar = new Button("Cerrar");
        btnCerrar.setOnAction(e -> ventanaModal.close());

        // agregar elementos al layout de la ventana
        layout.getChildren().addAll(lbId, lbTitulo, lbAutor, lbGeneros, lbAnioPublicacion, btnCerrar);

        Scene scene = new Scene(layout, 350, 250);
        ventanaModal.setScene(scene);
        ventanaModal.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}