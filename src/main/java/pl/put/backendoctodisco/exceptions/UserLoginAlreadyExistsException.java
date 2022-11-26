package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class UserLoginAlreadyExistsException extends ExceptionResponse {
    public UserLoginAlreadyExistsException(){
        super(HttpStatus.CONFLICT, "User login already exists");
    }
}
