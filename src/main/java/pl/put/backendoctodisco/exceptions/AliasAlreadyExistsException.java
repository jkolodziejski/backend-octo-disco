package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;


public class AliasAlreadyExistsException extends ExceptionResponse{
    public AliasAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "The alias already exists.");
    }
}
