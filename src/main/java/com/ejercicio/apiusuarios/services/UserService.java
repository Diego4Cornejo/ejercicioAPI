package com.ejercicio.apiusuarios.services;

import com.ejercicio.apiusuarios.dto.CreateUserDTO;
import com.ejercicio.apiusuarios.dto.ServiceResponse;
import com.ejercicio.apiusuarios.exception.ServiceException;
import com.ejercicio.apiusuarios.model.User;

public interface UserService {
    
    boolean isValidEmail(String email) throws ServiceException;
    boolean isValidPassword(String password) throws ServiceException;
    ServiceResponse mapUserToResponse(User newUser);
    String generateToken(String subject) throws ServiceException;
    User mapDtoToUser(CreateUserDTO userDTO) throws ServiceException;
}
