package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;


public class AliasAlreadyExistsException extends Throwable{
    public ApiError error = new ApiError(HttpStatus.CONFLICT, "The alias already exists.");
}
