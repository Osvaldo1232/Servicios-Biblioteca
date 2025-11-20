package com.primaria.app.DTO;

public class LibroActivoDTO {
	private String id;
    private String titulo;

    public LibroActivoDTO(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
}
