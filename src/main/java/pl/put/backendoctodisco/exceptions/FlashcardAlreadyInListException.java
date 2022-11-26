package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class FlashcardAlreadyInListException extends ExceptionResponse {
    public FlashcardAlreadyInListException() {
        super(HttpStatus.CONFLICT, "Flashcard already present in the list.");
    }
}
