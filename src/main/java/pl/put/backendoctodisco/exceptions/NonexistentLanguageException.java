package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class NonexistentLanguageException extends ExceptionResponse {
    public NonexistentLanguageException(){
        super(HttpStatus.CONFLICT, "Flashcard request has language that does not exist in the database.");
    }
}
