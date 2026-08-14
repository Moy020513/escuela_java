package com.moises.escuela.services.curso;


import com.moises.escuela.dto.cursos.CursoRequest;
import com.moises.escuela.dto.cursos.CursoResponse;
import com.moises.escuela.entities.Curso;
import com.moises.escuela.entities.Maestro;
import com.moises.escuela.exceptions.EntidadRelacionadaException;
import com.moises.escuela.exceptions.RecursoNoEncontradoException;
import com.moises.escuela.mappers.CursoMapper;
import com.moises.escuela.repositories.CursoRepository;
import com.moises.escuela.repositories.GrupoRepository;
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
public class CursoServiceImpl implements CursoService{

    private final CursoRepository cursoRepository;

    private final GrupoRepository grupoRepository;

    private final CursoMapper cursoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        log.info("Listando todos los cursos");
        return cursoRepository.findAll().stream()
                .map(cursoMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(Long id) {
        log.info("Buscando curso con ID: {}", id);
        Curso curso = obtenerCurso(id);
        return cursoMapper.entidadAResponse(curso);
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Registrando nuevo curso...");

        validarDatosUnicos(request);
        Curso curso = cursoMapper.requestAEntidad(request);
        Curso cursoGuardado = cursoRepository.save(curso);
        log.info("Nuevo curso {} resgistrado", curso.getNombre());
        return cursoMapper.entidadAResponse(cursoGuardado);
    }

    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        Curso curso = obtenerCurso(id);
        log.info("Actualizando curso con ID: {}", id);
        validarCambiosUnicos(request, id);

        curso.actualizar(
                request.nombre(),
                request.descripcion(),
                request.creditos()
        );
        return cursoMapper.entidadAResponse(curso);
    }
    @Override
    public void eliminar(Long id) {
        Curso curso = obtenerCurso(id);
        log.info("Eliminando curso con id: {}", id);
        if (grupoRepository.existsByCursoId(id))
            throw  new EntidadRelacionadaException(
                    "No se puede eliminar el curso, ya que tiene grupos asignados");
        cursoRepository.delete(curso);

        log.info("Curso {} eliminando correctamente", curso.getNombre());
    }

    private Curso obtenerCurso(Long id) {
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private void validarDatosUnicos(CursoRequest request) {
        log.info("Validando nombre único: {}", request.nombre());

        if (cursoRepository.existsByNombre(request.nombre().trim())) {
            throw new IllegalArgumentException("Ya existe un curso con el nombre: " + request.nombre());
        }
    }

    private void validarCambiosUnicos(CursoRequest request, Long id) {
        log.info("Validando nombre único: {}", request.nombre());

        if (cursoRepository.existsByNombreAndIdNot(request.nombre().trim(), id)) {
            throw new IllegalArgumentException("Ya existe un curso con el nombre: " + request.nombre());
        }
    }
}
