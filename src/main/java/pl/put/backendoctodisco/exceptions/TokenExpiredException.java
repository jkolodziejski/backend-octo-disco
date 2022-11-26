package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class TokenExpiredException extends ExceptionResponse {
    public TokenExpiredException(){
        super(HttpStatus.FORBIDDEN, "Token found, but is expired.");
    }
}
