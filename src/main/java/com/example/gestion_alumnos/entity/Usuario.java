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
@Table(name = "usuarios")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuario extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private  Long idUser;

    @Column(unique = true)
    private String email;


    @JsonIgnore
    @Column(name = "password_usuario")
    private String  password;
    private  String rol ;
    private  int status;



}
