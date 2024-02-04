package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.request.RequestNota;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.NotaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nota")
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService) {
        this.notaService = notaService;
    }

    @PostMapping
    public ResponseBase addNotas(@RequestBody RequestNota requestNota)
    {

        return notaService.addNota(requestNota);
    }

    @GetMapping
    public ResponseBase allNotas(@RequestBody RequestNota requestNota)
    {
        return notaService.findAllNotas();
    }

}
