package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.entity.ApiError;

public class WrongPasswordException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.FORBIDDEN, "User found, yet with wrong password");
}
