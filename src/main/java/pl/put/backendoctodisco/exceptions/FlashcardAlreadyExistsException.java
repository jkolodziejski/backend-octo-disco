package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class FlashcardAlreadyExistsException extends ExceptionResponse {
    public FlashcardAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Flashcard already exists.");
    }
}
