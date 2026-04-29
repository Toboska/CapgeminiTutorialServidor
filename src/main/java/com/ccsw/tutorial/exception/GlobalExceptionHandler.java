package com.ccsw.tutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //400
    @ExceptionHandler(BusinessBadRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessBadRequestException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("errorCode", ex.getErrorCode(), "message", ex.getMessage(), "field", ex.getField()));
    }

    @ExceptionHandler(BusinessConflictException.class)
    public ResponseEntity<Map<String, Object>> BusinessNotFoundException(BusinessConflictException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errorCode", ex.getErrorCode(), "message", ex.getMessage(), "field", ex.getField()));
    }

    //409
    @ExceptionHandler(BusinessConflictException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessConflict(BusinessConflictException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("errorCode", ex.getErrorCode(), "message", ex.getMessage(), "field", ex.getField()));
    }
}
