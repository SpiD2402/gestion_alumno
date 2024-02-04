package com.example.gestion_alumnos.dao;

import com.example.gestion_alumnos.entity.Notas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotaDao extends JpaRepository<Notas,Long> {

    @Query("SELECT CASE WHEN COUNT(n) > 0 THEN true ELSE false END FROM Notas n WHERE n.curso.cursoId = :idCurso AND n.alumno.idAlumno = :idAlumno")
    boolean existsByCursoIdAndAlumnoId(@Param("idCurso") Long idCurso, @Param("idAlumno") Long idAlumno);

}
