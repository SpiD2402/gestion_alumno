package com.example.gestion_alumnos.entity;


import com.example.gestion_alumnos.audit.Audit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "profesores")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profesor  extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_profesor")
    private  Long idProfesor;

    private String nombres;

    private String apellidos;

    private String dni;

    private int status;

    @OneToMany
    @JoinTable(
            name = "asignaciones",
            joinColumns =@JoinColumn(name = "id_profesor"),
            inverseJoinColumns =@JoinColumn(name = "id_curso")
    )
    private Set<Curso> cursos= new HashSet<>();

}
