package com.moises.escuela.dto.aulas;

public record AulaResponse(
        Long id,
        String nombre,
        Integer capacidad
) {
}
