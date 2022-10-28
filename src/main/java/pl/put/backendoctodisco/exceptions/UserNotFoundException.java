package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.entity.ApiError;

public class UserNotFoundException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.CONFLICT, "User not found");
}
