package com.example.gestion_alumnos.dao;

import com.example.gestion_alumnos.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlumnoDao extends JpaRepository<Alumno,Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Alumno a WHERE a.dni = :dni")
    boolean existsByDni(@Param("dni") String dni);
}
