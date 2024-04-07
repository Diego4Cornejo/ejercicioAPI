package com.ejercicio.apiusuarios.dto;

import java.util.List;

/***
 * Clase DTO para la creacion del usuario
 */
public class CreateUserDTO {
    private String name;
    private String email;
    private String password;

    private List<CreatePhoneDTO> phones;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<CreatePhoneDTO> getPhones() {
        return phones;
    }
    public void setPhones(List<CreatePhoneDTO> phones) {
        this.phones = phones;
    }
}
