package com.moises.escuela.dto.grupos;

import com.moises.escuela.dto.datos.DatosAula;
import com.moises.escuela.dto.datos.DatosCurso;
import com.moises.escuela.dto.datos.DatosMaestro;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {
}
