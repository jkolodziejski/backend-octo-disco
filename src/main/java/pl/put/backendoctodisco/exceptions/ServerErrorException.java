package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class ServerErrorException extends ExceptionResponse {
    public ServerErrorException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}