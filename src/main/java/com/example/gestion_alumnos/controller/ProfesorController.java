package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.request.RequestProfesor;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.ProfesorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profesor")
public class ProfesorController {


    private final ProfesorService profesorService;


    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }


    @GetMapping
    public ResponseBase allProfesores()
    {
        return profesorService.findAllProfesor();
    }

    @GetMapping("/{id}")
    public ResponseBase getByIdProfesor(@PathVariable Long id)
    {
        return profesorService.findByIdProfesor(id);
    }

    @PostMapping
    public ResponseBase addProfesor(@RequestBody RequestProfesor requestProfesor)
    {
        return profesorService.addProfesor(requestProfesor);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteProfesor(@PathVariable Long id)
    {
        return profesorService.deleteProfesor(id);
    }

}
