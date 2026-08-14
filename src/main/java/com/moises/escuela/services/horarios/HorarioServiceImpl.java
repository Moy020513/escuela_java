
package com.moises.escuela.services.horarios;

import com.moises.escuela.dto.horarios.HorarioRequest;
import com.moises.escuela.dto.horarios.HorarioResponse;
import com.moises.escuela.entities.Grupo;
import com.moises.escuela.entities.Horario;
import com.moises.escuela.enums.DiaSemana;
import com.moises.escuela.mappers.HorarioMapper;
import com.moises.escuela.repositories.GrupoRepository;
import com.moises.escuela.repositories.HorarioRepository;
import com.moises.escuela.utils.ServiceUtils;
import com.moises.escuela.utils.StringCustomUtils;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HorarioServiceImpl implements HorarioService {
    private final HorarioRepository horarioRepository;

    private final GrupoRepository grupoRepository;
    private final HorarioMapper horarioMapper;

    @Override
    public List<HorarioResponse> listar() {
        log.info("Listando todos los horarios");
        return horarioRepository.findAll().stream()
                .map(horarioMapper::entidadAResponse)
                .toList();
    }

    @Override
    public HorarioResponse obtenerPorId(Long id) {
        log.info("Buscando horario con ID: {}", id);
        Horario horario = obtenerHorario(id);
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Registrando nuevo horario...");
        Grupo grupo = obtenerGrupo(request.idGrupo());

        DiaSemana diaSemana = obtenerDiaSemanaPorDescripcion(request.dia());

        List<Horario> horarios = horarioRepository.obtenerHorariosConflicto(diaSemana, request.idGrupo(), grupo.getAula().getId(), grupo.getPeriodo());

        LocalTime horaInicio = parseStringALocalTime(request.horaInicio());
        LocalTime horaFin = parseStringALocalTime(request.horaFin());
        Horario horario = horarioMapper.requestAEntidad(request, grupo, diaSemana);

        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException(
                    "La hora de inicio debe ser menos que la hora de fin"
            );
        }
        validarTraslape(horarios, horaInicio, horaFin);
        horarioRepository.save(horario);
        log.info("Nuevo horario registrado correctamente");
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        Horario horario = obtenerHorario(id);

        log.info("Actualizando horario con id: {}", id);
        Grupo grupo = obtenerGrupo(request.idGrupo());

        DiaSemana diaSemana = obtenerDiaSemanaPorDescripcion(request.dia());
        LocalTime horaInicio = parseStringALocalTime(request.horaInicio());
        LocalTime horaFin = parseStringALocalTime(request.horaFin());

        if (!horaInicio.isBefore(horaFin)) {
            throw new IllegalArgumentException(
                    "La hora de inicio debe ser menor que la hora de fin"
            );
        }
        List<Horario> horarios = horarioRepository.obtenerHorariosConflicto(diaSemana, request.idGrupo(), grupo.getAula().getId(), grupo.getPeriodo(), id);
        validarTraslape(horarios, horaInicio, horaFin);

        horario.actualizar(
                grupo,
                diaSemana,
                request.horaInicio(),
                request.horaFin()
        );
        log.info("Horario actualizado correctamente");
        return horarioMapper.entidadAResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        Horario horario = obtenerHorario(id);
        log.info("Eliminando horario con id: {}", id);

        horarioRepository.delete(horario);
        log.info("Horario con id {} eliminado correctamente", horario.getId());

    }

    private Horario obtenerHorario(Long id) {
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }

    private Grupo obtenerGrupo(Long id) {
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

    private DiaSemana obtenerDiaSemanaPorDescripcion(String descripcion) {
        return DiaSemana.obtenerDiaSemanaPorDescripcion(descripcion.trim());
    }

    private LocalTime parseStringALocalTime(String horaStr) {
        return LocalTime.parse(horaStr, StringCustomUtils.FORMATOHORA);
    }

    private void validarTraslape(List<Horario> horarios, LocalTime horaInicioNueva, LocalTime horaFinNueva) {
        horarios.stream().forEach( horario -> {
            LocalTime horaInicioExistente =
                    LocalTime.parse(horario.getHoraInicio(), StringCustomUtils.FORMATOHORA);

            LocalTime horaFinExistente =
                    LocalTime.parse(horario.getHoraFin(), StringCustomUtils.FORMATOHORA);

            boolean traslapa =
                    horaInicioExistente.isBefore(horaFinNueva)
                            && horaFinExistente.isAfter(horaInicioNueva);

            if (traslapa) {
                throw new IllegalArgumentException(
                        "El horario se traslapa con otro horario existente"
                );
            }
        });
    }
}
