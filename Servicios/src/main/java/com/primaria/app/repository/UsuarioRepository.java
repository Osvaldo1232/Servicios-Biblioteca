package com.primaria.app.repository;

import com.primaria.app.Model.Usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Puedes agregar consultas personalizadas si es necesario
	Optional<Usuario> findByEmail(String email);
}