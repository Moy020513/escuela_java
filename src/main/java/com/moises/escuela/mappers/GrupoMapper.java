package com.moises.escuela.mappers;

import com.moises.escuela.dto.datos.DatosAula;
import com.moises.escuela.dto.datos.DatosCurso;
import com.moises.escuela.dto.datos.DatosGrupo;
import com.moises.escuela.dto.datos.DatosMaestro;
import com.moises.escuela.dto.grupos.GrupoRequest;
import com.moises.escuela.dto.grupos.GrupoResponse;
import com.moises.escuela.entities.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrupoMapper implements CommonMapper<GrupoRequest, GrupoResponse, Grupo>{
    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        if(request == null) return null;

        return Grupo.builder()
                .periodo(request.periodo())
                .build();
    }

    public Grupo requestAEntidad(GrupoRequest request, Curso curso, Aula aula, Maestro maestro) {
        if(request == null) return null;
        return Grupo.builder()
                .curso(curso)
                .aula(aula)
                .maestro(maestro)
                .periodo(request.periodo())
                .build();
    }

    @Override
    public GrupoResponse entidadAResponse(Grupo entidad) {
        if (entidad == null) return null;
        return new GrupoResponse(
                entidad.getId(),
                entidadADatosCurso(entidad),
                entidadADatosMaestro(entidad),
                entidadADatosAula(entidad),
                horariosArrayString(entidad.getHorarios()),
                entidad.getPeriodo()
        );
    }


    private DatosCurso entidadADatosCurso(Grupo entidad) {
        if (entidad == null || entidad.getCurso() == null) return null;
        Curso curso = entidad.getCurso();
        return new DatosCurso(
                curso.getNombre(),
                curso.getDescripcion(),
                curso.getCreditos()
        );
    }

    private DatosMaestro entidadADatosMaestro(Grupo entidad) {
        if (entidad == null || entidad.getMaestro() == null) return null;
        Maestro maestro = entidad.getMaestro();
        return new DatosMaestro(
                String.join(" ",
                        maestro.getNombre(),
                        maestro.getApellidoPaterno(),
                        maestro.getApellidoMaterno()),
                maestro.getEmail(),
                maestro.getTelefono()
        );
    }

    private DatosAula entidadADatosAula(Grupo entidad) {
        if (entidad == null || entidad.getAula() == null) return null;
        Aula aula = entidad.getAula();
        return new DatosAula(
                aula.getNombre(),
                aula.getCapacidad()
        );
    }

    private List<String> horariosArrayString(List<Horario> horarios) {
        if (horarios == null || horarios.isEmpty())
            return List.of();

        return horarios.stream()
                .map(horario ->
                        horario.getDiaSemana() + " " + horario.getHoraInicio() + " _ " + horario.getHoraFin())
                .toList();
    }

    public DatosGrupo entidadADatoGrupo(Grupo entidad) {
        if (entidad == null)
            return null;

        return new DatosGrupo(
                entidad.getCurso().getNombre(),
                String.join(" ",
                        entidad.getMaestro().getNombre(),
                        entidad.getMaestro().getApellidoPaterno(),
                        entidad.getMaestro().getApellidoMaterno()),
                entidad.getAula().getNombre(),
                entidad.getPeriodo()
        );
    }
}
