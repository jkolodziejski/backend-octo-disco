package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class IncorrectFlashcardRequestException extends ExceptionResponse {
    public IncorrectFlashcardRequestException() {
        super(HttpStatus.BAD_REQUEST, "Flashcard translations should be unique.");
    }
}
