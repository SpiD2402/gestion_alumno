package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.AsignacionDao;
import com.example.gestion_alumnos.dao.CursoDao;
import com.example.gestion_alumnos.dao.ProfesorDao;
import com.example.gestion_alumnos.entity.Asignacion;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.entity.Profesor;
import com.example.gestion_alumnos.util.AsignacionValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AsignacionServiceImplTest {

    @Mock
    AsignacionDao asignacionDao;

    @Mock
    AsignacionValidate asignacionValidate;

    @Mock
    ProfesorDao profesorDao;

    @Mock
    CursoDao cursoDao;

    @InjectMocks
    AsignacionServiceImpl asignacionService;

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        asignacionService = new AsignacionServiceImpl(asignacionDao,asignacionValidate,profesorDao,cursoDao);
    }


    @Test
    void findAllAsignaciones() {

        Asignacion asignacion = new Asignacion(1l,new Profesor(),new Curso(),1);
        Asignacion asignacion2 = new Asignacion(1l,new Profesor(),new Curso(),1);

        List<Asignacion> asignacions = List.of(asignacion,asignacion2);
        ResponseBase responseBase = asignacionService.findAllAsignaciones();
        ResponseBase responseBaseBueno =new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(asignacions) );
        assertNotNull(responseBaseBueno.getData());

    }

    @Test
    void findByIdAsignacion() {

        Long id = 1l;
        Optional asignacion = Optional.of(new Asignacion(1l, new Profesor(), new Curso(), 1));
        Mockito.when(cursoDao.findById(Mockito.any())).thenReturn(asignacion);

        ResponseBase responseBase = asignacionService.findByIdAsignacion(id);
        ResponseBase responseBaseBueno = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,asignacion );
        assertNotNull(responseBaseBueno.getData());
        assertEquals(responseBaseBueno.getData(),responseBaseBueno.getData());
    }



    @Test
    void deleteAsignacion() {

        Long id = 1l;
        Optional asignacion = Optional.of(new Asignacion(1l, new Profesor(), new Curso(), 1));
        Mockito.when(asignacionDao.findById(Mockito.any())).thenReturn(asignacion);
        Asignacion asignacion1 = (Asignacion) asignacion.get();
        asignacion1.setStatus(0);
        Mockito.when(asignacionDao.save(Mockito.any())).thenReturn(asignacion1);

        ResponseBase responseBase = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Eliminacion Exitosa"));
        ResponseBase responseService = asignacionService.deleteAsignacion(id);

        assertEquals(responseBase.getData().get(),responseService.getData().get());

    }


}