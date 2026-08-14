package com.moises.escuela.services.grupos;

import com.moises.escuela.dto.cursos.CursoRequest;
import com.moises.escuela.dto.grupos.GrupoRequest;
import com.moises.escuela.dto.grupos.GrupoResponse;
import com.moises.escuela.entities.Aula;
import com.moises.escuela.entities.Curso;
import com.moises.escuela.entities.Grupo;
import com.moises.escuela.entities.Maestro;
import com.moises.escuela.exceptions.EntidadRelacionadaException;
import com.moises.escuela.mappers.GrupoMapper;
import com.moises.escuela.repositories.*;
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
public class GrupoServiceImpl implements GrupoService{

    private final GrupoRepository grupoRepository;

    private final GrupoMapper grupoMapper;
    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final HorarioRepository horarioRepository;

    @Override
    public List<GrupoResponse> listar() {
        log.info("Listando todos los grupos");
        return grupoRepository.findAll().stream()
                .map(grupoMapper::entidadAResponse)
                .toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        log.info("Buscando grupo con ID: {}", id);
        Grupo grupo = obtenerGrupo(id);
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando nuevo grupo...");
        Curso curso = obtenerCurso(request.idCurso());

        Maestro maestro = obtenerMaestro(request.idMaestro());

        Aula aula = obtenerAula(request.idAula());

        Grupo grupo = grupoMapper.requestAEntidad(request, curso, aula, maestro);
        if(grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodo(request.idCurso(),
                request.idMaestro(), request.idAula(), request.periodo()))
            throw new IllegalArgumentException("Ya existe un grupo con el mismo curso, maestro, aula y periodo");

        grupoRepository.save(grupo);

        log.info("Nuevo grupo registrado correctamente");
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        Grupo grupo = obtenerGrupo(id);
        log.info("Actulizando grupo con id: {}", id);
        Curso curso = obtenerCurso(request.idCurso());

        Maestro maestro = obtenerMaestro(request.idMaestro());

        Aula aula = obtenerAula(request.idAula());

        if(grupoRepository.existsByCursoIdAndMaestroIdAndAulaIdAndPeriodoAndIdNot(request.idCurso(),
                request.idMaestro(), request.idAula(), request.periodo(), id))
            throw new IllegalArgumentException("Ya existe un grupo con el mismo curso, maestro, aula y periodo");

        grupo.actualizar(curso, maestro, aula, request.periodo());

        log.info("Grupo actualizado correctamente");
        return grupoMapper.entidadAResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        Grupo grupo = obtenerGrupo(id);
        log.info("Eliminando grupo con id: {}", id);

        if (inscripcionRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el grupo ya que tiene inscripciones asociadas"
            );
        if (horarioRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException(
                    "No se puede eliminar el grupo ya que tiene inscripciones asociadas"
            );
        grupoRepository.delete(grupo);
        log.info("Grupo con id: {} eliminado correctamente", grupo.getId());
    }

    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }
    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }
    private Maestro obtenerMaestro(Long id) {
        return ServiceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }
    private Aula obtenerAula(Long id) {
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }
}
