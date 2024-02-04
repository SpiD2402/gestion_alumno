package com.example.gestion_alumnos.service;


import com.example.gestion_alumnos.agregates.request.RequestAsignacion;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface AsignacionService {


    ResponseBase findAllAsignaciones();
    ResponseBase findByIdAsignacion(Long id);

    ResponseBase updateAsignacion(RequestAsignacion asignacion, Long id);

    ResponseBase addAsignacion(RequestAsignacion asignacion);

    ResponseBase deleteAsignacion(Long id);






}
