package com.primaria.app.Service.Impl;

import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.Model.*;
import com.primaria.app.repository.*;
import com.primaria.app.Service.PrestamoService;
import com.primaria.app.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final AlumnoRepository alumnoRepository;
    private final LibroRepository libroRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository,
                               AlumnoRepository alumnoRepository,
                               LibroRepository libroRepository) {
        this.prestamoRepository = prestamoRepository;
        this.alumnoRepository = alumnoRepository;
        this.libroRepository = libroRepository;
    }

    // ================== CREAR PRÉSTAMO ===================
    @Transactional
    @Override
    public PrestamoDTO crearPrestamo(PrestamoDTO dto) {
        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        if (libro.getCopiasDisponibles() < dto.getCantidad()) {
            throw new RuntimeException("No hay suficientes copias disponibles");
        }

        // Descontar copias disponibles
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() - dto.getCantidad());
        libroRepository.save(libro);

        // Crear préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setAlumno(alumno);
        prestamo.setLibro(libro);
        prestamo.setCantidad(dto.getCantidad());
        prestamo.setFechaPrestamo(LocalDate.now());
        prestamo.setFechaDevolucion(dto.getFechaDevolucion());
        prestamo.setEstatus(EstatusPrestamo.PRESTADO);

        prestamoRepository.save(prestamo);

        // Respuesta DTO
        PrestamoDTO response = new PrestamoDTO();
        response.setId(prestamo.getId());
        response.setAlumnoId(alumno.getId());
        response.setLibroId(libro.getId());
        response.setCantidad(prestamo.getCantidad());
        response.setCantidadDevuelta(0);
        response.setFechaPrestamo(prestamo.getFechaPrestamo());
        response.setFechaDevolucion(prestamo.getFechaDevolucion());
        response.setEstatus(prestamo.getEstatus());

        return response;
    }

    // ================== DEVOLVER PRÉSTAMO ===================
    @Transactional
    @Override
    public PrestamoDTO devolverPrestamo(String prestamoId, Integer cantidadDevuelta) {

        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ApiException("Préstamo no encontrado", HttpStatus.NOT_FOUND.value()));

        Libro libro = prestamo.getLibro();

        if (cantidadDevuelta == null || cantidadDevuelta <= 0)
            throw new ApiException("La cantidad devuelta debe ser mayor a cero", HttpStatus.BAD_REQUEST.value());

        int cantidadPendiente = prestamo.getCantidad() - prestamo.getCantidadDevuelta();

        if (cantidadDevuelta > cantidadPendiente)
            throw new ApiException("La cantidad devuelta no puede ser mayor a la pendiente", HttpStatus.BAD_REQUEST.value());

        // Actualizar cantidad devuelta
        prestamo.setCantidadDevuelta(prestamo.getCantidadDevuelta() + cantidadDevuelta);

        // Actualizar inventario del libro
        libro.setCopiasDisponibles(libro.getCopiasDisponibles() + cantidadDevuelta);
        libroRepository.save(libro);

        // Actualizar estatus por devolución
        prestamo.actualizarEstatusPorDevolucion();

        // Si se completó la devolución, actualizar fecha real de entrega
        if (prestamo.getEstatus() == EstatusPrestamo.DEVUELTO) {
            prestamo.setFechaDevolucion(LocalDate.now());
        }

        prestamoRepository.save(prestamo);

        PrestamoDTO dto = new PrestamoDTO();
        dto.setId(prestamo.getId());
        dto.setLibroId(libro.getId());
        dto.setAlumnoId(prestamo.getAlumno().getId());
        dto.setCantidad(prestamo.getCantidad());
        dto.setCantidadDevuelta(prestamo.getCantidadDevuelta());
        dto.setFechaPrestamo(prestamo.getFechaPrestamo());
        dto.setFechaDevolucion(prestamo.getFechaDevolucion());
        dto.setEstatus(prestamo.getEstatus());

        return dto;
    }

    // ================== LISTAR DETALLES ===================
    @Override
    public List<PrestamoDetalleDTO> obtenerDetalles() {

        return prestamoRepository.findAll()
                .stream()
                .peek(p -> {
                    // 🔥 AUTOVERIFICAR VENCIMIENTO
                    p.verificarVencimiento();
                    prestamoRepository.save(p);
                })
                .map(p -> {
                    Alumno a = p.getAlumno();

                    return new PrestamoDetalleDTO(
                            p.getId(),
                            a.getNombre(),
                            a.getApellidoPaterno(),
                            a.getApellidoMaterno(),
                            a.getMatricula(),
                            a.getCarrera() != null ? a.getCarrera().getNombre() : null,
                            p.getLibro().getTitulo(),
                            p.getCantidad(),
                            p.getFechaPrestamo(),
                            p.getFechaDevolucion(),
                            p.getEstatus()
                    );
                })
                .collect(Collectors.toList());
    }

    // ================== FILTRO AVANZADO ===================
    @Override
    public List<PrestamoDetalleDTO> filtrarPrestamos(LocalDate fechaPrestamo,
                                                     String alumnoNombre,
                                                     String libroTitulo,
                                                     EstatusPrestamo estatus) {

        return prestamoRepository.findAll()
                .stream()
                .peek(p -> {
                    // 🔥 AUTOVERIFICAR VENCIMIENTO
                    p.verificarVencimiento();
                    prestamoRepository.save(p);
                })
                .filter(p -> fechaPrestamo == null || p.getFechaPrestamo().equals(fechaPrestamo))
                .filter(p -> alumnoNombre == null ||
                        p.getAlumno().getNombre().toLowerCase().contains(alumnoNombre.toLowerCase()))
                .filter(p -> libroTitulo == null ||
                        p.getLibro().getTitulo().toLowerCase().contains(libroTitulo.toLowerCase()))
                .filter(p -> estatus == null || p.getEstatus() == estatus)
                .map(p -> new PrestamoDetalleDTO(
                        p.getId(),
                        p.getAlumno().getNombre(),
                        p.getAlumno().getApellidoPaterno(),
                        p.getAlumno().getApellidoMaterno(),
                        p.getAlumno().getMatricula(),
                        p.getAlumno().getCarrera() != null ? p.getAlumno().getCarrera().getNombre() : null,
                        p.getLibro().getTitulo(),
                        p.getCantidad(),
                        p.getFechaPrestamo(),
                        p.getFechaDevolucion(),
                        p.getEstatus()
                ))
                .collect(Collectors.toList());
    }
}
