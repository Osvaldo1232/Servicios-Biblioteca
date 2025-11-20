package com.primaria.app.controller;

import com.primaria.app.DTO.CarreraDTO;
import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Service.CarreraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carreras")
@Tag(name = "Carreras", description = "Gestión de carreras académicas")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    @Operation(summary = "Crear una nueva carrera")
    @PostMapping
    public ResponseEntity<CarreraDTO> crearCarrera(@RequestBody CarreraDTO dto) {
        return ResponseEntity.ok(carreraService.crearCarrera(dto));
    }

    @Operation(summary = "Actualizar una carrera existente")
    @PutMapping("/{id}")
    public ResponseEntity<CarreraDTO> actualizarCarrera(@PathVariable String id, @RequestBody CarreraDTO dto) {
        return ResponseEntity.ok(carreraService.actualizarCarrera(id, dto));
    }

    @Operation(summary = "Cambiar estatus de una carrera")
    @PatchMapping("/{id}/estatus")
    public ResponseEntity<CarreraDTO> cambiarEstatus(@PathVariable String id, @RequestParam Estatus estatus) {
        return ResponseEntity.ok(carreraService.cambiarEstatus(id, estatus));
    }

    @Operation(summary = "Listar todas las carreras")
    @GetMapping
    public ResponseEntity<List<CarreraDTO>> listarCarreras() {
        return ResponseEntity.ok(carreraService.listarTodas());
    }
    @Operation(summary = "Carreras activos", description = "Carreras activos")
    @GetMapping("/activosC")
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return carreraService.obtenerLibrosActivos();
    }
}
