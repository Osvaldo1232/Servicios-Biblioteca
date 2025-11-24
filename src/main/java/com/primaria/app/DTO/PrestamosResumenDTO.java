package com.primaria.app.DTO;

import java.time.LocalDate;

public class PrestamosResumenDTO {
	    private LocalDate fecha;
	    private Long totalPrestamos;

	    public PrestamosResumenDTO( LocalDate fecha, Long totalPrestamos) {
	        this.fecha = fecha;
	        this.totalPrestamos = totalPrestamos;
	    }

	    public LocalDate getFecha() { return fecha; }
	    public Long getTotalPrestamos() { return totalPrestamos; }
}
