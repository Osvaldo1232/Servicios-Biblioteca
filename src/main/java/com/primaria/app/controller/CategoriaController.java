package com.primaria.app.controller;

import com.primaria.app.DTO.CategoriaDTO;
import com.primaria.app.Service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("categorias")
@Tag(name = "Categorías", description = "Gestión de categorías de libros")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // 🔹 Listar todas las categorías
    @Operation(summary = "Listar todas las categorías", description = "Devuelve una lista con todas las categorías registradas")
   
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarTodas() {
        List<CategoriaDTO> categorias = categoriaService.listarTodas();
        return ResponseEntity.ok(categorias);
    }

    // 🔹 Obtener categoría por ID
    @Operation(summary = "Obtener categoría por ID", description = "Busca una categoría por su identificador único")
   
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable String id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Crear nueva categoría
    @Operation(summary = "Crear nueva categoría", description = "Agrega una nueva categoría, validando que el nombre no se repita")
   
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaDTO dto) {
        try {
            CategoriaDTO nueva = categoriaService.crear(dto);
            return ResponseEntity.status(201).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 Actualizar categoría
    @Operation(summary = "Actualizar categoría", description = "Modifica el nombre de una categoría existente")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable String id, @RequestBody CategoriaDTO dto) {
        try {
            CategoriaDTO actualizada = categoriaService.actualizar(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🔹 Eliminar categoría
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente por su ID")
   
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            categoriaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
