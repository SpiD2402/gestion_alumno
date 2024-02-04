package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestProfesor;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.agregates.response.ResponseReniec;
import com.example.gestion_alumnos.dao.ProfesorDao;
import com.example.gestion_alumnos.entity.Profesor;
import com.example.gestion_alumnos.feignClient.ReniecClient;
import com.example.gestion_alumnos.service.ProfesorService;
import com.example.gestion_alumnos.util.ProfesorValidate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorDao profesorDao;
    private final ReniecClient reniecClient;

    private final ProfesorValidate profesorValidate;

    public ProfesorServiceImpl(ProfesorDao profesorDao, ReniecClient reniecClient, ProfesorValidate profesorValidate) {
        this.profesorDao = profesorDao;
        this.reniecClient = reniecClient;
        this.profesorValidate = profesorValidate;
    }

    @Value("${token.api.reniec}")
    private String tokenReniec;
    @Override
    public ResponseBase findAllProfesor() {
        List<Profesor> profesors =  profesorDao.findAll().stream().filter(user -> user.getStatus() ==1).collect(Collectors.toList());
        Optional<List<Profesor>> oProfesor = Optional.of(profesors);
        if (oProfesor.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,oProfesor);
        }
        else{
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ZERO_ROWS,Optional.empty());

        }
    }

    @Override
    public ResponseBase findByIdProfesor(Long id) {
        Optional<Profesor> profesor = profesorDao.findById(id);
        if (profesor.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,profesor);
        }
        return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_NON_DATA,Optional.empty());

    }

    @Override
    public ResponseBase updateProfesor(RequestProfesor profesor, Long id) {
        return null;
    }

    @Override
    public ResponseBase addProfesor(RequestProfesor profesor) {
      boolean valdateProfesor= profesorValidate.validarProfesor(profesor);
      if (!valdateProfesor)
      {
          Profesor profesorNew = getReniecProfesor(profesor);
          profesorDao.save(profesorNew);

          return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(profesorNew));

      }
        return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.empty());


    }

    @Override
    public ResponseBase deleteProfesor(Long id) {
        Optional<Profesor> profesor = profesorDao.findById(id);
        if (profesor.isPresent())
        {
         Profesor profesor1 =  profesor.get();
         profesor1.setStatus(0);
         profesorDao.save(profesor1);
         return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of("Profesor se Deshabilito"));
        }
        else {
            return  new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.empty());

        }
    }
    public Timestamp timestamp ()
    {
        return new Timestamp(System.currentTimeMillis());
    }


    private Profesor getReniecProfesor(RequestProfesor requestProfesor)
    {
        ResponseReniec reniec  = getExecutionReniec(requestProfesor.getDni());
        Profesor profesor = new Profesor();
        if (reniec!=null)
        {
            profesor.setDni(reniec.getNumeroDocumento());
            profesor.setNombres(reniec.getNombres());
            profesor.setApellidos(reniec.getApellidoPaterno()+" "+reniec.getApellidoMaterno());

        }
        else {
            return null;
        }
        return getProfesor(requestProfesor,false,profesor);
    }

    private ResponseReniec getExecutionReniec(String numero)
    {
        String authorization = "Bearer "+ tokenReniec;
        return reniecClient.getInfoReniec(numero,authorization);
    }

    private Profesor getProfesor (RequestProfesor requestProfesor,boolean isUpdate,Profesor profesor)
    {
        if (isUpdate)
        {
            profesor.setDateModif(timestamp());
            profesor.setUserModif("User");
            profesor.setStatus(1);
        }
        else {
            profesor.setDateCreate(timestamp());
            profesor.setUserCreate("User");
            profesor.setStatus(1);
        }

        return  profesor;

    }


}
