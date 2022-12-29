package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class PropertiesNotAvailableException extends AuthorizationExceptionResponse {
    public PropertiesNotAvailableException(String property, String environment) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Property "+property+" not set for "+environment+" environment");
    }
}
