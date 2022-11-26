package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardAlreadyInListException extends ExceptionResponse {
    public FlashcardAlreadyInListException(){
        super(HttpStatus.CONFLICT, "Flashcard already present in the list.");
    }
}
