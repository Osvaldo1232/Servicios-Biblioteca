package com.primaria.app.DTO;


public class LibrosMasPrestadosDTO {

    private String nombreLibro;
    private Long totalPrestamos;

    public LibrosMasPrestadosDTO(String nombreLibro, Long totalPrestamos) {
        this.nombreLibro = nombreLibro;
        this.totalPrestamos = totalPrestamos;
    }

    public String getNombreLibro() { return nombreLibro; }
    public Long getTotalPrestamos() { return totalPrestamos; }
}
