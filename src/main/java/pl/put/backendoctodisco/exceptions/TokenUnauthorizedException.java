package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class TokenUnauthorizedException extends AuthorizationExceptionResponse {
    public TokenUnauthorizedException() {
        super(HttpStatus.FORBIDDEN, "Token signature does not match with secret key and should not be trusted.");
    }
}
