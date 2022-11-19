package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardAlreadyInListException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.CONFLICT, "Flashcard already present in the list.");
}
