package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestCurso;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.CursoDao;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.service.CursoService;
import com.example.gestion_alumnos.util.CursoValidate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl  implements CursoService {

    private final CursoValidate cursoValidate;
    private final CursoDao cursoDao;

    public CursoServiceImpl(CursoValidate cursoValidate, CursoDao cursoDao) {
        this.cursoValidate = cursoValidate;
        this.cursoDao = cursoDao;
    }

    @Override
    public ResponseBase findAllCourses() {

        List<Curso> cursoAll =  cursoDao.findAll().stream().filter(user -> user.getStatus() ==1).collect(Collectors.toList());
        Optional<List<Curso>> oCurso = Optional.of(cursoAll);
        if (oCurso.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,oCurso);
        }
        else{
                    return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ZERO_ROWS,Optional.empty());

        }

    }

    @Override
    public ResponseBase findByIdCourse(Long id) {
        Optional<Curso> curso = cursoDao.findById(id);
        if (curso.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,curso);
        }
        return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_NON_DATA,Optional.empty());

    }

    @Override
    public ResponseBase updateCourse(RequestCurso curso, Long id) {
        boolean exisCourse = cursoDao.existsById(id);
        if (exisCourse)
        {
            Optional<Curso> cursoEncontrado= cursoDao.findById(id);
            boolean valido = cursoValidate.validarCurso(curso);
            if (!valido)
            {
               Curso cursoEb = getCourse(curso,2,cursoEncontrado.get());
                cursoDao.save(cursoEb);
                return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(cursoEb));
            }
            else {
                return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.of("Campos null o vacios"));

            }
        }

        return  new ResponseBase(Constants.CODE_ERROR_EXIST,Constants.MESS_NON_DATA,Optional.empty());
    }

    @Override
    public ResponseBase addCourse(RequestCurso curso) {
        boolean validateCurso = cursoValidate.validarCurso(curso);
        if (!validateCurso)
        {
            Curso cursoNew = getCourse(curso,1,new Curso());
            cursoDao.save(cursoNew);
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(getCourse(curso,1,cursoNew)));
        }
        else{
                return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.empty());
        }
    }

    @Override
    public ResponseBase deleteCurse(Long id) {
        Optional<Curso> cursoEncntrado = cursoDao.findById(id);
        if (cursoEncntrado.isPresent())
        {
                Curso curso = cursoEncntrado.get();
                curso.setStatus(0);
                curso.setDateDelete(timestamp());
                curso.setUserDelete("User");
                cursoDao.save(curso);
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Curso Deshabilitado exitosamente"));

        }
        else {
            return new ResponseBase(Constants.CODE_ERROR_EXIST,Constants.MESS_NON_DATA,Optional.empty());

        }

    }

    public Timestamp timestamp ()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public Curso getCourse(RequestCurso requestCurso,int num,Curso curso)
    {
        curso.setNombre(requestCurso.getNombre());
        if (num == 1)
        {
            curso.setDateCreate(timestamp());
            curso.setUserCreate("User");
            curso.setStatus(1);
        }
        if (num ==2)
        {
            curso.setDateModif(timestamp());
            curso.setUserModif("User");
        }

        return  curso;
    }


}
