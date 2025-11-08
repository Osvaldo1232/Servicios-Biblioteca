package com.primaria.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.primaria.app.Model.Categoria;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, String>{
	  Optional<Categoria> findByNombreIgnoreCase(String nombre);

	    boolean existsByNombreIgnoreCase(String nombre);
}
