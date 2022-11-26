package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends ExceptionResponse {
    public TokenNotFoundException() {
        super(HttpStatus.FORBIDDEN, "Token not found in the database or written incorrectly in the first place.");
    }
}
