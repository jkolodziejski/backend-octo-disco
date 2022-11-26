package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;

public class TokenUnauthorizedException extends ExceptionResponse {
    public TokenUnauthorizedException(){
        super(HttpStatus.FORBIDDEN, "Token signature does not match with secret key and should not be trusted.");
    }
}
