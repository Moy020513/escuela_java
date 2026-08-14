package com.moises.escuela.controllers;

import com.moises.escuela.dto.inscripciones.InscripcionesRequest;
import com.moises.escuela.dto.inscripciones.InscripcionesResponse;
import com.moises.escuela.services.inscripciones.InscripcionesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController extends CommonController<InscripcionesRequest, InscripcionesResponse, InscripcionesService>{
    public InscripcionController(InscripcionesService service) {
        super(service);
    }
}
