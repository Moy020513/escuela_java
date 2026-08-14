package com.moises.escuela.dto.horarios;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record HorarioRequest(
        @NotNull(message = "El ID del curso es requerido")
        @Positive(message = "El ID del curso debe ser positivo")
        Long idGrupo,


        @NotBlank(message = "El día es un campo requerido")
        String dia,

        @NotBlank(message = "La hora inicio del horario es un campo requerido")
        @Pattern(
                regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "La hora inicio debe tener el formato HH:mm"
        )
        String horaInicio,

        @NotBlank(message = "La hora fin del horario es un campo requerido")
        @Pattern(
                regexp = "^([01][0-9]|2[0-3]):[0-5][0-9]$",
                message = "La hora fin debe tener el formato HH:mm"
        )
        String horaFin
) {
}