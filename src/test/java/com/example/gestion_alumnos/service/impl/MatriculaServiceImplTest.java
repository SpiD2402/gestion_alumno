package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.AlumnoDao;
import com.example.gestion_alumnos.dao.MatriculaDao;
import com.example.gestion_alumnos.entity.Matricula;
import com.example.gestion_alumnos.util.MatriculaValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MatriculaServiceImplTest {
    @Mock
  AlumnoDao alumnoDao;

    @Mock
     MatriculaDao matriculaDao;
    @Mock
 MatriculaValidate matriculaValidate;

    @InjectMocks
    MatriculaServiceImpl matriculaService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        matriculaService = new MatriculaServiceImpl(alumnoDao,matriculaDao,matriculaValidate);
    }


    @Test
    void findAllMatricula() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Matricula matricula = new Matricula(1l,"Ram74750",1l,1l,timestamp,1);
        Matricula matricula2 = new Matricula(1l,"Ram74750",1l,1l,timestamp,1);

        List<Matricula> matriculas =List.of(matricula,matricula2);

        Mockito.when(matriculaDao.findAll((Sort) Mockito.any())).thenReturn(matriculas);

        ResponseBase responseExitoso =new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(matriculas));
        ResponseBase responseService = matriculaService.findAllMatricula();

        assertNotNull(responseExitoso.getData());
        assertNotNull(responseService.getData());


    }

    @Test
    void findByIdMatricula() {
        Long id = 1l;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Matricula matricula  = new Matricula(1l,"Ram74750",1l,1l,timestamp,1);

        Mockito.when(matriculaDao.findById(Mockito.any())).thenReturn(Optional.of(matricula));

        ResponseBase responseExitoso = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(matricula));
        ResponseBase responseService = matriculaService.findByIdMatricula(id);

        assertEquals(responseExitoso.getData(),responseService.getData());

    }


    @Test
    void deleteMatricula() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long id = 1l;
        Matricula matricula= new Matricula(1l,"Ram74750",1l,1l,timestamp,1);
        matricula.setStatus(0);
        Mockito.when(matriculaDao.findById(Mockito.any())).thenReturn(Optional.of(matricula));

        Mockito.when(matriculaDao.save(Mockito.any())).thenReturn(matricula);

        ResponseBase responseBueno = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of("Se deshabilito exitoso"));
        ResponseBase responseService = matriculaService.deleteMatricula(id);

        assertEquals(responseBueno.getData().get(),responseService.getData().get());


    }

    @Test
    void timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Timestamp timestamp1 = matriculaService.timestamp();

        assertEquals(timestamp1.getTime(),timestamp.getTime());


    }

}