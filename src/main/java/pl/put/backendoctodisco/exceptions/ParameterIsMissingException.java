package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class ParameterIsMissingException extends ExceptionResponse{
    public ParameterIsMissingException(){
        super(HttpStatus.NOT_FOUND, "Parameters are missing");
    }
}
