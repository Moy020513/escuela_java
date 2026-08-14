package com.moises.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InscripcionesRequest(
        @NotNull(message = "El ID del alumno es requerido")
        @Positive(message = "El ID del alumno debe ser positivo")
        Long idAlumno,

        @NotNull(message = "El ID del grupo es requerido")
        @Positive(message = "El ID del grupo debe ser positivo")
        Long idGrupo
) {
}