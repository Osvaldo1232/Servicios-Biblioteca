package com.primaria.app.DTO;

public class LibrosPrestadosPorFechaDTO {
    private String nombreLibro;
    private Integer cantidadPrestada;
    private String fechaPrestamo;

    public LibrosPrestadosPorFechaDTO(String nombreLibro, Integer cantidadPrestada, String fechaPrestamo) {
        this.nombreLibro = nombreLibro;
        this.cantidadPrestada = cantidadPrestada;
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getNombreLibro() { return nombreLibro; }
    public Integer getCantidadPrestada() { return cantidadPrestada; }
    public String getFechaPrestamo() { return fechaPrestamo; }
}
