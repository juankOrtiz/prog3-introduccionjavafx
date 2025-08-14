package com.juankortiz.introduccionjavafx.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Recursos {
    // Ruta del directirio de recursos desde el proyecto
    private final static String RUTA_RECURSO = "src/main";

    public static URL leerUrlRecursos(String rutaRecursos) {
        var ruta = (RUTA_RECURSO + "/resources/" + rutaRecursos).replace("/", File.separator);
        try {
            return new File(ruta).getCanonicalFile().toURI().toURL();
        } catch(IOException e) {
            System.err.println("No se puede obtener URL para " + rutaRecursos);
            System.err.println("Verifique el contenido de RUTA_RECURSO");
            return null;
        }
    }

    public static String obtenerRecurso(String rutaRecursos) {
        return leerUrlRecursos(rutaRecursos).toString();
    }
}
