package com.primaria.app.DTO;

public class CategoriaDTO {

    private String id;
    private String nombre;

    // 🔹 Constructores
    public CategoriaDTO() {}

    public CategoriaDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // 🔹 Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
