package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.AlumnoDao;
import com.example.gestion_alumnos.dao.CursoDao;
import com.example.gestion_alumnos.dao.MatriculaDao;
import com.example.gestion_alumnos.dao.NotaDao;
import com.example.gestion_alumnos.entity.Alumno;
import com.example.gestion_alumnos.entity.Curso;
import com.example.gestion_alumnos.entity.Notas;
import com.example.gestion_alumnos.util.NotaVlidate;
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

class NotaServiceImplTest {

@Mock
 NotaDao notaDao;

@Mock
CursoDao cursoDao;

@Mock
MatriculaDao matriculaDao;
@Mock
AlumnoDao alumnoDao;

@Mock
NotaVlidate notaVlidate;

@InjectMocks
NotaServiceImpl notaService;

@BeforeEach
void setUp()
{
    MockitoAnnotations.openMocks(this);
    notaService = new NotaServiceImpl(notaDao,cursoDao,matriculaDao,alumnoDao,notaVlidate);
}

    @Test
    void findAllNotas() {
        Notas notas = new Notas(1l,14.0,15.0,16.0,18.0,1,new Alumno(),new Curso());
        Notas notas2 = new Notas(1l,14.0,15.0,16.0,18.0,1,new Alumno(),new Curso());
        List<Notas> notas1 = List.of(notas,notas2);
        Mockito.when(notaDao.findAll((Sort) Mockito.any())).thenReturn(notas1);
        ResponseBase responseExitoso = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(notas1));
        ResponseBase responseService = notaService.findAllNotas();

        assertNotNull(responseExitoso.getData());
        assertNotNull(responseService.getData());

    }

    @Test
    void findByIdNota() {
    Long id = 1l;
    Notas notas = new Notas(1l,14.0,15.0,16.0,18.0,1,new Alumno(),new Curso());
    Mockito.when(notaDao.findById(Mockito.any())).thenReturn(Optional.of(notas));
    ResponseBase responseExitosa = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(notas));
    ResponseBase responseService = notaService.findByIdNota(id);
    assertEquals(responseExitosa.getData().get(),responseService.getData().get());

    }



    @Test
    void timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Timestamp timestamp1 = notaService.timestamp();

        assertEquals(timestamp1.getTime(),timestamp.getTime());


    }


}