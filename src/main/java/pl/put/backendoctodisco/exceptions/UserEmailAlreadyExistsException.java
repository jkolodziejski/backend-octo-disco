package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class UserEmailAlreadyExistsException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.CONFLICT, "User email already exists");
}
