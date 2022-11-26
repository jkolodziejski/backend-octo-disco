package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardNotAvailableException extends ExceptionResponse {
    public FlashcardNotAvailableException(){
        super(HttpStatus.FORBIDDEN, "User not authorized to the given flashcard.");
    }
}