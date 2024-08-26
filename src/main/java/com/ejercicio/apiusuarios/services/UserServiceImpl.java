package com.ejercicio.apiusuarios.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ejercicio.apiusuarios.dto.CreatePhoneDTO;
import com.ejercicio.apiusuarios.dto.CreateUserDTO;
import com.ejercicio.apiusuarios.dto.ServiceResponse;
import com.ejercicio.apiusuarios.exception.ServiceException;
import com.ejercicio.apiusuarios.model.Phone;
import com.ejercicio.apiusuarios.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${email.regex}")
    private String emailRegex;

    @Value("${password.regex}")
    private String passwordRegex;

    @Value("${jwt.secretKey}")
    private String secretKey;

    /**
     * 
     * @param email
     * @return
     * @throws ServiceException
     */
    public boolean isValidEmail(String email) throws ServiceException{
        try {
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (PatternSyntaxException e) {
            logger.error("Error en la expresión regular para email " + e.getMessage());
            throw new ServiceException("Error en la expresión regular para email", e);
        }

    }

    /**
     * 
     * @param password
     * @return
     * @throws ServiceException
     */
    public boolean isValidPassword(String password) throws ServiceException {
        try {
            Pattern pattern = Pattern.compile(passwordRegex);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        } catch (PatternSyntaxException e) {
            logger.error("Error en la expresión regular para contraseña" + e.getMessage());
            throw new ServiceException("Error en la expresión regular para contraseña", e);
        }

    }

    /**
     * 
     * @param newUser
     * @return
     */
    public ServiceResponse mapUserToResponse(User newUser){
        // TODO Auto-generated method stub
        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setMensaje("Usuario Creado Exitosamente");
        serviceResponse.setUser(newUser.getEmail());
        serviceResponse.setActive(newUser.getIsActive());
        serviceResponse.setCreated(newUser.getCreated());
        serviceResponse.setId(newUser.getId());
        serviceResponse.setLastLogin(newUser.getLastLogin());
        serviceResponse.setModified(newUser.getModified());
        serviceResponse.setToken(generateToken(newUser.getEmail()));
        return serviceResponse;
    }

    /**
     * 
     * @param subject
     * @return
     * @throws ServiceException
     */
    public String generateToken(String subject) throws ServiceException{
        long expirationTimeMillis = 3600000L; 
        try {
            String token = Jwts.builder().setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
            return token;
        } catch (Exception e) {
            logger.error("Error generando el token JWT" + e.getMessage());
            throw new ServiceException("Error generando el token JWT", e);
        }

    }

     /**
     * Metodo que mapeara los datos de userDTO al model 
     * @param userDTO
     * @return
     */
    public User mapDtoToUser(CreateUserDTO userDTO) throws ServiceException{
        try {
            User user = new User();
            user.setName(userDTO.getName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setActive(true);
            user.setLastLogin(LocalDateTime.now());
            user.setCreated(LocalDateTime.now());
            user.setModified(LocalDateTime.now());
    
            List<Phone> phones = new ArrayList<>();
            for (CreatePhoneDTO phoneDTO : userDTO.getPhones()) {
                Phone phone = new Phone();
                phone.setNumber(phoneDTO.getNumber());
                phone.setCitycode(phoneDTO.getCitycode());
                phone.setCountrycode(phoneDTO.getCountrycode());
                phone.setUser(user);
                phones.add(phone);
            }
            user.setPhones(phones);

            return user;
        } catch (Exception e) {
            logger.error("Error al mapear datos del usuario" + e.getMessage());
            throw new ServiceException("Error al mapear datos del usuario", e);
        }
    }
}
