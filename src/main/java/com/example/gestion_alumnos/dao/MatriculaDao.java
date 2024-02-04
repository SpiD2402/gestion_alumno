package com.example.gestion_alumnos.dao;

import com.example.gestion_alumnos.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatriculaDao extends JpaRepository<Matricula,Long> {

    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Matricula m WHERE m.idAlumno = :idAlumno AND m.idCurso = :idCurso")
    boolean existsByAlumnoIdAndCursoId(@Param("idAlumno") Long idAlumno, @Param("idCurso") Long idCurso);

}
