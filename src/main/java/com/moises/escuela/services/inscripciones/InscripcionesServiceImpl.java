package com.moises.escuela.services.inscripciones;

import com.moises.escuela.dto.inscripciones.InscripcionesRequest;
import com.moises.escuela.dto.inscripciones.InscripcionesResponse;
import com.moises.escuela.entities.*;
import com.moises.escuela.exceptions.EntidadRelacionadaException;
import com.moises.escuela.repositories.CalificacionRepository;
import com.moises.escuela.mappers.InscripcionesMapper;
import com.moises.escuela.repositories.AlumnoRepository;
import com.moises.escuela.repositories.GrupoRepository;
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
public class InscripcionesServiceImpl implements InscripcionesService{

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionesMapper inscripcionesMapper;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final CalificacionRepository calificacionRepository;
    @Override
    public List<InscripcionesResponse> listar() {
        log.info("Listando todas las inscripciones");
        return inscripcionRepository.findAll().stream()
                .map(inscripcionesMapper::entidadAResponse)
                .toList();
    }

    @Override
    public InscripcionesResponse obtenerPorId(Long id) {
        log.info("Buscando inscripcion con ID: {}", id);
        Inscripcion inscripcion = obtenerInscripcion(id);
        return inscripcionesMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionesResponse registrar(InscripcionesRequest request) {
        log.info("Registrando nueva inscripcion...");
        Alumno alumno = obtenerAlumno(request.idAlumno());
        Grupo grupo = obtenerGrupo(request.idGrupo());

        if(inscripcionRepository.existsByAlumnoIdAndGrupoId(request.idAlumno(),
                request.idGrupo())) {
            throw new IllegalArgumentException("El alumno ya fue registrado en el grupo con id: " + grupo.getId());
        }
        Inscripcion inscripcion = inscripcionesMapper.requestAEntidad(request, alumno, grupo);
        inscripcionRepository.save(inscripcion);

        log.info("Nueva inscripción registrada correctamente");
        return inscripcionesMapper.entidadAResponse(inscripcion);
    }

    @Override
    public InscripcionesResponse actualizar(InscripcionesRequest request, Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);
        log.info("Actualizando inscripción con id: {}", id);
        Alumno alumno = obtenerAlumno(request.idAlumno());
        Grupo grupo = obtenerGrupo(request.idGrupo());

        if(inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(request.idAlumno(),
                request.idGrupo(), id)) {
            throw new IllegalArgumentException("El alumno ya fue registrado en el grupo con id: " + grupo.getId());
        }
        inscripcion.actualizar(alumno, grupo);
        log.info("Inscripción actualizada correctamente");
        return inscripcionesMapper.entidadAResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        Inscripcion inscripcion = obtenerInscripcion(id);
        log.info("Eliminando inscripción con id: {}", id);

        if (calificacionRepository.existsByInscripcionId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar la inscripción ya que tiene calificación asociada"
            );
        inscripcionRepository.delete(inscripcion);
        log.info("Inscripcioón con id: {} eliminado correctamente", inscripcion.getId());
    }
    private Inscripcion obtenerInscripcion(Long id) {
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
    private Alumno obtenerAlumno(Long id) {
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }
    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }
}
