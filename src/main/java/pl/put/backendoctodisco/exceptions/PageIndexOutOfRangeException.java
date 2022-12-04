package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class PageIndexOutOfRangeException extends ExceptionResponse {
    public PageIndexOutOfRangeException() {
        super(HttpStatus.BAD_REQUEST, "Request has dictionary choice that does not exist in the database.");
    }
}
