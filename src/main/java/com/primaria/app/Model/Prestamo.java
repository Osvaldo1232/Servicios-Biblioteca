package com.primaria.app.Model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36, updatable = false, nullable = false)
    private String id;

    // Alumno que realiza el préstamo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    // Libro prestado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo = LocalDate.now();

    // Fecha límite para devolver el libro
    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    // Cantidad total de ejemplares prestados
    @Column(nullable = false)
    private Integer cantidad;

    // Cantidad que el alumno ya devolvió (para permitir devoluciones parciales)
    @Column(name = "cantidad_devuelta", nullable = false)
    private Integer cantidadDevuelta = 0;

    // Estatus actual del préstamo (PRESTADO, DEVUELTO, PARCIALMENTE_DEVUELTO, VENCIDO)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EstatusPrestamo estatus = EstatusPrestamo.PRESTADO;

    // ===========================
    // Constructores
    // ===========================
    public Prestamo() {}

    public Prestamo(Alumno alumno, Libro libro, Integer cantidad, LocalDate fechaDevolucion) {
        this.alumno = alumno;
        this.libro = libro;
        this.cantidad = cantidad;
        this.fechaDevolucion = fechaDevolucion;
        this.fechaPrestamo = LocalDate.now();
        this.estatus = EstatusPrestamo.PRESTADO;
    }

    // ===========================
    // Getters y Setters
    // ===========================
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Integer getCantidadDevuelta() { return cantidadDevuelta; }
    public void setCantidadDevuelta(Integer cantidadDevuelta) { this.cantidadDevuelta = cantidadDevuelta; }

    public EstatusPrestamo getEstatus() { return estatus; }
    public void setEstatus(EstatusPrestamo estatus) { this.estatus = estatus; }

    // ===========================
    // Métodos útiles de negocio
    // ===========================

    /** Marca el préstamo como vencido si ya pasó la fecha de devolución y no ha sido completamente devuelto */
    public void actualizarEstatusVencido() {
        if (this.estatus == EstatusPrestamo.PRESTADO &&
            this.fechaDevolucion.isBefore(LocalDate.now())) {
            this.estatus = EstatusPrestamo.VENCIDO;
        }
    }

    /** Verifica si el préstamo ya fue completamente devuelto */
    public boolean estaDevueltoCompletamente() {
        return this.cantidadDevuelta >= this.cantidad;
    }

    /** Actualiza el estatus según la cantidad devuelta */
    public void actualizarEstatusPorDevolucion() {
        if (estaDevueltoCompletamente()) {
            this.estatus = EstatusPrestamo.DEVUELTO;
        } else if (this.cantidadDevuelta > 0 && this.cantidadDevuelta < this.cantidad) {
            this.estatus = EstatusPrestamo.PARCIALMENTE_DEVUELTO;
        }
    }
}
