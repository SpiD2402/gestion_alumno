package com.example.gestion_alumnos.entity;

import com.example.gestion_alumnos.audit.Audit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "cursos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Curso extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long cursoId;
    private String nombre;

    @JsonIgnore
    private int status;

    @JsonIgnore
    @OneToMany
    @JoinTable(
            name = "notas",
            joinColumns =@JoinColumn(name = "id_curso"),
            inverseJoinColumns =@JoinColumn(name = "id_alumno")
    )
    private Set<Alumno>alumno= new HashSet<>();





}
