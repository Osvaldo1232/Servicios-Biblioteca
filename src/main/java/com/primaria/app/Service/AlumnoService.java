package com.primaria.app.Service;

import com.primaria.app.DTO.AlumnoDTO;
import com.primaria.app.DTO.LibroActivoDTO;
import com.primaria.app.Model.Alumno;
import com.primaria.app.Model.Carrera;
import com.primaria.app.Model.Estatus;
import com.primaria.app.repository.AlumnoRepository;
import com.primaria.app.repository.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    // Crear alumno (sin repetir matrícula)
    public AlumnoDTO crearAlumno(AlumnoDTO dto) {
        Optional<Alumno> existente = alumnoRepository.findByMatriculaIgnoreCase(dto.getMatricula());
        if (existente.isPresent()) {
            throw new RuntimeException("Ya existe un alumno con la matrícula: " + dto.getMatricula());
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + dto.getCarreraId()));

        Alumno alumno = new Alumno();
        alumno.setNombre(dto.getNombre());
        alumno.setApellidoPaterno(dto.getApellidoPaterno());
        alumno.setApellidoMaterno(dto.getApellidoMaterno());
        alumno.setMatricula(dto.getMatricula());
        alumno.setCarrera(carrera);
        alumno.setEstatus(dto.getEstatus() != null ? dto.getEstatus() : Estatus.ACTIVO);

        alumno = alumnoRepository.save(alumno);
        return convertirADTO(alumno);
    }

    // Actualizar alumno
    public AlumnoDTO actualizarAlumno(String id, AlumnoDTO dto) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con ID: " + id));

        if (!alumno.getMatricula().equalsIgnoreCase(dto.getMatricula())) {
            Optional<Alumno> existente = alumnoRepository.findByMatriculaIgnoreCase(dto.getMatricula());
            if (existente.isPresent()) {
                throw new RuntimeException("Ya existe un alumno con la matrícula: " + dto.getMatricula());
            }
        }

        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + dto.getCarreraId()));

        alumno.setNombre(dto.getNombre());
        alumno.setApellidoPaterno(dto.getApellidoPaterno());
        alumno.setApellidoMaterno(dto.getApellidoMaterno());
        alumno.setMatricula(dto.getMatricula());
        alumno.setCarrera(carrera);
        alumno.setEstatus(dto.getEstatus());

        alumno = alumnoRepository.save(alumno);
        return convertirADTO(alumno);
    }

    // Cambiar estatus (ACTIVO / INACTIVO)
    public AlumnoDTO cambiarEstatus(String id, Estatus nuevoEstatus) {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con ID: " + id));

        alumno.setEstatus(nuevoEstatus);
        alumno = alumnoRepository.save(alumno);
        return convertirADTO(alumno);
    }

    // Listar todos
    public List<AlumnoDTO> listarTodos() {
        return alumnoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private AlumnoDTO convertirADTO(Alumno alumno) {
        return new AlumnoDTO(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellidoPaterno(),
                alumno.getApellidoMaterno(),
                alumno.getMatricula(),
                alumno.getCarrera().getId(),
                alumno.getCarrera().getNombre(),
                alumno.getEstatus()
        );
    }
    public List<LibroActivoDTO> obtenerLibrosActivos() {
        return alumnoRepository.findByEstatus(Estatus.ACTIVO)
                .stream()
                .map(libro -> new LibroActivoDTO(libro.getId(), libro.getNombre()+" "+ libro.getApellidoPaterno()+ " " + libro.getApellidoMaterno()))
                .toList();
    }
}
