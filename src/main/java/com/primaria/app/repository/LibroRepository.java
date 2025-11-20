package com.primaria.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Libro;

@Repository
public interface LibroRepository   extends JpaRepository<Libro, String>{
	 List<Libro> findByEstatus(Estatus estatus);
}
