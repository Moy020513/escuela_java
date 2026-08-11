package com.moises.escuela.dto.maestros;

import com.moises.escuela.dto.datos.DatosCurso;

import java.util.List;

public record MaestroResponse(
        Long id,
        String nombre,
        String email,
        String telefono,
        List<DatosCurso> cursos
) {
}
