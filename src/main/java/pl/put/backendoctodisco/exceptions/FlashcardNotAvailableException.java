package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardNotAvailableException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.FORBIDDEN, "User not authorized to the given flashcard.");
}