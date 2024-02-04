package com.example.gestion_alumnos.util;

import com.example.gestion_alumnos.agregates.request.RequestUser;
import com.example.gestion_alumnos.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserValidate {



    private final UserDao userDao;

    public UserValidate(UserDao userDao) {
        this.userDao = userDao;
    }

    public  boolean validarUser(RequestUser requestUser)
    {
        if (isNullorEmpty(requestUser.getEmail()) || isNullorEmpty(requestUser.getPasswordUser()))
        {
            return  true;
        }

        return userDao.existByEmail(requestUser.getEmail());
    }


    public boolean isNullorEmpty (String valor)
    {
        return  valor ==null || valor.isEmpty();
    }






}
