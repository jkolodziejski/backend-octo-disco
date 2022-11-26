package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class NonexistentChoiceException extends Throwable{
    public ApiError error = new ApiError(HttpStatus.NOT_FOUND, "Request has choice that does not exist in the database.");
}
