package com.ejercicio.apiusuarios.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class UserController {

    @GetMapping("/usuarios")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}
