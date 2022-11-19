package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class FlashcardDoesNotExistException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Flashcard does not exist.");
}