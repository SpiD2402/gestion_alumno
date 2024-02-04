package com.example.gestion_alumnos.util;


import com.example.gestion_alumnos.agregates.request.RequestMatricula;
import com.example.gestion_alumnos.dao.MatriculaDao;
import org.springframework.stereotype.Service;

@Service
public class MatriculaValidate {

    private final MatriculaDao matriculaDao;

    public MatriculaValidate(MatriculaDao matriculaDao) {
        this.matriculaDao = matriculaDao;
    }

    public  boolean validarMatricula(RequestMatricula requestMatricula)
    {
        if (isNullorEmpty(String.valueOf(requestMatricula.getIdCurso())) || isNullorEmpty(String.valueOf(requestMatricula.getIdAlumno())))
        {
            return  true;
        }

        return matriculaDao.existsByAlumnoIdAndCursoId(requestMatricula.getIdAlumno(), requestMatricula.getIdCurso());
    }




    public boolean isNullorEmpty (String valor)
    {
        return  valor ==null || valor.isEmpty();
    }

}
