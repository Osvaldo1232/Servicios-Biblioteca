package com.primaria.app.repository;

import com.primaria.app.Model.Autores;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Libro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autores, String> {
	
}
