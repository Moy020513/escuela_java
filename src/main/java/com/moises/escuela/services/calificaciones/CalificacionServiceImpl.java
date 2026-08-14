package com.moises.escuela.services.calificaciones;


import com.moises.escuela.dto.calificaciones.CalificacionesRequest;
import com.moises.escuela.dto.calificaciones.CalificacionesResponse;
import com.moises.escuela.entities.Calificacion;
import com.moises.escuela.entities.Grupo;
import com.moises.escuela.entities.Inscripcion;
import com.moises.escuela.mappers.CalificacionMapper;
import com.moises.escuela.repositories.CalificacionRepository;
import com.moises.escuela.repositories.InscripcionRepository;
import com.moises.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CalificacionServiceImpl implements CalificacionService{

    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final InscripcionRepository inscripcionRepository;

    @Override
    public List<CalificacionesResponse> listar() {
        log.info("Listando todas las calificaciones");
        return calificacionRepository.findAll().stream()
                .map(calificacionMapper::entidadAResponse)
                .toList();
    }

    @Override
    public CalificacionesResponse obtenerPorId(Long id) {
        log.info("Buscando calificacion con ID: {}", id);
        Calificacion calificacion = obtenerCalificacion(id);
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionesResponse registrar(CalificacionesRequest request) {
        log.info("Registrando nueva califiación...");
        Inscripcion inscripcion = obtenerInscripcion(request.idInscripcion());
        Calificacion calificacion = calificacionMapper.requestAEntidad(request, inscripcion);

        if(calificacionRepository.existsByInscripcionId(request.idInscripcion())) {
            throw new IllegalArgumentException("La inscripción ya tiene una califiación registrada");
        }

        calificacionRepository.save(calificacion);
        log.info("Nueva calificacion registrada");
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public CalificacionesResponse actualizar(CalificacionesRequest request, Long id) {
        Calificacion calificacion = obtenerCalificacion(id);
        log.info("Actualizando califiación con id: {}", id);

        Inscripcion inscripcion = obtenerInscripcion(request.idInscripcion());

        if(calificacionRepository.existsByInscripcionIdAndIdNot(request.idInscripcion(), id)) {
            throw new IllegalArgumentException("La inscripción ya tiene una califiación registrada");
        }

        calificacion.actualizar(
                request.calificacion(),
                inscripcion
        );
        log.info("Calificación con id: {} actualizada correctamente", calificacion.getId());
        return calificacionMapper.entidadAResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        Calificacion calificacion = obtenerCalificacion(id);

        log.info("Eliminando calificación con id: {}", id);
        calificacionRepository.delete(calificacion);

        log.info("Calificación con id {} eliminada correctamente", calificacion.getId());

    }

    private Calificacion obtenerCalificacion(Long id) {
        return ServiceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }
    private Inscripcion obtenerInscripcion(Long id) {
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
}
