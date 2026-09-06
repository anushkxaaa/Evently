package com.evently.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.evently.dto.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(OpenServiceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(OpenServiceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(e.getMessage(),HttpStatus.NOT_FOUND.value()));
    }
}
