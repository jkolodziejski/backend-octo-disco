package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardListDoesNotExistException extends ExceptionResponse {
    public FlashcardListDoesNotExistException(){
        super(HttpStatus.NOT_FOUND, "Flashcard list does not exist.");
    }
    public ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Flashcard list does not exist.");
}
