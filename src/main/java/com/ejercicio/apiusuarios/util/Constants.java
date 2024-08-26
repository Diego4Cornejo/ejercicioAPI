package com.ejercicio.apiusuarios.util;

public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    public static final String EMAIL_INVALID_MESSAGE = "El formato del correo no es valido";
    public static final String PASSWORD_INVALID_MESSAGE = "El formato de la contraseña no es valido. " +
            "Al menos un digito (0-9). " +
            "Al menos una letra minuscula (a-z). " +
            "Al menos una letra mayuscula (A-Z). " +
            "Longitud minima de 8 caracteres.";
    public static final String EMAIL_ALREADY_REGISTERED_MESSAGE = "El email ingresado ya se encuentra registrado";
    public static final String USER_CREATION_ERROR_MESSAGE = "Error al crear usuario";
    public static final String TOKEN_INVALID_MESSAGE = "Token invalido";
    public static final String TOKEN_VALID_MESSAGE = "Token Valido";
    
}
