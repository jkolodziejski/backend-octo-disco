package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class NonexistentLanguageException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.CONFLICT, "Flashcard request has language that does not exist in the database.");
}
