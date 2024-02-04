package com.example.gestion_alumnos.service.impl;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestUser;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.dao.UserDao;
import com.example.gestion_alumnos.entity.Usuario;
import com.example.gestion_alumnos.security.CustomerDetailService;
import com.example.gestion_alumnos.security.jwt.JwtUtil;
import com.example.gestion_alumnos.service.UsuarioService;
import com.example.gestion_alumnos.util.UserValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UserDao userDao;

    private final   AuthenticationManager authenticationManager;

    @Autowired
    private final   CustomerDetailService customerDetailService;

    @Autowired
    private  PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Autowired
    private  UserValidate userValidate;


    public UsuarioServiceImpl(UserDao userDao,  AuthenticationManager authenticationManager, CustomerDetailService customerDetailService, JwtUtil jwtUtil) {
        this.userDao = userDao;

        this.authenticationManager = authenticationManager;

        this.customerDetailService = customerDetailService;

        this.jwtUtil = jwtUtil;
    }


    @Transactional(readOnly = true)
    @Override
    public ResponseBase findAllUser() {

        List<Usuario> usuariosAll =  userDao.findAll().stream().filter(user -> user.getStatus() ==1).collect(Collectors.toList());
        Optional<List<Usuario>> oUser = Optional.of(usuariosAll);
        if (oUser.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,oUser);
        }
        else {
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ZERO_ROWS,Optional.empty());

        }
    }
    @Transactional(readOnly = true)

    @Override
    public ResponseBase findByIdUser(Long id) {
        Optional<Usuario> user = userDao.findById(id);
        if (user.isPresent())
        {
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,user);
        }
        return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_NON_DATA,Optional.empty());

    }

    @Transactional
    @Override
    public ResponseBase loggerUser(RequestUser requestUser) {

        boolean usuario =  userValidate.validarUser(requestUser);
        if (usuario)
        {
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.empty());
        }
        else {
            Usuario user = getUsuarioMap(requestUser);
            user.setDateCreate(timestamp());
            user.setUserCreate(user.getRol());
            String constraCodif= passwordEncoder.encode(requestUser.getPasswordUser());
            user.setPassword(constraCodif);
            return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(userDao.save(user)));
        }

    }


    @Transactional
    @Override
    public ResponseBase startSession(RequestUser requestUser) {

        try {
            Authentication authentication=authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(requestUser.getEmail(),requestUser.getPasswordUser()));
            if (authentication.isAuthenticated())
            {
                String token =  jwtUtil.generarToken(customerDetailService.getUsuario().getEmail(),customerDetailService.getUsuario().getRol());
                return  new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of(token));
            }
            else {
                return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.of("Error en el login"));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR,Optional.of("El usuario no existe"));



    }

    @Transactional
    @Override
    public ResponseBase deleteUser(Long id) {
        Optional<Usuario> user = userDao.findById(id);
        if (user.isPresent())
        {
            Usuario usuario =  user.get();
            usuario.setStatus(0);
            usuario.setDateDelete(timestamp());
            usuario.setUserDelete(usuario.getRol());
            userDao.save(usuario);
            return new ResponseBase(Constants.CODE_SUCCESS,Constants.MESS_SUCCESS,Optional.of("Usuario Deshabilitado exitosamente"));
        }

        else {
            return new ResponseBase(Constants.CODE_ERROR_EXIST,Constants.MESS_NON_DATA,Optional.empty());
        }
    }


    public Timestamp timestamp ()
    {
        return new Timestamp(System.currentTimeMillis());
    }

    public Usuario getUsuarioMap(RequestUser requestUser)
    {
        Usuario usuario = new Usuario();
        usuario.setEmail(requestUser.getEmail());
        usuario.setPassword(requestUser.getPasswordUser());
        usuario.setRol("User");
        usuario.setStatus(1);

        return  usuario;
    }





}



