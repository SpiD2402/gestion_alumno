package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.agregates.request.RequestCurso;
import com.example.gestion_alumnos.dao.CursoDao;
import org.springframework.stereotype.Service;

@Service
public class CursoValidate {

    private final CursoDao cursoDao;

    public CursoValidate(CursoDao cursoDao) {
        this.cursoDao = cursoDao;
    }

    public  boolean validarCurso(RequestCurso requestCurso)
    {


        if (isNullorEmpty(requestCurso.getNombre()))
        {
            return  true;
        }

        return cursoDao.existsByNombre(requestCurso.getNombre());
    }




    public boolean isNullorEmpty (String valor)
    {
        return  valor ==null || valor.isEmpty();
    }

}
