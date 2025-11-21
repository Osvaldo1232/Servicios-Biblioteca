package com.primaria.app.DTO;

public class UsuarioInfoDTO {
	private String nombre;
    private String nombreCompleto;
    private String rol;

    public UsuarioInfoDTO(String nombre, String nombreCompleto, String rol) {
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getRol() {
        return rol;
    }
}
