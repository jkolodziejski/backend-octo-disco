package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;


public abstract class AuthorizationExceptionResponse extends ExceptionResponse {
    protected AuthorizationExceptionResponse(HttpStatus status, String message) {
        super(status, message);
    }
}
