package com.devsu.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.devsu.demo.exception.FormatoFechaException;
import com.devsu.demo.exception.IdNoEncontradaException;
import com.devsu.demo.exception.SaldoInsuficienteException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Map<String, String>> errorDetails = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            // Format the error details
            Map<String, String> errorDetail = new HashMap<>();
            errorDetail.put("code", "VALIDATION_ERROR");
            errorDetail.put("field", violation.getPropertyPath().toString());
            errorDetail.put("message", violation.getMessage());
            errorDetails.add(errorDetail);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("errors", errorDetails);

        return ResponseEntity.badRequest().body(response);
    }
    
    // Handle FormatoFechaException
    @ExceptionHandler(FormatoFechaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleFormatoFechaException(FormatoFechaException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "FORMATO_FECHA_ERROR", ex.getMessage());
    }

    // Handle IdNoEncontradaException
    @ExceptionHandler(IdNoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Map<String, Object>> handleIdNoEncontradaException(IdNoEncontradaException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, "ID_NO_ENCONTRADA_ERROR", ex.getMessage());
    }

    // Handle SaldoInsuficienteException
    @ExceptionHandler(SaldoInsuficienteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleSaldoInsuficienteException(SaldoInsuficienteException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "SALDO_INSUFICIENTE_ERROR", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpStatus status, String errorCode, String errorMessage) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status.value());

        Map<String, String> errorDetail = new HashMap<>();
        errorDetail.put("code", errorCode);
        errorDetail.put("message", errorMessage);

        response.put("error", errorDetail);

        return ResponseEntity.status(status).body(response);
    }
    
    
}
