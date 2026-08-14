package com.moises.escuela.mappers;

import com.moises.escuela.dto.datos.DatosAlumno;
import com.moises.escuela.dto.datos.DatosGrupo;
import com.moises.escuela.dto.inscripciones.InscripcionesRequest;
import com.moises.escuela.dto.inscripciones.InscripcionesResponse;
import com.moises.escuela.entities.Alumno;
import com.moises.escuela.entities.Grupo;
import com.moises.escuela.entities.Inscripcion;
import com.moises.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@Component
public class InscripcionesMapper implements CommonMapper<InscripcionesRequest, InscripcionesResponse, Inscripcion>{

    private final AlumnoMapper alumnoMapper;

    private final GrupoMapper grupoMapper;

    @Override
    public Inscripcion requestAEntidad(InscripcionesRequest request) {
        if (request == null) return null;

        return Inscripcion.builder()
                .fechaInscripcion(LocalDate.now())
                .build();
    }

    public Inscripcion requestAEntidad(InscripcionesRequest request, Alumno alumno, Grupo grupo) {
        if (request == null) return null;

        return Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .fechaInscripcion(LocalDate.now())
                .build();
    }

    @Override
    public InscripcionesResponse entidadAResponse(Inscripcion entidad) {
        if (entidad == null) return null;

        DatosAlumno datosAlumno = entidadADatoAlumno(entidad);

        DatosGrupo datosGrupo = grupoMapper.entidadADatoGrupo(entidad.getGrupo());

        return new InscripcionesResponse(
                entidad.getId(),
                datosAlumno,
                datosGrupo,
                entidad.getCalificacion() != null ? entidad.getCalificacion().getCalificacion() : null,
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
