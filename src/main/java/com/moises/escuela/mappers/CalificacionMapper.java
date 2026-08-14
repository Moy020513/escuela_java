package com.moises.escuela.mappers;

import com.moises.escuela.dto.calificaciones.CalificacionesRequest;
import com.moises.escuela.dto.calificaciones.CalificacionesResponse;
import com.moises.escuela.dto.datos.DatosAlumno;
import com.moises.escuela.dto.datos.DatosGrupo;
import com.moises.escuela.dto.datos.DatosInscripcion;
import com.moises.escuela.entities.Calificacion;
import com.moises.escuela.entities.Inscripcion;
import com.moises.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class CalificacionMapper implements CommonMapper<CalificacionesRequest, CalificacionesResponse, Calificacion>{

    private final AlumnoMapper alumnoMapper;

    private final GrupoMapper grupoMapper;

    @Override
    public Calificacion requestAEntidad(CalificacionesRequest request) {
       if (request == null) return null;
       return Calificacion.builder()
               .calificacion(request.calificacion())
               .fechaRegistro(LocalDate.now())
               .build();
    }
    public Calificacion requestAEntidad(CalificacionesRequest request, Inscripcion inscripcion) {
        if (request == null) return null;

        Calificacion calificacion = requestAEntidad(request);
        calificacion.asignarInscripcion(inscripcion);
        return calificacion;
    }

    @Override
    public CalificacionesResponse entidadAResponse(Calificacion entidad) {
        if (entidad == null) return null;

        DatosInscripcion datosInscripcion = entidadADatoInscripcion(entidad.getInscripcion());

        return new CalificacionesResponse(
                entidad.getId(),
                datosInscripcion,
                entidad.getCalificacion(),
                entidad.getFechaRegistro().format(StringCustomUtils.FORMATOFECHA)
        );
    }
    public DatosInscripcion entidadADatoInscripcion(Inscripcion entidad) {
        if (entidad == null)
            return null;

        DatosAlumno datosAlumno = entidadADatoAlumno(entidad);

        DatosGrupo datosGrupo = grupoMapper.entidadADatoGrupo(entidad.getGrupo());

        return new DatosInscripcion(
                datosAlumno,
                datosGrupo,
                entidad.getFechaInscripcion().format(StringCustomUtils.FORMATOFECHA)
        );
    }

    public DatosAlumno entidadADatoAlumno(Inscripcion entidad) {
        if (entidad == null) return null;

        return new DatosAlumno(
                String.join(" ",
                        entidad.getAlumno().getNombre(),
                        entidad.getAlumno().getApellidoPaterno(),
                        entidad.getAlumno().getApellidoMaterno()),
                entidad.getAlumno().getMatricula(),
                entidad.getAlumno().getEmail(),
                entidad.getAlumno().getFechaIngreso().format(StringCustomUtils.FORMATOFECHA)
        );
    }
}
