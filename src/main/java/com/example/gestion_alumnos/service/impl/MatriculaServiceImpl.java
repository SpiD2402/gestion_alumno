package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestMatricula;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.AlumnoDao;
import com.example.gestion_alumnos.dao.MatriculaDao;
import com.example.gestion_alumnos.entity.Alumno;
import com.example.gestion_alumnos.entity.Matricula;
import com.example.gestion_alumnos.service.MatriculaService;
import com.example.gestion_alumnos.util.MatriculaValidate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaServiceImpl implements MatriculaService {

    private final AlumnoDao alumnoDao;
    private  final MatriculaDao matriculaDao;

    private  final MatriculaValidate matriculaValidate;


    public MatriculaServiceImpl(AlumnoDao alumnoDao, MatriculaDao matriculaDao, MatriculaValidate matriculaValidate) {
        this.alumnoDao = alumnoDao;

        this.matriculaDao = matriculaDao;
        this.matriculaValidate = matriculaValidate;
    }

    @Override
    public ResponseBase findAllMatricula() {
        List<Matricula> matriculas = matriculaDao.findAll();
        Optional<List<Matricula>> oMat= Optional.of(matriculas);
        if (oMat.isPresent())
        {
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,oMat);
        }

        else{
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR, Optional.empty());

        }

    }

    @Override
    public ResponseBase findByIdMatricula(Long id) {
        Optional<Matricula> matricula = matriculaDao.findById(id);
        if (matricula.isPresent())
        {
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,matricula);
        }
        else {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR, Optional.empty());
        }

    }

    @Override
    public ResponseBase updateMatricula(RequestMatricula matricula, Long id) {
        return null;
    }

    @Override
    public ResponseBase addMatricula(RequestMatricula matricula) {
        boolean validarMatricula = matriculaValidate.validarMatricula(matricula);
        if (validarMatricula)
        {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR, Optional.empty());

        }
        else {
            Matricula matriculaNew = getMatricula(matricula,false,new Matricula());
            matriculaDao.save(matriculaNew);
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(matriculaNew));
        }

    }

    @Override
    public ResponseBase deleteMatricula(Long id) {
        Optional <Matricula> matricula = matriculaDao.findById(id);
        if (matricula.isPresent())
        {
            Matricula matricula1 = matricula.get();
            matricula1.setStatus(0);
            matriculaDao.save(matricula1);
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of("Se deshabilito exitoso"));

        }
        else {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR, Optional.empty());

        }
    }
    public Timestamp timestamp ()
    {
        return new Timestamp(System.currentTimeMillis());
    }


    public Matricula getMatricula(RequestMatricula requestMatricula,boolean isUpdate,Matricula matricula){

        Optional<Alumno> alumno = alumnoDao.findById(requestMatricula.getIdAlumno());
        String codMatricula = alumno.get().getApellidos().substring(0,3)+alumno.get().getDni();
        matricula.setIdAlumno(requestMatricula.getIdAlumno());
        matricula.setIdCurso(requestMatricula.getIdCurso());
        if (isUpdate)
        {
            matricula.setDateModif(timestamp());
            matricula.setUserModif("User");
            matricula.setStatus(1);
        }
        else {
            matricula.setFechaMatricula(timestamp());
            matricula.setDateCreate(timestamp());
            matricula.setUserCreate("User");
            matricula.setStatus(1);
            matricula.setCodMatricula(codMatricula);
        }

        return  matricula;
    }

}
