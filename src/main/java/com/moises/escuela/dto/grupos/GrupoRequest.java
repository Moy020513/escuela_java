package com.moises.escuela.dto.grupos;

import jakarta.validation.constraints.*;
import org.aspectj.bridge.IMessage;

public record GrupoRequest(

        @NotNull(message = "El id es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idCurso,

        @NotNull(message = "El id es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idMaestro,

        @NotNull(message = "El id es requerido")
        @Positive(message = "El id debe ser positivo")
        Long idAula,

        @NotBlank(message = "El periodo es requerido")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "El periodo debe tener el formato YYYY-MM")
        String periodo
) {
}
