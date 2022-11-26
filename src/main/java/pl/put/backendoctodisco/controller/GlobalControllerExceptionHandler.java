package pl.put.backendoctodisco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.utils.ApiError;


@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<ApiError> userNotFoundException(ExceptionResponse ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }
}