package com.example.gestion_alumnos.service;

import com.example.gestion_alumnos.agregates.request.RequestUser;
import com.example.gestion_alumnos.agregates.response.ResponseBase;

public interface UsuarioService {

    ResponseBase findAllUser();
    ResponseBase findByIdUser(Long id);

    ResponseBase loggerUser(RequestUser requestUser);

    ResponseBase startSession(RequestUser requestUser);

    ResponseBase deleteUser(Long id);

}
