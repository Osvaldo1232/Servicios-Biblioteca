package com.primaria.app.controller;

import com.primaria.app.DTO.AlumnoDTO;
import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Service.AlumnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("alumnos")
@Tag(name = "Alumnos", description = "Gestión de alumnos con estatus y relación con carreras")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    @Operation(summary = "Crear un nuevo alumno")
    @PostMapping
    public ResponseEntity<AlumnoDTO> crearAlumno(@RequestBody AlumnoDTO dto) {
        return ResponseEntity.ok(alumnoService.crearAlumno(dto));
    }

    @Operation(summary = "Actualizar un alumno existente")
    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> actualizarAlumno(@PathVariable String id, @RequestBody AlumnoDTO dto) {
        return ResponseEntity.ok(alumnoService.actualizarAlumno(id, dto));
    }

    @Operation(summary = "Cambiar el estatus de un alumno (ACTIVO / INACTIVO)")
    @PatchMapping("/{id}/estatus")
    public ResponseEntity<AlumnoDTO> cambiarEstatus(@PathVariable String id, @RequestParam Estatus estatus) {
        return ResponseEntity.ok(alumnoService.cambiarEstatus(id, estatus));
    }

    @Operation(summary = "Listar todos los alumnos")
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> listarAlumnos() {
        return ResponseEntity.ok(alumnoService.listarTodos());
    }
    @Operation(summary = "Alumnos activos", description = "Alumnos activos")
    @GetMapping("/activosAL")
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return alumnoService.obtenerLibrosActivos();
    }
}
