package com.primaria.app.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.primaria.app.DTO.LibrosMasPrestadosDTO;
import com.primaria.app.DTO.PrestamosResumenDTO;
import com.primaria.app.repository.PrestamoRepository;

@Service
public class PrestamoServices {
	
	private final PrestamoRepository prestamoRepository;

    public PrestamoServices(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public List<PrestamosResumenDTO> obtenerTop10FechasConMasPrestamos() {
        return prestamoRepository.obtenerTop10FechasConMasPrestamos()
                .stream()
                .limit(10)
                .toList();
    }
    public List<LibrosMasPrestadosDTO> obtenerTop10LibrosMasPrestados() {
        return prestamoRepository
                .obtenerTop10LibrosMasPrestados()
                .stream()
                .limit(10)
                .toList();
    }

}
