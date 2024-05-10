package com.example.testassignment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Requested resource was not found: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date format. Date must be in the format YYYY-MM-DD. "
                + ex.getMessage() + ".");
    }

}
