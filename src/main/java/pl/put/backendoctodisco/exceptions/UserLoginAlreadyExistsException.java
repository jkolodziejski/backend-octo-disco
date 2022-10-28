package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.entity.ApiError;

public class UserLoginAlreadyExistsException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.CONFLICT, "User login already exists");
}
