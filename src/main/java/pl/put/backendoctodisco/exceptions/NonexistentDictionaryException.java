package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class NonexistentDictionaryException extends ExceptionResponse {
    public NonexistentDictionaryException() {
        super(HttpStatus.BAD_REQUEST, "Request has dictionary choice that does not exist in the database.");
    }
}
