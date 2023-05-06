package com.oom.temporal.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<String> errorNotHandled(RuntimeException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
