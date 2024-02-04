package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.agregates.request.RequestProfesor;
import com.example.gestion_alumnos.dao.ProfesorDao;
import org.springframework.stereotype.Service;

@Service
public class ProfesorValidate {

    private final ProfesorDao profesorDao;


    public ProfesorValidate(ProfesorDao profesorDao) {
        this.profesorDao = profesorDao;
    }

    public boolean validarProfesor(RequestProfesor requestProfesor)
    {
        return profesorDao.existsByDni(requestProfesor.getDni());
    }


}
