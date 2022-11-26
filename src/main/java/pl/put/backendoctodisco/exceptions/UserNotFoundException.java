package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ExceptionResponse {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
