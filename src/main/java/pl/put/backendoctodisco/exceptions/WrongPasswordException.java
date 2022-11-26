package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class WrongPasswordException extends ExceptionResponse {
    public WrongPasswordException(){
        super(HttpStatus.FORBIDDEN, "User found, yet with wrong password");
    }
}
