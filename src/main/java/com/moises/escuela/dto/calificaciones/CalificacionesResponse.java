package com.moises.escuela.dto.calificaciones;

import com.moises.escuela.dto.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionesResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
) {
}
