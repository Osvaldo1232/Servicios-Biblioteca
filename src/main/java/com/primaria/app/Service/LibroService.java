package com.primaria.app.Service;

import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.DTO.LibroCategoriaDTO;
import com.primaria.app.DTO.LibroDTO;
import com.primaria.app.Model.Autores;
import com.primaria.app.Model.Categoria;
import com.primaria.app.Model.Estatus;
import com.primaria.app.Model.Libro;
import com.primaria.app.repository.AutorRepository;
import com.primaria.app.repository.CategoriaRepository;
import com.primaria.app.repository.LibroRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;
    private final AutorRepository autorRepository;
    private final ModelMapper modelMapper;

    public LibroService(LibroRepository libroRepository,
                        CategoriaRepository categoriaRepository,
                        AutorRepository autorRepository,
                        ModelMapper modelMapper) {
        this.libroRepository = libroRepository;
        this.categoriaRepository = categoriaRepository;
        this.autorRepository = autorRepository;
        this.modelMapper = modelMapper;
    }

  public List<LibroCategoriaDTO> listarTodos() {
    return libroRepository.findAll()
            .stream()
            .map(libro -> new LibroCategoriaDTO(
                    libro.getId(),
                    libro.getTitulo(),

                    // Lista de nombres de autores
                    libro.getAutores().stream()
                            .map(a -> a.getNombre() + " " + a.getApellidoPaterno())
                            .collect(Collectors.toList()),

                    // Lista de IDs de autores  <-- NUEVO
                    libro.getAutores().stream()
                            .map(a -> a.getId())
                            .collect(Collectors.toList()),

                    libro.getAnioPublicacion(),
                    libro.getEditorial(),
                    libro.getCopiasDisponibles(),
                    libro.getCategoria().getId(),
                    libro.getCategoria().getNombre(),
                    libro.getEstatus().name()
            ))
            .collect(Collectors.toList());
}



    // Obtener libro por ID
    public Optional<LibroDTO> obtenerPorId(String id) {
        return libroRepository.findById(id)
                .map(this::convertirADTO);
    }

    // Crear libro
    public LibroDTO crear(LibroDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Libro libro = new Libro();
        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());
        libro.setCopiasDisponibles(dto.getCopiasDisponibles());
        libro.setCategoria(categoria);
        libro.setEstatus(dto.getEstatus() != null
                ? Estatus.valueOf(dto.getEstatus().toUpperCase())
                : Estatus.ACTIVO);

        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<Autores> autores = autorRepository.findAllById(dto.getAutoresIds());
            libro.setAutores(new HashSet<>(autores)); // ✅ Convertir a Set
        }

        Libro guardado = libroRepository.save(libro);
        return convertirADTO(guardado);
    }

    // Actualizar libro
    public LibroDTO actualizar(String id, LibroDTO dto) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        libro.setTitulo(dto.getTitulo());
        libro.setAnioPublicacion(dto.getAnioPublicacion());
        libro.setEditorial(dto.getEditorial());
        libro.setCopiasDisponibles(dto.getCopiasDisponibles());

        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            libro.setCategoria(categoria);
        }

        if (dto.getEstatus() != null) {
            libro.setEstatus(Estatus.valueOf(dto.getEstatus().toUpperCase()));
        }

        if (dto.getAutoresIds() != null) {
            List<Autores> autores = autorRepository.findAllById(dto.getAutoresIds());
            libro.setAutores(new HashSet<>(autores)); // ✅ Convertir a Set
        }

        Libro actualizado = libroRepository.save(libro);
        return convertirADTO(actualizado);
    }

    // Eliminar libro
    public void eliminar(String id) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("Libro no encontrado");
        }
        libroRepository.deleteById(id);
    }

    // Cambiar estatus
    public LibroDTO cambiarEstatus(String id, String nuevoEstatus) {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        try {
            Estatus estatus = Estatus.valueOf(nuevoEstatus.toUpperCase());
            libro.setEstatus(estatus);
            Libro actualizado = libroRepository.save(libro);
            return convertirADTO(actualizado);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estatus inválido. Valores permitidos: ACTIVO, INACTIVO");
        }
    }

    // Conversión a DTO
    private LibroDTO convertirADTO(Libro libro) {
        LibroDTO dto = new LibroDTO();
        dto.setId(libro.getId());
        dto.setTitulo(libro.getTitulo());
        dto.setAnioPublicacion(libro.getAnioPublicacion());
        dto.setEditorial(libro.getEditorial());
        dto.setCopiasDisponibles(libro.getCopiasDisponibles());
        dto.setCategoriaId(libro.getCategoria().getId());
        dto.setEstatus(libro.getEstatus().name());

        if (libro.getAutores() != null) {
            dto.setAutoresIds(libro.getAutores()
                    .stream()
                    .map(Autores::getId)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return libroRepository.findByEstatus(Estatus.ACTIVO)
                .stream()
                .map(libro -> new LibroActivoDTO(libro.getId(), libro.getTitulo()))
                .toList();
    }
}
