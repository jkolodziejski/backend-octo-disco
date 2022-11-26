package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends ExceptionResponse {
    public WrongPasswordException() {
        super(HttpStatus.FORBIDDEN, "User found, yet with wrong password");
    }
}
