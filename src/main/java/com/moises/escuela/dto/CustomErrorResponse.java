package com.moises.escuela.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {
}