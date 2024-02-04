package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.agregates.request.RequestAlumno;
import com.example.gestion_alumnos.agregates.request.RequestCurso;
import com.example.gestion_alumnos.dao.AlumnoDao;
import org.springframework.stereotype.Service;

@Service
public class AlumnoValidate {
    private final AlumnoDao alumnoDao;

    public AlumnoValidate(AlumnoDao alumnoDao) {
        this.alumnoDao = alumnoDao;
    }


    public  boolean validarAlumno(RequestAlumno requestAlumno)
    {

        return alumnoDao.existsByDni(requestAlumno.getDni());
    }

    public  boolean validarAlumnoUpdate(RequestAlumno requestAlumno)
    {
        return isNullorEmpty(requestAlumno.getNombres()) || isNullorEmpty(requestAlumno.getApellidos()) || alumnoDao.existsByDni(requestAlumno.getDni());
    }


    public boolean isNullorEmpty (String valor)
    {
        return  valor ==null || valor.isEmpty();
    }


}
