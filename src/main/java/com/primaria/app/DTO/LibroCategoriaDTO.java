package com.primaria.app.DTO;

import java.util.List;

public class LibroCategoriaDTO {

    private String id;
    private String titulo;
    private List<String> autores; 
    private List<String> autoresIds;  // <-- NUEVO
    private Integer anioPublicacion;
    private String editorial;
    private Integer copiasDisponibles;
    private String categoriaId;
    private String categoriaNombre;
    private String estatus;

    public LibroCategoriaDTO() {}

    public LibroCategoriaDTO(
            String id, String titulo,
            List<String> autores,
            List<String> autoresIds,     // <-- NUEVO
            Integer anioPublicacion,
            String editorial,
            Integer copiasDisponibles,
            String categoriaId,
            String categoriaNombre,
            String estatus) {

        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.autoresIds = autoresIds;   // <-- NUEVO
        this.anioPublicacion = anioPublicacion;
        this.editorial = editorial;
        this.copiasDisponibles = copiasDisponibles;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.estatus = estatus;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public List<String> getAutores() { return autores; }
    public void setAutores(List<String> autores) { this.autores = autores; }

    public List<String> getAutoresIds() { return autoresIds; }
    public void setAutoresIds(List<String> autoresIds) { this.autoresIds = autoresIds; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }

    public Integer getCopiasDisponibles() { return copiasDisponibles; }
    public void setCopiasDisponibles(Integer copiasDisponibles) { this.copiasDisponibles = copiasDisponibles; }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }

    public String getCategoriaNombre() { return categoriaNombre; }
    public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}
