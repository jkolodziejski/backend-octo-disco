package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class ParameterIsMissingException extends ExceptionResponse{
    public ParameterIsMissingException(){
        super(HttpStatus.BAD_REQUEST, "Parameters are missing");
    }
}
