package com.example.products_app.product.exception;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Hidden
public class GlobalExceptionsHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionsHandler.class);

    @ExceptionHandler({
            EntityNotFoundException.class,
            PropertyValueException.class
    })
    public ResponseEntity<String> entityNotFound(RuntimeException e) {
        logger.error("{} - {}", HttpStatus.METHOD_FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_FAILURE)
                .body(e.getMessage());
    }
}
