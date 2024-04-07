package com.ejercicio.apiusuarios.dto;


public class ServiceResponse {
    private String mensaje;

    public ServiceResponse() {
    }

    public ServiceResponse(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}