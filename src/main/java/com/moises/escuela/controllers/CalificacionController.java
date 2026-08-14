package com.moises.escuela.controllers;

import com.moises.escuela.dto.calificaciones.CalificacionesRequest;
import com.moises.escuela.dto.calificaciones.CalificacionesResponse;
import com.moises.escuela.services.calificaciones.CalificacionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController extends CommonController<CalificacionesRequest, CalificacionesResponse, CalificacionService>{
    public CalificacionController(CalificacionService service) {
        super(service);
    }
}

