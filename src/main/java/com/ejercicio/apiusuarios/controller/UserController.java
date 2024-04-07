package com.ejercicio.apiusuarios.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.*;

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

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.subject}")
    private String subject;


    @GetMapping("/usuario")
    public String TestingGET() {
        String token = generateToken();
        logger.info("Se ha generado un token");
        return token;
    }
    
    @PostMapping("/crearusuario")
    public String postMethodName(@RequestBody CreateUserDTO usuario) {
        //TODO: process POST request

        return null;
    }

    /**
     * Metodo que Genera el Token
     * @return
     */
    private String generateToken(){
        String token = Jwts.builder().setSubject(subject)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        return token;
    }

}
