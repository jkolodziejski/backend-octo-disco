package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class UserNotFoundException extends ExceptionResponse {
    public UserNotFoundException(){
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}
