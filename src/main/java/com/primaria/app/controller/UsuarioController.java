package com.primaria.app.controller;

import com.primaria.app.DTO.UsuarioDTO;
import com.primaria.app.DTO.UsuarioInfoDTO;
import com.primaria.app.Model.*;
import com.primaria.app.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones para registrar usuarios de distintos roles")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

     @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico mediante su UUID")
  
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(
            @Parameter(description = "UUID del usuario", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/director")
    @Operation(summary = "Registrar Director")
    public ResponseEntity<?> registrarDirector(@RequestBody UsuarioDTO dto) {
        Usuario director = new Usuario();
        director.setNombre(dto.getNombre());
        director.setApellidoPaterno(dto.getApellidoPaterno());
        director.setApellidoMaterno(dto.getApellidoMaterno());
        director.setEmail(dto.getEmail());
        director.setPassword(dto.getPassword());
        director.setRol(Rol.ADMINISTRADOR);
        usuarioService.save(director);
        return ResponseEntity.ok("Director registrado exitosamente");
    }

    @Operation(
            summary = "Obtener información básica del usuario",
            description = "Devuelve el nombre, nombre completo y rol del usuario basándose en su ID UUID."
    )
    @GetMapping("loguedo/{id}")
    public UsuarioInfoDTO obtenerInfo(
            @Parameter(
                    description = "ID del usuario (UUID)",
                    required = true
            )
            @PathVariable UUID id
    ) {
        return usuarioService.obtenerUsuarioInfo(id);
    }
}
