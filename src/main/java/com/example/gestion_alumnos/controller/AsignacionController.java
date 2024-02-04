package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.request.RequestAsignacion;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.AsignacionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/asignacion")
public class AsignacionController {

    private final AsignacionService asignacionService;

    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @PostMapping
    public ResponseBase createAsignacion(@RequestBody RequestAsignacion requestAsignacion)

    {
        return asignacionService.addAsignacion(requestAsignacion);
    }

    @GetMapping
    public  ResponseBase allAsignacion()
    {
        return asignacionService.findAllAsignaciones();
    }

    @GetMapping("/{id}")
    public  ResponseBase findByIdAsignacion(@PathVariable Long id)
    {
        return  asignacionService.findByIdAsignacion(id);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteAsignacion(@PathVariable Long id)
    {
        return asignacionService.deleteAsignacion(id);
    }
}
