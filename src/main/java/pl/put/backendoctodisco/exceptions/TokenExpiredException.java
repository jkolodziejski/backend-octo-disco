package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends AuthorizationExceptionResponse {
    public TokenExpiredException() {
        super(HttpStatus.FORBIDDEN, "Token found, but is expired.");
    }
}
