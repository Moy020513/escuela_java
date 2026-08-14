package com.moises.escuela.mappers;


import com.moises.escuela.dto.alumnos.AlumnoRequest;
import com.moises.escuela.dto.alumnos.AlumnoResponse;
import com.moises.escuela.dto.datos.DatosCalificacion;
import com.moises.escuela.entities.Alumno;
import com.moises.escuela.utils.StringCustomUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class AlumnoMapper implements CommonMapper<AlumnoRequest, AlumnoResponse, Alumno>{

    @Override
    public Alumno requestAEntidad(AlumnoRequest request) {
        if(request == null) return null;

        return Alumno.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .build();
    }

    public Alumno requestAEntidad(AlumnoRequest request, String email, String matricula) {

        if(request == null) return null;

        Alumno  alumno = requestAEntidad(request);
        alumno.asignarDatosAcademicos(email, matricula);

        return alumno;
    }

    @Override
    public AlumnoResponse entidadAResponse(Alumno entidad) {
        if(entidad == null) return null;

        List<DatosCalificacion> calificaciones = entidadADatosCalificacion(entidad);

        return new AlumnoResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getMatricula(),
                StringCustomUtils.localDateAAstring(
                        entidad.getFechaIngreso()),
                calificaciones,
                entidad.calcularPromedio()
        );
    }

    private List<DatosCalificacion> entidadADatosCalificacion(Alumno entidad) {

        if (entidad == null || entidad.getInscripciones() == null || entidad.getInscripciones().isEmpty())
            return List.of();

        return entidad.getInscripciones().stream()
                .map(inscripcion -> new DatosCalificacion(
                        inscripcion.getGrupo().getCurso().getNombre(),
                        inscripcion.getGrupo().getPeriodo(),
                        inscripcion.getCalificacion() != null
                                ? inscripcion.getCalificacion().getCalificacion()
                                : null
                )
        ).toList();
    }

}
