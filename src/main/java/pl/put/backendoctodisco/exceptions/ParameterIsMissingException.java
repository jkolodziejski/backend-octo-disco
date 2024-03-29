package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class ParameterIsMissingException extends ExceptionResponse {
    public ParameterIsMissingException(String parameterMissing) {
        super(HttpStatus.BAD_REQUEST, "Parameter "+parameterMissing+" is missing");
    }

    public ParameterIsMissingException(String parameterMissing, String successMessage) {
        super(HttpStatus.OK, "Parameter "+parameterMissing+" is missing. "+successMessage);
    }
}
