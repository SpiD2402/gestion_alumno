package com.example.gestion_alumnos.dao;

import com.example.gestion_alumnos.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesorDao extends JpaRepository<Profesor,Long> {

    @Query("SELECT COUNT(p) > 0 FROM Profesor p WHERE p.dni = :dni")
    boolean existsByDni(@Param("dni") String dni);
}