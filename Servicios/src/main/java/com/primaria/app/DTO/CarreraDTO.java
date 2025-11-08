package com.primaria.app.DTO;

import com.primaria.app.Model.Estatus;

public class CarreraDTO {

    private String id;
    private String nombre;
    private Estatus estatus;

    public CarreraDTO() {
    }

    public CarreraDTO(String id, String nombre, Estatus estatus) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
    }

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

    public Estatus getEstatus() {
        return estatus;
    }

    public void setEstatus(Estatus estatus) {
        this.estatus = estatus;
    }
}
