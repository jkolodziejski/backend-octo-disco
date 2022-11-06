package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class TokenExpiredException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Token found, but is expired.");
}
