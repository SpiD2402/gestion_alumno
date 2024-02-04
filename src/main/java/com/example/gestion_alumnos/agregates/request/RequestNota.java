package com.example.gestion_alumnos.agregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestNota {

    private Long idAlumno;
    private Long idCurso;
    private double nota1;
    private double nota2;
    private double nota3;
}
