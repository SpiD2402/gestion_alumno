package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.config.RedisService;
import com.example.gestion_alumnos.dao.AlumnoDao;
import com.example.gestion_alumnos.entity.Alumno;
import com.example.gestion_alumnos.feignClient.ReniecClient;
import com.example.gestion_alumnos.util.AlumnoValidate;
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

import static org.junit.jupiter.api.Assertions.*;

class AlumnoServiceImplTest {

    @Mock

   AlumnoValidate alumnoValidate;
    @Mock
 AlumnoDao alumnoDao;
    @Mock
   ReniecClient reniecClient;

    @Mock
     RedisService redisService;

    @InjectMocks
    AlumnoServiceImpl alumnoService;


    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        alumnoService = new AlumnoServiceImpl(alumnoValidate,alumnoDao,reniecClient,redisService);
    }

    @Test
    void findAllStudent() {
        Alumno alumno = new Alumno(1l,"piero","ramirez","74737935",1,new HashSet<>(),new HashSet<>());
        Alumno alumno2 = new Alumno(1l,"piero","ramirez","74737935",1,new HashSet<>(),new HashSet<>());
        List<Alumno> alumnos= List.of(alumno2,alumno);

        Mockito.when(alumnoDao.findAll((Sort) Mockito.any())).thenReturn(alumnos);
        ResponseBase responseExitosa = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(alumnos));
        ResponseBase responseService = alumnoService.findAllStudent();

        assertNotNull(responseExitosa.getData());

    }



    @Test
    void deleteStudent() {
        Long id = 1l;
        Alumno alumno = new Alumno(1l,"piero","ramirez","74737935",1,new HashSet<>(),new HashSet<>());
        alumno.setStatus(0);
        Mockito.when(alumnoDao.findById(Mockito.any())).thenReturn(Optional.of(alumno));
        Mockito.when(alumnoDao.save(Mockito.any())).thenReturn(alumno);
        ResponseBase responseExitoso =new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Alumno Deshabilitado exitosamente"));
        ResponseBase responseService = alumnoService.deleteStudent(id);

        assertEquals(responseExitoso.getData().get(),responseService.getData().get());


    }

    @Test
    void timestamp() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Timestamp timestamp1 = alumnoService.timestamp();

        assertEquals(timestamp1.getTime(),timestamp.getTime());

    }

}