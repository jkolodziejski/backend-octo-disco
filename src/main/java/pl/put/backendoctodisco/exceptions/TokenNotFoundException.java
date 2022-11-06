package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class TokenNotFoundException extends Throwable {
    public ApiError error = new ApiError(HttpStatus.FORBIDDEN, "Token not found in the database");
}
