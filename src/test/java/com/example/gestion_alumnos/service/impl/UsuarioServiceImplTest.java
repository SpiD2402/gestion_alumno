package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.UserDao;
import com.example.gestion_alumnos.entity.Usuario;
import com.example.gestion_alumnos.security.CustomerDetailService;
import com.example.gestion_alumnos.security.jwt.JwtUtil;
import com.example.gestion_alumnos.util.UserValidate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceImplTest {

    @Mock
    UserDao userDao;
    @Mock
 AuthenticationManager authenticationManager;

    @Mock
    CustomerDetailService customerDetailService;
    @Mock
   PasswordEncoder passwordEncoder;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    UserValidate userValidate;

    @InjectMocks
    UsuarioServiceImpl usuarioService;


    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioServiceImpl(userDao,authenticationManager,customerDetailService,jwtUtil);

    }


    @Test
    void findAllUser() {

        Usuario usuario = new Usuario(1l,"piero1235545@gmail.com","1235545","admin",1);
        Usuario usuario2 = new Usuario(1l,"piero1235545@gmail.com","1235545","admin",1);
        List<Usuario> usuarios = List.of(usuario2,usuario);
        Mockito.when(userDao.findAll((Sort) Mockito.any())).thenReturn(usuarios);
        ResponseBase responseExitosa = new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS, Optional.of(usuarios));
        ResponseBase responseService = usuarioService.findAllUser();
        assertNotNull(responseExitosa.getData());
        assertNotNull(responseService.getData());

    }

    @Test
    void findByIdUser() {
        Long id = 1l;
        Usuario usuario = new Usuario(1l,"piero1235545@gmail.com","1235545","admin",1);
        Mockito.when(userDao.findById(Mockito.any())).thenReturn(Optional.of(usuario));
        ResponseBase responseExitosa =  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(usuario));
        ResponseBase responseService = usuarioService.findByIdUser(id);

        assertEquals(responseExitosa.getData().get(),responseService.getData().get());

    }



    @Test
    void deleteUser() {
        Long id = 1l;
        Usuario usuario = new Usuario(1l,"piero1235545@gmail.com","1235545","admin",1);
        usuario.setStatus(0);
        Mockito.when(userDao.findById(Mockito.any())).thenReturn(Optional.of(usuario));
        Mockito.when(userDao.save(Mockito.any())).thenReturn(usuario);
        ResponseBase responseExitoso =new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Usuario Deshabilitado exitosamente"));
        ResponseBase responseService =  usuarioService.deleteUser(id);

        assertEquals(responseExitoso.getData().get(),responseService.getData().get());

    }

    @Test
    void timestamp() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Timestamp timestamp1 = usuarioService.timestamp();

        assertEquals(timestamp1.getTime(),timestamp.getTime());


    }


}