package com.primaria.app.Service;

import com.primaria.app.DTO.AutorDTO;
import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.Model.Autores;
import com.primaria.app.Model.Estatus;
import com.primaria.app.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    // Crear nuevo autor
    public AutorDTO agregarAutor(AutorDTO dto) {
        Autores autor = new Autores();
        autor.setNombre(dto.getNombre());
        autor.setApellidoPaterno(dto.getApellidoPaterno());
        autor.setApellidoMaterno(dto.getApellidoMaterno());
        autor.setNacionalidad(dto.getNacionalidad());

        autor = autorRepository.save(autor);

        return convertirADTO(autor);
    }

    // Listar todos los autores
    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Editar autor existente
    public AutorDTO editarAutor(String id, AutorDTO dto) {
        Optional<Autores> optionalAutor = autorRepository.findById(id);

        if (optionalAutor.isEmpty()) {
            throw new RuntimeException("Autor no encontrado con ID: " + id);
        }

        Autores autor = optionalAutor.get();
        autor.setNombre(dto.getNombre());
        autor.setApellidoPaterno(dto.getApellidoPaterno());
        autor.setApellidoMaterno(dto.getApellidoMaterno());
        autor.setNacionalidad(dto.getNacionalidad());

        autor = autorRepository.save(autor);

        return convertirADTO(autor);
    }

    // Conversión de entidad a DTO
    private AutorDTO convertirADTO(Autores autor) {
        return new AutorDTO(
                autor.getId(),
                autor.getNombre(),
                autor.getApellidoPaterno(),
                autor.getApellidoMaterno(),
                autor.getNacionalidad()
        );
    }
}
