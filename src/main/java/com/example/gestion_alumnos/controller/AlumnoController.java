package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.request.RequestAlumno;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.AlumnoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alumno")
public class AlumnoController {

    private final AlumnoService alumnoService;

    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public ResponseBase allAlumno()
    {
        return alumnoService.findAllStudent();
    }

    @PostMapping
    public ResponseBase addAlumno(@RequestBody RequestAlumno requestAlumno)
    {
        return alumnoService.addStudent(requestAlumno);
    }

    @PutMapping("/{id}")
    public  ResponseBase updateAlumno(@RequestBody RequestAlumno requestAlumno,@PathVariable Long id)
    {
        return  alumnoService.updateStudent(requestAlumno,id);
    }

    @GetMapping("/{id}")
    public ResponseBase findByIdAlumno (@PathVariable Long id)
    {
        return alumnoService.findByIdStudent(id);
    }


    @DeleteMapping("/{id}")
    public  ResponseBase deleteAlumno(@PathVariable Long id)
    {
        return alumnoService.deleteStudent(id);
    }

}
