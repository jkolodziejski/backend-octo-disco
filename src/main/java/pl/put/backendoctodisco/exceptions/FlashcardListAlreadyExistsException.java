package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardListAlreadyExistsException extends ExceptionResponse {
    public FlashcardListAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "Flashcard list already exists.");
    }
}
