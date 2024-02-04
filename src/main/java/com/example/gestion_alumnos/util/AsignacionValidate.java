package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.agregates.request.RequestAsignacion;
import com.example.gestion_alumnos.dao.AsignacionDao;
import org.springframework.stereotype.Service;

@Service
public class AsignacionValidate {

    private final AsignacionDao asignacionDao;


    public AsignacionValidate(AsignacionDao asignacionDao) {
        this.asignacionDao = asignacionDao;
    }

    public  boolean validatAsignacion(RequestAsignacion requestAsignacion)
    {


        if (isNullorEmpty(String.valueOf(requestAsignacion.getIdCurso())) || isNullorEmpty(String.valueOf(requestAsignacion.getIdProfesor())))
        {
            return  true;
        }

        return false;
    }




    public boolean isNullorEmpty (String valor)
    {
        return  valor ==null || valor.isEmpty();
    }
}
