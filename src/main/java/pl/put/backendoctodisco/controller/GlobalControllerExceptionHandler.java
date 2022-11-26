package pl.put.backendoctodisco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.utils.ApiError;


@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<ApiError> defaultException(ExceptionResponse exception) {
        return new ResponseEntity<>(exception.error, exception.error.status());
    }
}