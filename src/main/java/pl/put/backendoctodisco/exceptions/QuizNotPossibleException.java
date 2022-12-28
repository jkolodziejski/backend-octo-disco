package pl.put.backendoctodisco.exceptions;

import org.springframework.http.HttpStatus;

public class QuizNotPossibleException extends ExceptionResponse {
    public QuizNotPossibleException(String has, String should) {
        super(HttpStatus.CONFLICT, "Quiz not possible to create. Flashcard list has "+has+" flashcards when it should have "+should+" flashcards minimum.");
    }
}
