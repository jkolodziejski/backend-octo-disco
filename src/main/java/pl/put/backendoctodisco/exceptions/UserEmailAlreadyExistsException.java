package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class UserEmailAlreadyExistsException extends ExceptionResponse {
    public UserEmailAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "User email already exists");
    }
}
