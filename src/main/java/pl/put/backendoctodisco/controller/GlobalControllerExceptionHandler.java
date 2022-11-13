package pl.put.backendoctodisco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.utils.ApiError;


@ControllerAdvice
class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> userNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ApiError> wrongPasswordException(WrongPasswordException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(UserLoginAlreadyExistsException.class)
    public ResponseEntity<ApiError> userLoginAlreadyExistsException(UserLoginAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<ApiError> userEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ApiError> tokenNotFoundException(TokenNotFoundException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ApiError> tokenExpiredException(TokenExpiredException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(TokenUnauthorizedException.class)
    public ResponseEntity<ApiError> tokenUnauthorizedException(TokenUnauthorizedException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(FlashcardAlreadyExistsException.class)
    public ResponseEntity<ApiError> flashcardAlreadyExistsException(FlashcardAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(NonexistentLanguageException.class)
    public ResponseEntity<ApiError> nonexistentLanguageException(NonexistentLanguageException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }

    @ExceptionHandler(FlashcardListAlreadyExistsException.class)
    public ResponseEntity<ApiError> flashcardListAlreadyExistsException(FlashcardListAlreadyExistsException ex) {
        return new ResponseEntity<>(ex.error, ex.error.status());
    }
}