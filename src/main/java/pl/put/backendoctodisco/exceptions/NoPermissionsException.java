package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class NoPermissionsException extends ExceptionResponse {
    public NoPermissionsException() {
        super(HttpStatus.FORBIDDEN, "User not authorized for this action. Admin permissions expected.");
    }
}