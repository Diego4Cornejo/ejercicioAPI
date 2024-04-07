package com.ejercicio.apiusuarios.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import io.jsonwebtoken.*;

import com.ejercicio.apiusuarios.dto.CreatePhoneDTO;
import com.ejercicio.apiusuarios.dto.CreateUserDTO;
import com.ejercicio.apiusuarios.dto.ErrorResponse;
import com.ejercicio.apiusuarios.model.Phone;
import com.ejercicio.apiusuarios.model.User;
import com.ejercicio.apiusuarios.repository.UserRepository;

import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //Implementamos la interfaz
    @Autowired
    private UserRepository userRepository;
    //Se rescata el valor de la expresion regular del email y password a validar desde las properties
    @Value("${email.regex}")
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
    
    /**
     * Servicio /crearusuario POST, para la creacion del usuario
     * @param user
     * @return
     */
    @PostMapping("/crearusuario")
    public ResponseEntity<ErrorResponse> postMethodName(@RequestBody CreateUserDTO user) {
        logger.info("Llamada al servicio //crearusuario  ");
        List<User> existingUser = userRepository.findByEmail(user.getEmail());

        //valida email
        if(!isValidEmail(user.getEmail())){
            logger.error("El formato del correo no es valido");
            ErrorResponse errorResponse = new ErrorResponse("El formato del correo no es valido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        //valida password
        if(!isValidPassword(user.getPassword())){
            logger.error("El formato de la contraseña no es valido");
            ErrorResponse errorResponse = new ErrorResponse("El formato de la contraseña no es valido "  + 
                                "Al menos un digito (0-9). " + 
                                "Al menos una letra minuscula (a-z). " +
                                "Al menos una letra mayuscula (A-Z). " +
                                "Longitud minima de 8 caracteres.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);   
        }

        //valida si usuario ya esta en bd
        if(existingUser != null && existingUser.size() != 0){
            logger.error("El Correo ya esta registrado");
            ErrorResponse errorResponse = new ErrorResponse("El email ingresado ya se encuentra registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        //Mapeo de campos para el registro en bd
        User newUser = mapDtoToUser(user);
        userRepository.save(newUser);
        logger.info("Usuario Creado Correctamente");
        return ResponseEntity.ok().build();
    }

    /**
     * Metodo que mapeara los datos de userDTO al model 
     * @param userDTO
     * @return
     */
    private User mapDtoToUser(CreateUserDTO userDTO){
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setActive(true);

        List<Phone> phones = new ArrayList<>();
        for (CreatePhoneDTO phoneDTO : userDTO.getPhones()) {
            Phone phone = new Phone();
            phone.setNumber(phoneDTO.getNumber());
            phone.setCitycode(phoneDTO.getCitycode());
            phone.setCountrycode(phoneDTO.getCountrycode());
            phones.add(phone);
        }
        user.setPhones(phones);

        return user;
    }

    /**
     * Metodo que Genera el Token JWT
     * @return Token 
     */
    private String generateToken(){
        String token = Jwts.builder().setSubject(subject)
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        return token;
    }

    /**
     * Valida si email es valido comparandolo con la Expresion regular de las properties
     * @param email
     * @return True en caso de que el email sea valido
     */
    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Valida si la password es valida comparandola con la expresion regular de las properties
     * @param password
     * @return true en caso de que las password sea valida
     */
    private boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

}
