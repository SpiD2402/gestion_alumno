package com.example.gestion_alumnos.service;

import com.example.gestion_alumnos.agregates.request.RequestCurso;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface CursoService {


    ResponseBase findAllCourses();
    ResponseBase findByIdCourse(Long id);

    ResponseBase updateCourse(RequestCurso curso, Long id);

    ResponseBase addCourse(RequestCurso curso);

    ResponseBase deleteCurse(Long id);


}
