package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.CursoDao;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.util.CursoValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CursoServiceImplTest {

    @Mock
    CursoValidate cursoValidate;

    @Mock
    CursoDao cursoDao;


    @InjectMocks
    CursoServiceImpl cursoServiceImpl;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        cursoServiceImpl = new CursoServiceImpl(cursoValidate,cursoDao);

    }

    @Test
    void findAllCourses() {

        Curso curso =new Curso(1l,"Piero",1,new HashSet<>());
        Curso curso2 =new Curso(1l,"Piero",1,new HashSet<>());
        List<Curso> cursos = List.of(curso,curso2);
        Mockito.when(cursoDao.findAll((Sort) Mockito.any())).thenReturn(cursos);

        ResponseBase responseBase = cursoServiceImpl.findAllCourses();

        ResponseBase responseBueno =  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(cursos));

        assertNotNull(responseBueno);
        assertNotNull(responseBueno.getData());

    }


    @Test
    void findByIdCourse() {
        Curso curso =new Curso(1l,"Piero",1,new HashSet<>());
        Long id = 1l;
        curso.setStatus(0);
        Mockito.when(cursoDao.findById(Mockito.any())).thenReturn(Optional.of(curso));

        ResponseBase responseExitoso = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(curso));
        ResponseBase responseService = cursoServiceImpl.findByIdCourse(id);

        assertEquals(responseExitoso.getData().get(),responseExitoso.getData().get());


    }



    @Test
    void deleteCurse() {
        Curso curso =new Curso(1l,"Piero",1,new HashSet<>());
        Long id = 1l;
        curso.setStatus(0);
        Mockito.when(cursoDao.findById(Mockito.any())).thenReturn(Optional.of(curso));
        Mockito.when(cursoDao.save(Mockito.any())).thenReturn(curso);

        ResponseBase responseBueno = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Curso Deshabilitado exitosamente"));
        ResponseBase responseService = cursoServiceImpl.deleteCurse(id);

        assertEquals(responseService.getData().get(),responseBueno.getData().get());


    }

    @Test
    void timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Timestamp timestamp1 = cursoServiceImpl.timestamp();

        assertEquals(timestamp1.getTime(),timestamp.getTime());


    }

    @Test
    void getCourse() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Curso curso = new Curso();
        curso.setNombre("piero");
        curso.setDateCreate(timestamp);
        curso.setDateModif(timestamp);
        curso.setUserModif("Piero");
        curso.setStatus(1);
        curso.setUserModif("Piero");

    }
}