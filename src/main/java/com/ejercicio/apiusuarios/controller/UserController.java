package com.ejercicio.apiusuarios.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejercicio.apiusuarios.dto.CreateUserDTO;
import com.ejercicio.apiusuarios.dto.ErrorResponse;
import com.ejercicio.apiusuarios.dto.ServiceResponse;
import com.ejercicio.apiusuarios.exception.ServiceException;
import com.ejercicio.apiusuarios.model.User;
import com.ejercicio.apiusuarios.repository.UserRepository;
import com.ejercicio.apiusuarios.services.UserService;
import com.ejercicio.apiusuarios.util.Constants;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;



@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //inyectamos la interfaz
    @Autowired
    private UserRepository userRepository;
    //inyectamos la logica de negocio
    @Autowired
    private UserService userService; 

    @Value("${jwt.secretKey}")
    private String secretKey;
    
    /**
     * Servicio /crearusuario POST, para la creacion del usuario
     * @param user
     * @return
     */
    @PostMapping("/crearusuario")
    public ResponseEntity<?> postMethodName(@RequestBody CreateUserDTO user) {
        try {
            logger.info("Llamada al servicio //crearusuario");

            // Valida email
            if (!userService.isValidEmail(user.getEmail())) {
                logger.error(Constants.EMAIL_INVALID_MESSAGE);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(Constants.EMAIL_INVALID_MESSAGE));
            }

            // Valida password
            if (!userService.isValidPassword(user.getPassword())) {
                logger.error(Constants.PASSWORD_INVALID_MESSAGE);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(Constants.PASSWORD_INVALID_MESSAGE));
            }

            // Valida si usuario ya está en bd
            List<User> existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null && !existingUser.isEmpty()) {
                logger.error(Constants.EMAIL_ALREADY_REGISTERED_MESSAGE);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(Constants.EMAIL_ALREADY_REGISTERED_MESSAGE));
            }

            // Mapeo de campos para el registro en bd
            User newUser = userService.mapDtoToUser(user);
            userRepository.save(newUser);
            logger.info("Usuario Creado Correctamente");

            ServiceResponse response = userService.mapUserToResponse(newUser);
            return ResponseEntity.ok(response);

        } catch (ServiceException e) {
            logger.error(Constants.USER_CREATION_ERROR_MESSAGE + ": " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(Constants.USER_CREATION_ERROR_MESSAGE + " " + e.getMessage()));
        }
    }
    /**
     * Valida el token entregado
     * @param token
     * @return
     */
    @GetMapping("/valida-token")
    public ResponseEntity<String> validaToken(@RequestParam String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return ResponseEntity.ok(Constants.TOKEN_VALID_MESSAGE);
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Constants.TOKEN_INVALID_MESSAGE);
        }
    }
}
