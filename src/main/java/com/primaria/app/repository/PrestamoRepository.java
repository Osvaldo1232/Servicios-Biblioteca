package com.primaria.app.repository;

import com.primaria.app.DTO.LibrosMasPrestadosDTO;
import com.primaria.app.DTO.PrestamosResumenDTO;
import com.primaria.app.Model.Prestamo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, String> {
	@Query("""
		    SELECT new com.primaria.app.DTO.PrestamosResumenDTO(
		        p.fechaPrestamo,
		        SUM(p.cantidad)
		    )
		    FROM Prestamo p
		    GROUP BY p.libro.titulo, p.fechaPrestamo
		    ORDER BY SUM(p.cantidad) DESC
		""")
		List<PrestamosResumenDTO> obtenerTop10FechasConMasPrestamos();
	
	
	 @Query("""
    SELECT new com.primaria.app.DTO.LibrosMasPrestadosDTO(
        p.libro.titulo, 
        SUM(p.cantidad)
    )
    FROM Prestamo p
    GROUP BY p.libro.titulo
    ORDER BY SUM(p.cantidad) DESC
    """)
List<LibrosMasPrestadosDTO> obtenerTop10LibrosMasPrestados();

}
