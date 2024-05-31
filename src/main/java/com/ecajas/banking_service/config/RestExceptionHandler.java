package com.ecajas.banking_service.config;


import com.ecajas.banking_service.exception.BadRequestException;
import com.ecajas.banking_service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Clase que tiene la respoonsabilidad de gestionar las exceptiones
 * creadas en el package exception, en este caso son dos: badRequestException
 * y resourceNotFoundException
 */

/**
 * RestControllerAdvice devuelve el mensaje de error en formato JSON
 */
@AllArgsConstructor
@RestControllerAdvice
public class RestExceptionHandler {

    private MessageSource messageSource;

    /**
     *Gestiona un exception  generico para cualquier error que devuelva una peticion
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "La solicitud tiene unos errores de validación.");

        Set<String> errors = new HashSet<>();
        List<FieldError> fieldErrors = ex.getFieldErrors();

        for (FieldError fe : fieldErrors) {
            String message = messageSource.getMessage(fe, Locale.getDefault());
            errors.add(message);
        }
        problemDetail.setProperty("errors", errors);

        return problemDetail;
    }

    /**
     *  Exception personalizado cuando no encuentra el recurso
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResorceNotFoundException(ResourceNotFoundException ex){
        return ProblemDetail.
                forStatusAndDetail(HttpStatus.NOT_FOUND,"El recurso no ha sido encontrado");
    }


    /**
     * Exception personalizado cuando el formato de
     * cuenta no es correcto
     */
    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail handleBadRequestException(BadRequestException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

}
