package com.example.gestion_alumnos.service;

import com.example.gestion_alumnos.agregates.request.RequestAlumno;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface AlumnoService {
    ResponseBase findAllStudent();
    ResponseBase findByIdStudent(Long id);

    ResponseBase updateStudent(RequestAlumno alumno, Long id);

    ResponseBase addStudent(RequestAlumno alumno);

    ResponseBase deleteStudent(Long id);
}
