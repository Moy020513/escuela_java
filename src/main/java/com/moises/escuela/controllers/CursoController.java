package com.moises.escuela.controllers;

import com.moises.escuela.dto.cursos.CursoRequest;
import com.moises.escuela.dto.cursos.CursoResponse;
import com.moises.escuela.services.alumnos.AlumnoService;
import com.moises.escuela.services.curso.CursoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cursos")
public class CursoController extends CommonController<CursoRequest, CursoResponse, CursoService>{

    public CursoController(CursoService service) {
        super(service);
    }
}
