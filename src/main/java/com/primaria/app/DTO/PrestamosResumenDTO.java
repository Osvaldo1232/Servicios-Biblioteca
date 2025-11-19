package com.primaria.app.DTO;

import java.time.LocalDate;

public class PrestamosResumenDTO {
	 private String libro;
	    private LocalDate fecha;
	    private Long totalPrestamos;

	    public PrestamosResumenDTO(String libro, LocalDate fecha, Long totalPrestamos) {
	        this.libro = libro;
	        this.fecha = fecha;
	        this.totalPrestamos = totalPrestamos;
	    }

	    public String getLibro() { return libro; }
	    public LocalDate getFecha() { return fecha; }
	    public Long getTotalPrestamos() { return totalPrestamos; }
}
