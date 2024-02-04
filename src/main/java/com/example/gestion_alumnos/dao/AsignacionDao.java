package com.example.gestion_alumnos.dao;

import com.example.gestion_alumnos.entity.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AsignacionDao extends JpaRepository<Asignacion,Long> {

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Asignacion a WHERE a.cursoId = :cursoId AND a.profesorId = :profesorId")
    boolean existsByCursoIdAndProfesorId(@Param("cursoId") Long cursoId, @Param("profesorId") Long profesorId);
}
