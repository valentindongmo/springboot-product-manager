package com.productmanager.product_manager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;


@ControllerAdvice
public class GlobalHandlerException {

    /**
     * Gestion des exceptions personnalisées métiers
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorEntity> handleProductNotFoundException(
            ProductNotFoundException ex,
            HttpServletRequest request) {

        ErrorEntity error = new ErrorEntity(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}