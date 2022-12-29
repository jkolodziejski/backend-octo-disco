package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class EntityDoesNotExistException extends ExceptionResponse {
    public EntityDoesNotExistException(String entity) {
        super(HttpStatus.BAD_REQUEST, entity+" does not exist.");
    }
}