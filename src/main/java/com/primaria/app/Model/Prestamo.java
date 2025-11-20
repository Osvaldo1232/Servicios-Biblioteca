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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDate fechaPrestamo = LocalDate.now();

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDate fechaDevolucion;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "cantidad_devuelta", nullable = false)
    private Integer cantidadDevuelta = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EstatusPrestamo estatus = EstatusPrestamo.PRESTADO;

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
    // Métodos de negocio
    // ===========================

    /**
     * Verifica si el préstamo ya debe ser marcado como vencido.
     */
    public void verificarVencimiento() {
        if (this.estatus != EstatusPrestamo.DEVUELTO &&
            this.estatus != EstatusPrestamo.PARCIALMENTE_DEVUELTO &&
            this.fechaDevolucion.isBefore(LocalDate.now())) {

            this.estatus = EstatusPrestamo.VENCIDO;
        }
    }

    public boolean estaDevueltoCompletamente() {
        return this.cantidadDevuelta >= this.cantidad;
    }

    /**
     * Actualiza el estatus basado en devoluciones.
     */
    public void actualizarEstatusPorDevolucion() {
        if (estaDevueltoCompletamente()) {
            this.estatus = EstatusPrestamo.DEVUELTO;
        } else if (this.cantidadDevuelta > 0 && this.cantidadDevuelta < this.cantidad) {
            this.estatus = EstatusPrestamo.PARCIALMENTE_DEVUELTO;
        }
    }
}
