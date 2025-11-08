package com.primaria.app.DTO;

import java.util.List;

public class LibroDTO {

    private String id;
    private String titulo;
    private Integer anioPublicacion;
    private String editorial;
    private Integer copiasDisponibles;
    private String categoriaId;
    private String estatus;

    // 🔹 Lista de IDs de autores asociados al libro
    private List<String> autoresIds;

    public LibroDTO() {
    }

    public LibroDTO(String id, String titulo, Integer anioPublicacion, String editorial,
                    Integer copiasDisponibles, String categoriaId, String estatus,
                    List<String> autoresIds) {
        this.id = id;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;
        this.copiasDisponibles = copiasDisponibles;
        this.categoriaId = categoriaId;
        this.estatus = estatus;
        this.autoresIds = autoresIds;
    }

    // 🔹 Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Integer getCopiasDisponibles() {
        return copiasDisponibles;
    }

    public void setCopiasDisponibles(Integer copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public List<String> getAutoresIds() {
        return autoresIds;
    }

    public void setAutoresIds(List<String> autoresIds) {
        this.autoresIds = autoresIds;
    }
}
