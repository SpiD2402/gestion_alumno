package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestAsignacion;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.AsignacionDao;
import com.example.gestion_alumnos.dao.CursoDao;
import com.example.gestion_alumnos.dao.ProfesorDao;
import com.example.gestion_alumnos.entity.Asignacion;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.entity.Profesor;
import com.example.gestion_alumnos.service.AsignacionService;
import com.example.gestion_alumnos.util.AsignacionValidate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsignacionServiceImpl  implements AsignacionService {

    private final AsignacionDao asignacionDao;

    private final AsignacionValidate asignacionValidate;

    private  final ProfesorDao profesorDao;

    private final CursoDao cursoDao;

    public AsignacionServiceImpl(AsignacionDao asignacionDao, AsignacionValidate asignacionValidate, ProfesorDao profesorDao, CursoDao cursoDao) {
        this.asignacionDao = asignacionDao;
        this.asignacionValidate = asignacionValidate;
        this.profesorDao = profesorDao;
        this.cursoDao = cursoDao;
    }

    @Override
    public ResponseBase findAllAsignaciones() {
        List<Asignacion> asignacionsAll = asignacionDao.findAll().stream().filter(asig -> asig.getStatus() == 1).collect(Collectors.toList());
        Optional<List<Asignacion>> oAsignaciones= Optional.of(asignacionsAll);

        if (oAsignaciones.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, oAsignaciones);
        }
        else {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR, Optional.empty());
        }
    }

    @Override
    public ResponseBase findByIdAsignacion(Long id) {
        Optional<Asignacion> findByAsig = asignacionDao.findById(id);
        if (findByAsig.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,findByAsig );
        }
        else {
            return new ResponseBase(Constants.CODE_ERROR_EXIST,Constants.MESS_NON_DATA,Optional.empty());
        }
    }

    @Override
    public ResponseBase updateAsignacion(RequestAsignacion asignacion, Long id) {



        return null;
    }

    @Override
    public ResponseBase addAsignacion(RequestAsignacion asignacion) {
        boolean bolAsig = asignacionValidate.validatAsignacion(asignacion);
        if (bolAsig)
        {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.of("Error en la validacion") );

        }
        else {
            Asignacion asignacion1 = getAsignacion(asignacion,new Asignacion());
            asignacionDao.save(asignacion1);
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(asignacion1));
        }

    }

    @Override
    public ResponseBase deleteAsignacion(Long id) {
        Optional<Asignacion> oAsignacion = asignacionDao.findById(id);
        if(oAsignacion.isPresent())
        {
            Asignacion asignacion = oAsignacion.get();
            asignacion.setStatus(0);
            asignacionDao.save(asignacion);
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Eliminacion Exitosa"));
        }
        else {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.empty() );

        }

    }

    public Asignacion getAsignacion(RequestAsignacion requestAsignacion,Asignacion asignacion )
    {
        Optional<Profesor>profesor = profesorDao.findById(requestAsignacion.getIdProfesor());
        Optional<Curso> curso = cursoDao.findById(requestAsignacion.getIdCurso());

        asignacion.setStatus(1);
        asignacion.setProfesorId(profesor.get());
        asignacion.setCursoId(curso.get());
        return  asignacion;
    }


}
