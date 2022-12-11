package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class FlashcardListNotAvailableException extends ExceptionResponse {
    public FlashcardListNotAvailableException() {
        super(HttpStatus.FORBIDDEN, "User not authorized to the given flashcard list.");
    }
}