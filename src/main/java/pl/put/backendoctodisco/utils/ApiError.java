package pl.put.backendoctodisco.utils;

import org.springframework.http.HttpStatus;

public record ApiError(HttpStatus status, String message) {
}