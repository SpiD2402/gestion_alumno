package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.ProfesorDao;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.entity.Profesor;
import com.example.gestion_alumnos.feignClient.ReniecClient;
import com.example.gestion_alumnos.util.ProfesorValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProfesorServiceImplTest {


    @Mock
  ProfesorDao profesorDao;
    @Mock
     ReniecClient reniecClient;
    @Mock
     ProfesorValidate profesorValidate;

    @InjectMocks
    ProfesorServiceImpl profesorService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        profesorService = new ProfesorServiceImpl(profesorDao,reniecClient,profesorValidate);
    }

    @Test
    void findAllProfesor() {

        Profesor profesor = new Profesor(1l,"Piero","Ramirez","74779354",1, new HashSet<>());
        Profesor profesor2 = new Profesor(1l,"Piero","Ramirez","74779354",1, new HashSet<>());

        List<Profesor> list = List.of(profesor,profesor2);
        Mockito.when(profesorDao.findAll((Sort) Mockito.any())).thenReturn(list);


        ResponseBase responseBueno = profesorService.findAllProfesor();

        assertNotNull(responseBueno.getData());
    }

    @Test
    void findByIdProfesor() {
        Long id = 1l;
        Profesor profesor = new Profesor(1l,"Piero","Ramirez","74779354",1, new HashSet<>());
        Mockito.when(profesorDao.findById(Mockito.any())).thenReturn(Optional.of(profesor));

        ResponseBase responseBase2 = profesorService.findByIdProfesor(id);
        ResponseBase responseBase = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(profesor));


        assertEquals(responseBase2.getData(),responseBase.getData());

    }


    @Test
    void deleteProfesor() {
        Long id =1l;
        Profesor profesor = new Profesor(1l,"Piero","Ramirez","74779354",1, new HashSet<>());
        profesor.setStatus(0);
        Mockito.when(profesorDao.findById(Mockito.any())).thenReturn(Optional.of(profesor));

        Mockito.when(profesorDao.save(Mockito.any())).thenReturn(profesor);

        ResponseBase responseBase= profesorService.deleteProfesor(id);
        ResponseBase responseBase1 =new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of("Profesor se Deshabilito"));

        assertEquals(responseBase1.getData(),responseBase.getData());

    }

    @Test
    void timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Timestamp timestamp1 = profesorService.timestamp();

        assertEquals(timestamp1.getTime(),timestamp.getTime());


    }
}