package com.primaria.app.Service;

import com.primaria.app.DTO.PrestamoDTO;
import com.primaria.app.DTO.PrestamoDetalleDTO;
import com.primaria.app.Model.EstatusPrestamo;
import java.time.LocalDate;
import java.util.List;

public interface PrestamoService {

    PrestamoDTO crearPrestamo(PrestamoDTO dto);

    // 🔹 Ahora recibe también la cantidad devuelta
    PrestamoDTO devolverPrestamo(String prestamoId, Integer cantidadDevuelta);

    List<PrestamoDetalleDTO> obtenerDetalles();

    // 🔹 Búsqueda con filtros opcionales
    List<PrestamoDetalleDTO> filtrarPrestamos(LocalDate fechaPrestamo,
                                              String alumnoNombre,
                                              String libroTitulo,
                                              EstatusPrestamo estatus);


   

                                              
}
