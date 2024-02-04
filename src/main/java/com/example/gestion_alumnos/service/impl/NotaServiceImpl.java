package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestNota;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.*;
import com.example.gestion_alumnos.entity.Alumno;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.entity.Notas;
import com.example.gestion_alumnos.service.NotaService;
import com.example.gestion_alumnos.util.NotaVlidate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotaServiceImpl  implements NotaService {

    private final NotaDao notaDao;
    private  final CursoDao cursoDao;

    private final MatriculaDao matriculaDao;
    private final AlumnoDao alumnoDao;

    private final NotaVlidate notaVlidate;
    public NotaServiceImpl(NotaDao notaDao, CursoDao cursoDao, MatriculaDao matriculaDao, AlumnoDao alumnoDao, NotaVlidate notaVlidate) {
        this.notaDao = notaDao;
        this.cursoDao = cursoDao;
        this.matriculaDao = matriculaDao;
        this.alumnoDao = alumnoDao;
        this.notaVlidate = notaVlidate;
    }

    @Override
    public ResponseBase findAllNotas() {
        List<Notas> notasAll =  notaDao.findAll().stream().filter(nota -> nota.getStatus() ==1).collect(Collectors.toList());
        Optional<List<Notas>> oNotas = Optional.of(notasAll);
        if (oNotas.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,oNotas);
        }
        else{
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ZERO_ROWS,Optional.empty());

        }
    }

    @Override
    public ResponseBase findByIdNota(Long id) {

        Optional<Notas> nota = notaDao.findById(id);
        if (nota.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,nota);
        }
        return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_NON_DATA,Optional.empty());


    }

    @Override
    public ResponseBase updateNota(RequestNota nota, Long id) {
        return null;
    }

    @Override
    public ResponseBase addNota(RequestNota nota) {

        boolean vaidateNota =notaVlidate.validarNota(nota);
        boolean  existMatricula = matriculaDao.existsByAlumnoIdAndCursoId(nota.getIdAlumno(), nota.getIdCurso());
        if (!vaidateNota) {
                if (existMatricula) {
                    Notas notas = getNotas(nota, false, new Notas());
                    notaDao.save(notas);
                    return new ResponseBase(Constants.CODE_SUCCESS, Constants.MESS_SUCCESS, Optional.of(notas));
                } else {
                    return new ResponseBase(Constants.CODE_ERROR_DATA_NOT, Constants.MESS_NON_DATA, Optional.of("No existe matricula entre el alumno y el curso"));

                }
        }
        else {
            return   new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_NON_DATA,Optional.of("Error en la validacion"));

        }
    }

    @Override
    public ResponseBase deleteNota(Long id) {
        return null;
    }
    public Timestamp timestamp ()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public Notas getNotas(RequestNota requestNota,boolean isUpdate ,Notas notas)
    {

        Optional<Curso> curso = cursoDao.findById(requestNota.getIdCurso());
        Optional<Alumno> alumno = alumnoDao.findById(requestNota.getIdAlumno());

        double promedio = (requestNota.getNota1()+ requestNota.getNota2()+ requestNota.getNota3()) /3;
        notas.setAlumno(alumno.get());
        notas.setCurso(curso.get());
        notas.setNota1(requestNota.getNota1());
        notas.setNota2(requestNota.getNota2());
        notas.setNota3(requestNota.getNota3());
        notas.setPromedio(promedio);
        if (isUpdate)
        {
            notas.setDateModif(timestamp());
            notas.setUserModif("User");
        }
        else {

            notas.setDateCreate(timestamp());
            notas.setUserCreate("User");
            notas.setStatus(1);
        }
        return  notas;
    }

}









