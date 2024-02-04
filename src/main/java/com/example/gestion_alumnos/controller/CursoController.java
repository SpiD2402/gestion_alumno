package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.request.RequestCurso;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.CursoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/curso")
public class CursoController {

     private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public  ResponseBase allCourse()
    {
        return  cursoService.findAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseBase byIdCourse(@PathVariable Long id)
    {
        return cursoService.findByIdCourse(id);
    }

    @PutMapping("/{id}")
    public ResponseBase updateCourse(@PathVariable Long id, @RequestBody RequestCurso requestCurso)
    {
        return cursoService.updateCourse(requestCurso,id);
    }


    @PostMapping
    public  ResponseBase createCourse(@RequestBody RequestCurso requestCurso)
    {
        return cursoService.addCourse(requestCurso);
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteCourse(@PathVariable Long id)
    {
        return  cursoService.deleteCurse(id);
    }


}
