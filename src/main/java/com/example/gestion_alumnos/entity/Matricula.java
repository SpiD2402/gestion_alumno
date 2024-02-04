package com.example.gestion_alumnos.entity;

import com.example.gestion_alumnos.audit.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "matriculas")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Matricula  extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_matricula")
    private Long idMatricula;

    private String codMatricula;

    @Column(name = "id_alumno")
    private Long idAlumno;

    @Column(name = "id_curso")
    private  Long idCurso;

    @Column(name = "fecha_matricula")
    private Timestamp fechaMatricula;

    private int  status;


}
