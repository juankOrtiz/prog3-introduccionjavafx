package com.juankortiz.introduccionjavafx.libro;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.List;

public class Libro {
    private final int idLibro;
    private final String titulo;
    private final String autor;
    private final List<String> generos;
    private final int anioPublicacion;

    public Libro(int idLibro, String titulo, String autor, List<String> generos, int anioPublicacion) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.generos = generos;
        this.anioPublicacion = anioPublicacion;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public List<String> getGeneros() {
        return generos;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public boolean tieneGenero(String genero) {
        return generos.contains(genero);
    }
}
