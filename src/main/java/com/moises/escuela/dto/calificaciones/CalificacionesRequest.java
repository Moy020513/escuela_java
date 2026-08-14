package com.moises.escuela.dto.calificaciones;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CalificacionesRequest(
        @NotNull(message = "El ID de la inscripción es requerido")
        @Positive(message = "El ID de la inscripción debe ser positivo")
        Long idInscripcion,

        @NotNull(message = "La calificación es requerida")
        @Min(value = 0, message = "La calificación mímina es 0")
        @Max(value = 10, message = "La calificación máxima es 10")
        BigDecimal calificacion
) {
}