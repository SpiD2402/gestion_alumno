package com.example.gestion_alumnos.entity;

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
@Table(name = "asignaciones")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asignacion")
    private Long asignacionId;


    @OneToOne
    @JoinColumn(name = "id_profesor")
    private Profesor  profesorId;


    @OneToOne
    @JoinColumn(name = "id_curso")
    private Curso cursoId;

    private  int status;

}
