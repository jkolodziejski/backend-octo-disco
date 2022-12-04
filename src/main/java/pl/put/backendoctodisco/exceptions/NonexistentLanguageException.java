package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class NonexistentLanguageException extends ExceptionResponse {
    public NonexistentLanguageException() {
        super(HttpStatus.BAD_REQUEST, "Flashcard request has language that does not exist in the database.");
    }
}
