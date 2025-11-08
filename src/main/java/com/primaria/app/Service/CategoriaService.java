package com.primaria.app.Service;

import com.primaria.app.DTO.CategoriaDTO;
import com.primaria.app.Model.Categoria;
import com.primaria.app.repository.CategoriaRepository;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    // 🔹 Listar todas las categorías
    public List<CategoriaDTO> listarTodas() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Obtener una categoría por ID
    public Optional<CategoriaDTO> obtenerPorId(String id) {
        return categoriaRepository.findById(id)
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class));
    }

    // 🔹 Crear una nueva categoría (sin duplicar nombre)
    public CategoriaDTO crear(CategoriaDTO dto) {
        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = modelMapper.map(dto, Categoria.class);
        Categoria guardada = categoriaRepository.save(categoria);
        return modelMapper.map(guardada, CategoriaDTO.class);
    }

    // 🔹 Actualizar categoría existente
    public CategoriaDTO actualizar(String id, CategoriaDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Validar duplicado si cambia el nombre
        if (!categoria.getNombre().equalsIgnoreCase(dto.getNombre()) &&
            categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        categoria.setNombre(dto.getNombre());
        Categoria actualizada = categoriaRepository.save(categoria);
        return modelMapper.map(actualizada, CategoriaDTO.class);
    }

    // 🔹 Eliminar categoría
    public void eliminar(String id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }
}
