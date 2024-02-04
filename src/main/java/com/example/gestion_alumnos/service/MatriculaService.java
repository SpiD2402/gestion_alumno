package com.example.gestion_alumnos.service;

import com.example.gestion_alumnos.agregates.request.RequestMatricula;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface MatriculaService {

    ResponseBase findAllMatricula();
    ResponseBase findByIdMatricula(Long id);

    ResponseBase updateMatricula(RequestMatricula matricula, Long id);

    ResponseBase addMatricula(RequestMatricula matricula);

    ResponseBase deleteMatricula(Long id);



}
