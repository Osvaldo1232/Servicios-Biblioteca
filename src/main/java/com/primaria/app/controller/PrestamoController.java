package com.primaria.app.controller;

import com.primaria.app.DTO.LibrosMasPrestadosDTO;
import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.DTO.PrestamosResumenDTO;
import com.primaria.app.Model.EstatusPrestamo;
import com.primaria.app.Service.PrestamoService;
import com.primaria.app.Service.PrestamoServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("prestamos")
@CrossOrigin(origins = "*")
@Tag(name = "Gestión de Préstamos", description = "Operaciones para registrar, devolver y consultar préstamos de libros")
public class PrestamoController {

    private final PrestamoService prestamoService;
    private final PrestamoServices prestamoServices;

    public PrestamoController(PrestamoService prestamoService, PrestamoServices prestamoServices) {
        this.prestamoService = prestamoService;
        this.prestamoServices = prestamoServices;
    }

    // ================== CREAR PRÉSTAMO ===================
    @Operation(
            summary = "Registrar un nuevo préstamo",
            description = "Crea un préstamo para un alumno, asignando un libro y reduciendo las copias disponibles."
    )
    @PostMapping
    public PrestamoDTO crearPrestamo(@RequestBody PrestamoDTO dto) {
        return prestamoService.crearPrestamo(dto);
    }

    // ================== DEVOLVER PRÉSTAMO ===================
    @Operation(
            summary = "Registrar devolución parcial o total",
            description = "Permite devolver total o parcialmente los libros de un préstamo y actualiza el stock."
    )
    @PutMapping("/{id}/devolver")
    public PrestamoDTO devolverPrestamo(
            @PathVariable String id,
            @Parameter(description = "Cantidad de libros devueltos (puede ser menor a la cantidad prestada)")
            @RequestParam(required = false, defaultValue = "0") Integer cantidadDevuelta
    ) {
        return prestamoService.devolverPrestamo(id, cantidadDevuelta);
    }

    // ================== LISTAR TODOS LOS DETALLES ===================
    @Operation(
            summary = "Listar todos los préstamos",
            description = "Muestra la lista completa de préstamos con nombre del alumno, matrícula, carrera, libro, fechas y estatus."
    )
    @GetMapping("/detalles")
    public List<PrestamoDetalleDTO> listarPrestamos() {
        return prestamoService.obtenerDetalles();
    }

    // ================== FILTRAR PRÉSTAMOS ===================
    @Operation(
            summary = "Buscar préstamos por filtros opcionales",
            description = """
                    Permite buscar préstamos por:
                    - Fecha de préstamo
                    - Nombre del alumno
                    - Título del libro
                    - Estatus del préstamo
                    
                    Si no se especifica ningún filtro, se devuelven todos los préstamos.
                    """
    )
    @GetMapping("/buscar")
    public List<PrestamoDetalleDTO> buscarPrestamos(
            @Parameter(description = "Fecha del préstamo (formato: yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPrestamo,

            @Parameter(description = "Nombre o parte del nombre del alumno")
            @RequestParam(required = false) String alumnoNombre,

            @Parameter(description = "Título o parte del título del libro")
            @RequestParam(required = false) String libroTitulo,

            @Parameter(description = "Estatus del préstamo (PRESTADO, DEVUELTO, VENCIDO, SIN_ENTREGAR)")
            @RequestParam(required = false) EstatusPrestamo estatus
    ) {
        return prestamoService.filtrarPrestamos(fechaPrestamo, alumnoNombre, libroTitulo, estatus);
    }
    
    
    @Operation(
            summary = "Obtener top 10 fechas con más préstamos",
            description = "Devuelve las 10 fechas en las que se registraron más préstamos, acompañadas del total de préstamos por fecha."
        )
        
        @GetMapping("/top10-fechas")
        public List<PrestamosResumenDTO> obtenerTop10FechasConMasPrestamos() {
            return prestamoServices.obtenerTop10FechasConMasPrestamos();
        }
    
    @Operation(
            summary = "Obtener los 10 libros más prestados",
            description = "Devuelve una lista con los 10 libros que han sido prestados con mayor frecuencia, incluyendo el nombre del libro y el total de préstamos."
    )
   
    @GetMapping("/top10-libros")
    public List<LibrosMasPrestadosDTO> obtenerTop10Libros() {
        return prestamoServices.obtenerTop10LibrosMasPrestados();
    }

}
