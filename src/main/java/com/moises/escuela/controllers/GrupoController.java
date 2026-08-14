package com.moises.escuela.controllers;

import com.moises.escuela.dto.grupos.GrupoRequest;
import com.moises.escuela.dto.grupos.GrupoResponse;
import com.moises.escuela.services.curso.CursoService;
import com.moises.escuela.services.grupos.GrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends CommonController<GrupoRequest, GrupoResponse, GrupoService>{

    public GrupoController(GrupoService service) {
        super(service);
    }
}
