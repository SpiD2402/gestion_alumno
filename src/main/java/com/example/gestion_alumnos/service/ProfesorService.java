package com.example.gestion_alumnos.service;

import com.example.gestion_alumnos.agregates.request.RequestProfesor;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface ProfesorService {

    ResponseBase findAllProfesor();
    ResponseBase findByIdProfesor(Long id);

    ResponseBase updateProfesor(RequestProfesor profesor, Long id);

    ResponseBase addProfesor(RequestProfesor profesor);

    ResponseBase deleteProfesor(Long id);

}
