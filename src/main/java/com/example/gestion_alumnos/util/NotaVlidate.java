package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.agregates.request.RequestNota;
import com.example.gestion_alumnos.dao.AlumnoDao;
import com.example.gestion_alumnos.dao.CursoDao;
import com.example.gestion_alumnos.dao.NotaDao;
import org.springframework.stereotype.Service;

@Service
public class NotaVlidate {
    private final NotaDao notaDao;


    public NotaVlidate(NotaDao notaDao) {
        this.notaDao = notaDao;

    }


    public  boolean validarNota(RequestNota requestNota)
    {
        return isNullorEmpty(String.valueOf(requestNota.getNota1()))
                || isNullorEmpty(String.valueOf(requestNota.getNota2()))
                || isNullorEmpty(String.valueOf(requestNota.getNota3())) || isNullorEmpty(String.valueOf(requestNota.getIdCurso()))
                || isNullorEmpty(String.valueOf(requestNota.getIdAlumno()))
                || notaDao.existsByCursoIdAndAlumnoId(requestNota.getIdCurso(), requestNota.getIdAlumno()
        );
    }




    public boolean isNullorEmpty (String valor)
    {
        return  valor ==null || valor.isEmpty();
    }

}
