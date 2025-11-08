package com.primaria.app.controller;

import com.primaria.app.DTO.UsuarioDTO;
import com.primaria.app.Model.*;
import com.primaria.app.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
