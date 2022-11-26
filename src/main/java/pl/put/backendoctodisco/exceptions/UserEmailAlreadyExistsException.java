package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class UserEmailAlreadyExistsException extends ExceptionResponse {
    public UserEmailAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "User email already exists");
    }
}
