package com.example.gestion_alumnos.service;


import com.example.gestion_alumnos.agregates.request.RequestNota;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface NotaService {

    ResponseBase findAllNotas();
    ResponseBase findByIdNota(Long id);

    ResponseBase updateNota(RequestNota nota, Long id);

    ResponseBase addNota(RequestNota nota);

    ResponseBase deleteNota(Long id);


}
