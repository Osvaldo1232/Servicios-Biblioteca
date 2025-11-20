package com.primaria.app.controller;

import com.primaria.app.DTO.AutorDTO;
import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.Service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("autores")
@Tag(name = "Autores", description = "Gestión de autores (tutores) del sistema")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @Operation(summary = "Agregar un nuevo autor")
    @PostMapping
    public ResponseEntity<AutorDTO> agregarAutor(@RequestBody AutorDTO autorDTO) {
        return ResponseEntity.ok(autorService.agregarAutor(autorDTO));
    }

    @Operation(summary = "Listar todos los autores")
    @GetMapping
    public ResponseEntity<List<AutorDTO>> listarAutores() {
        return ResponseEntity.ok(autorService.listarTodos());
    }
    
    @Operation(summary = "Editar un autor existente por ID")
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> editarAutor(
            @PathVariable String id,
            @RequestBody AutorDTO autorDTO) {
        return ResponseEntity.ok(autorService.editarAutor(id, autorDTO));
    }

}
