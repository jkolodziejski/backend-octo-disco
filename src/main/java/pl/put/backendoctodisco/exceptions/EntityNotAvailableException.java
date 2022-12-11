package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotAvailableException extends ExceptionResponse {
    public EntityNotAvailableException(String entityName) {
        super(HttpStatus.FORBIDDEN, "User not authorized to the given "+entityName);
    }
}