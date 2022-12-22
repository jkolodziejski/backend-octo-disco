package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.ListRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.Quiz;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.QuizService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.*;


@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    private final FlashcardService flashcardService;
    private final QuizService quizService;
    private final UserService userService;

    public QuizController(FlashcardService flashcardService, QuizService quizService, UserService userService) {
        this.flashcardService = flashcardService;
        this.quizService = quizService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get quiz of flashcards",
            notes = "Returns quiz")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found flashcards list"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping
    private ResponseEntity<Quiz> getQuiz(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestParam(name = "list_id") Long list_id) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        //TODO quiz might be empty
        List<FlashcardResponse> flashcards = flashcardService.getFlashcardsFromList(list_id);
        Quiz quiz = quizService.createQuizForCards(flashcards);

        //TODO no question type determined in response
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }
}