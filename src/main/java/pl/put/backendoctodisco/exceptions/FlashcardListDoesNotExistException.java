package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class FlashcardListDoesNotExistException extends ExceptionResponse {
    public FlashcardListDoesNotExistException() {
        super(HttpStatus.BAD_REQUEST, "Flashcard list does not exist.");
    }
}
