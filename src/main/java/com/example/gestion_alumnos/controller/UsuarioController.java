package com.example.gestion_alumnos.controller;

import com.example.gestion_alumnos.agregates.Constants;
import com.example.gestion_alumnos.agregates.request.RequestUser;
import com.example.gestion_alumnos.agregates.response.ResponseBase;
import com.example.gestion_alumnos.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UsuarioController {

    private  final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/singup")
    public ResponseBase addUser(@RequestBody RequestUser requestUser)
    {
        return usuarioService.loggerUser(requestUser);
    }

    @PostMapping ("/login")
    public  ResponseBase login(@RequestBody RequestUser requestUser)
    {
        try
        {
            return usuarioService.startSession(requestUser);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
            return new ResponseBase(Constants.CODE_ERROR_DATA_NOT,Constants.MESS_ERROR, Optional.empty());
    }

    @GetMapping
    public ResponseBase listar()
    {
        return  usuarioService.findAllUser();
    }

    @DeleteMapping("/{id}")
    public ResponseBase deleteUser(@PathVariable Long id)
    {
        return  usuarioService.deleteUser(id);
    }

}
