package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;
import pl.put.backendoctodisco.utils.ApiError;


public abstract class ExceptionResponse extends Throwable{
    public final ApiError error;
    protected ExceptionResponse(HttpStatus status, String message){
        this.error = new ApiError(status, message);
    }
}
