package com.primaria.app.repository;

import com.primaria.app.Model.Alumno;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, String> {
    Optional<Alumno> findByMatriculaIgnoreCase(String matricula);
    List<Alumno> findByEstatus(Estatus estatus);
}
