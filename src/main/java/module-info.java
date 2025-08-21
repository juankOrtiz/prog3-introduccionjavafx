module com.juankortiz.introduccionjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.juankortiz.introduccionjavafx to javafx.fxml;
    exports com.juankortiz.introduccionjavafx;

    opens com.juankortiz.introduccionjavafx.dashboard to javafx.fxml;
    exports com.juankortiz.introduccionjavafx.dashboard;

    opens com.juankortiz.introduccionjavafx.cambioescena to javafx.fxml;
    exports com.juankortiz.introduccionjavafx.cambioescena;

    opens com.juankortiz.introduccionjavafx.estilos to javafx.fxml;
    exports com.juankortiz.introduccionjavafx.estilos;

    opens com.juankortiz.introduccionjavafx.libro to javafx.fxml;
    exports com.juankortiz.introduccionjavafx.libro;
}