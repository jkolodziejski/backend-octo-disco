package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class FlashcardListAlreadyExistsException extends ExceptionResponse {
    public FlashcardListAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "Flashcard list already exists.");
    }
}
