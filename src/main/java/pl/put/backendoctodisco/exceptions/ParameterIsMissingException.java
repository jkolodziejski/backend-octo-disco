package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class ParameterIsMissingException extends ExceptionResponse {
    public ParameterIsMissingException() {
        super(HttpStatus.BAD_REQUEST, "Parameters are missing");
    }
}
