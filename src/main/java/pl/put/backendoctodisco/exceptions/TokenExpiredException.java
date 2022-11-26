package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends ExceptionResponse {
    public TokenExpiredException() {
        super(HttpStatus.FORBIDDEN, "Token found, but is expired.");
    }
}
