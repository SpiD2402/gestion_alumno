package com.example.gestion_alumnos.entity;

import com.example.gestion_alumnos.audit.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "alumnos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Alumno  extends Audit implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alumno")
    private Long idAlumno;
    private String nombres;
    private String apellidos;
    private  String dni;
    private int status;

    @OneToMany
    @JoinTable(
            name = "notas",
            joinColumns =@JoinColumn(name = "id_alumno"),
            inverseJoinColumns =@JoinColumn(name = "id_curso")
    )
    private Set<Curso>cursos= new HashSet<>();


    @OneToMany
    @JoinTable(
            name = "notas",
            joinColumns =@JoinColumn(name = "id_alumno"),
            inverseJoinColumns =@JoinColumn(name = "id_curso")
    )
    private Set<Notas>notas= new HashSet<>();



}
