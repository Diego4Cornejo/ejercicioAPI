package com.ejercicio.apiusuarios.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.ejercicio.apiusuarios.dto.CreateUserDTO;


import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //Se rescata el valor de la expresion regular del email y password a validar desde las properties
    @Value("{$email.regex}")
    private String emailRegex;

    @Value("${password.regex}")
    private String passwordRegex;

    @GetMapping("/usuario")
    public String TestingGET() {
        logger.info("Ejemplo de log con nivel INFO");
        logger.debug("Ejemplo de log con nivel DEBUG");
        logger.error("Ejemplo de log con nivel ERROR");
        return "TestingGET";
    }
    
    @PostMapping("/crearusuario")
    public String postMethodName(@RequestBody CreateUserDTO usuario) {
        //TODO: process POST request
        
        return null;
    }
    
    
}
