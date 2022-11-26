package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardDoesNotExistException extends ExceptionResponse {
    public FlashcardDoesNotExistException(){
        super(HttpStatus.BAD_REQUEST, "Flashcard does not exist.");
    }
}