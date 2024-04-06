package com.ejercicio.apiusuarios.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.apiusuarios.model.User;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    //Se rescata el valor de la expresion regular del email y password a validar desde las properties
    @Value("{$email.regex}")
    private String emailRegex;

    @Value("${password.regex}")
    private String passwordRegex;

    @PostMapping("/crearusuario")
    public String postMethodName(@RequestBody User usuario) {
        //TODO: process POST request
        
        return null;
    }
    
    
}
