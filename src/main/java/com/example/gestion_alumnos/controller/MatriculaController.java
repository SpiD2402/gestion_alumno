package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.request.RequestMatricula;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.MatriculaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matricula")
public class MatriculaController {
    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }


    @PostMapping
    public ResponseBase addMatricula(@RequestBody RequestMatricula requestMatricula)
    {
        return matriculaService.addMatricula(requestMatricula);
    }

    @GetMapping
    public ResponseBase allMatriculas()
    {
        return  matriculaService.findAllMatricula();
    }

    @GetMapping("/{id}")
    public ResponseBase findBiMatricula(@PathVariable Long id)
    {
        return matriculaService.findByIdMatricula(id);
    }

    @DeleteMapping("/{id}")
    public  ResponseBase deleteMatricula(@PathVariable Long id)
    {
        return matriculaService.deleteMatricula(id);
    }

}
