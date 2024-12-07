package com.example.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ServiceConstraintViolationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ServiceConstraintViolationException(String message){
        super(message);
    }
}
