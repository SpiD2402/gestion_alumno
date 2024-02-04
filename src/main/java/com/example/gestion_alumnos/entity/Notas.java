package com.example.gestion_alumnos.entity;

import com.example.gestion_alumnos.audit.Audit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "notas")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notas extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Long notaId;

    private double nota1;
    private double nota2;
    private double nota3;
    private double  promedio;
    @JsonIgnore
    private  int status;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_alumno")
    private Alumno alumno;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_curso")
    private Curso curso;


}
