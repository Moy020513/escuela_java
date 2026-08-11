package com.moises.escuela.enums;

import com.moises.escuela.entities.Curso;
import com.moises.escuela.exceptions.RecursoNoEncontradoException;
import com.moises.escuela.utils.StringCustonUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {

    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado");

    private final String descripcion;

    public static DiaSemana obtenerDiaSemanaPorDescripcion(String descripcion) {
        StringCustonUtils.validarNoVacio(descripcion, "La descripción es requerida");

        String descripcionNormalizada = StringCustonUtils.quitarAcentos(descripcion);

        for (DiaSemana diaSemana : values()) {
            if (StringCustonUtils.quitarAcentos(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return diaSemana;
        }

        throw new RecursoNoEncontradoException("No existe un día de la semana con la descripción: " + descripcion);

    }

}