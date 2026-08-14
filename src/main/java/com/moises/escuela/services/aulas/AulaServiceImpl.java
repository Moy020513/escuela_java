package com.moises.escuela.services.aulas;


import com.moises.escuela.dto.aulas.AulaRequest;
import com.moises.escuela.dto.aulas.AulaResponse;
import com.moises.escuela.dto.cursos.CursoRequest;
import com.moises.escuela.entities.Aula;
import com.moises.escuela.entities.Curso;
import com.moises.escuela.exceptions.EntidadRelacionadaException;
import com.moises.escuela.mappers.AulaMapper;
import com.moises.escuela.repositories.AulaRepository;
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
public class AulaServiceImpl implements AulaService {

    private final AulaRepository aulaRepository;

    private final GrupoRepository grupoRepository;
    private final AulaMapper aulaMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        log.info("Listando todas las aulas");
        return aulaRepository.findAll().stream()
                .map(aulaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        log.info("Buscando aula con ID: {}", id);
        Aula aula = obtenerAula(id);
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando nueva aula...");

        validarDatosUnicos(request);
        Aula aula = aulaMapper.requestAEntidad(request);
        Aula aulaGuardado = aulaRepository.save(aula);
        log.info("Nueva aula {} resgistrada", aula.getNombre());
        return aulaMapper.entidadAResponse(aulaGuardado);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        Aula aula = obtenerAula(id);
        log.info("Actualizando aula con ID: {}", id);
        validarCambiosUnicos(request, id);

        aula.actualizar(
                request.nombre(),
                request.capacidad()
        );
        return aulaMapper.entidadAResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);
        log.info("Eliminando aula con id: {}", id);
        if (grupoRepository.existsByAulaId(id))
            throw  new EntidadRelacionadaException(
                    "No se puede eliminar el aula, ya que tiene grupos asignados");
        aulaRepository.delete(aula);

        log.info("Aula {} eliminanda correctamente", aula.getNombre());

    }

    private Aula obtenerAula(Long id) {
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private void validarDatosUnicos(AulaRequest request) {
        log.info("Validando nombre único: {}", request.nombre());

        if (aulaRepository.existsByNombre(request.nombre().trim())) {
            throw new IllegalArgumentException("Ya existe una aula con el nombre: " + request.nombre());
        }
    }

    private void validarCambiosUnicos(AulaRequest request, Long id) {
        log.info("Validando nombre único: {}", request.nombre());

        if (aulaRepository.existsByNombreAndIdNot(request.nombre().trim(), id)) {
            throw new IllegalArgumentException("Ya existe una aula con el nombre: " + request.nombre());
        }
    }
}
