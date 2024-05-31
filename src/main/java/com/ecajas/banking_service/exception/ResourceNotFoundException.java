package com.ecajas.banking_service.exception;

/**
 *Esta exception reporesenta un escenario en el cual no se
 * encuentra un recurso
 */
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}


