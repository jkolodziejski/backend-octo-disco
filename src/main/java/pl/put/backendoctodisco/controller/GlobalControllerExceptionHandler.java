package pl.put.backendoctodisco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.put.backendoctodisco.entity.ApiError;
import pl.put.backendoctodisco.exceptions.UserNotFoundException;


@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> userNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }
}