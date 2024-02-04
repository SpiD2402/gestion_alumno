package com.example.gestion_alumnos.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;


@Getter
@Setter
@MappedSuperclass
public class Audit {
    @Column(name = "user_create", length = 45,nullable = true)
    private String userCreate;

    @Column(name = "user_modif", length = 45,nullable = true)
    private String userModif;

    @Column(name = "user_delete", length = 45,nullable = true)
    private String userDelete;

    @Column(name = "date_modif")
    private Timestamp dateModif;

    @Column(name = "date_delete")
    private Timestamp dateDelete;

    @Column(name = "date_create")
    private Timestamp dateCreate;
}
