package com.scm.backend.exceptions;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolation(ConstraintViolationException exception){

        Map<String, String> errors = new LinkedHashMap<>();
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        constraintViolations.forEach(constraintViolation -> {
            String nameProperty = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.put(nameProperty, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Authentication failed");
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String, String>> handleJwtException(JwtException exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid JWT token");
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handlegenericException(Exception exception){
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error");
        response.put("message", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
