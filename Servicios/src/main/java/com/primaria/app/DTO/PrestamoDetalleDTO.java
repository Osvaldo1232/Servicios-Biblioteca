package com.primaria.app.DTO;

import com.primaria.app.Model.EstatusPrestamo;
import java.time.LocalDate;

public class PrestamoDetalleDTO {
    private String id;

    // Alumno
    private String alumnoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String matricula;

    // Carrera
    private String carreraNombre;

    // Libro
    private String libroTitulo;

    private Integer cantidad;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private EstatusPrestamo estatus;

    public PrestamoDetalleDTO() {}

    public PrestamoDetalleDTO(String id,
                              String alumnoNombre,
                              String apellidoPaterno,
                              String apellidoMaterno,
                              String matricula,
                              String carreraNombre,
                              String libroTitulo,
                              Integer cantidad,
                              LocalDate fechaPrestamo,
                              LocalDate fechaDevolucion,
                              EstatusPrestamo estatus) {
        this.id = id;
        this.alumnoNombre = alumnoNombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.matricula = matricula;
        this.carreraNombre = carreraNombre;
        this.libroTitulo = libroTitulo;
        this.cantidad = cantidad;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estatus = estatus;
    }

    // Getters y Setters (omito aquí por brevedad; en tu código añádelos todos)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAlumnoNombre() { return alumnoNombre; }
    public void setAlumnoNombre(String alumnoNombre) { this.alumnoNombre = alumnoNombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCarreraNombre() { return carreraNombre; }
    public void setCarreraNombre(String carreraNombre) { this.carreraNombre = carreraNombre; }

    public String getLibroTitulo() { return libroTitulo; }
    public void setLibroTitulo(String libroTitulo) { this.libroTitulo = libroTitulo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public EstatusPrestamo getEstatus() { return estatus; }
    public void setEstatus(EstatusPrestamo estatus) { this.estatus = estatus; }
}
