package com.example.gestion_alumnos.security;

import com.example.gestion_alumnos.dao.UserDao;
import com.example.gestion_alumnos.entity.Usuario;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomerDetailService  implements UserDetailsService {


    @Autowired
    private UserDao userDao;

    @Getter
    private Usuario usuario;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        usuario = userDao.findByEmail(username);
        if (usuario!= null)
        {
            return  new User(usuario.getEmail(),usuario.getPassword(),new ArrayList<>());

        }
        else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

    }


}
