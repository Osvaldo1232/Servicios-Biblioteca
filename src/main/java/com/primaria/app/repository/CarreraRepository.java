package com.primaria.app.repository;

import com.primaria.app.Model.Carrera;
import com.primaria.app.Model.Estatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, String> {
    Optional<Carrera> findByNombreIgnoreCase(String nombre);
    List<Carrera> findByEstatus(Estatus estatus);
}
