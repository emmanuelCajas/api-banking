package com.ecajas.banking_service.exception;

/**
 * Se da cuando se envie una cuenta distinta a la configurada
 * o no cumpla con los requisitos de connfiguracion de una cuenta
 */
public class BadRequestException extends RuntimeException{

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}
