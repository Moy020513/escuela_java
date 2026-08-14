package com.moises.escuela.dto.inscripciones;

import com.moises.escuela.dto.datos.DatosAlumno;
import com.moises.escuela.dto.datos.DatosGrupo;

import java.math.BigDecimal;

public record InscripcionesResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion
) {
}