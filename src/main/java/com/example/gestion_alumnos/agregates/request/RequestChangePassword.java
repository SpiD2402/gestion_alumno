package com.example.gestion_alumnos.agregates.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestChangePassword {

    private String email;
    private String currentPassword;
    private String newPassword;

}
