package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class SomethingWentWrongException extends ExceptionResponse {
    public SomethingWentWrongException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Communicate with dev team, about this error if needed.");
    }
}
