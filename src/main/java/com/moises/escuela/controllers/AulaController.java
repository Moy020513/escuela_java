package com.moises.escuela.controllers;


import com.moises.escuela.dto.aulas.AulaRequest;
import com.moises.escuela.dto.aulas.AulaResponse;
import com.moises.escuela.services.alumnos.AlumnoService;
import com.moises.escuela.services.aulas.AulaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aulas")
public class AulaController extends CommonController<AulaRequest, AulaResponse, AulaService>{

    public AulaController(AulaService service) {
        super(service);
    }
}
