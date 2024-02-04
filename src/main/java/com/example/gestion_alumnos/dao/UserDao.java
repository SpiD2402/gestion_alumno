package com.example.gestion_alumnos.dao;

import com.example.gestion_alumnos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao  extends JpaRepository<Usuario,Long> {

    Usuario findByEmail(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.email = :email")
    boolean existByEmail(@Param("email") String email);



}
