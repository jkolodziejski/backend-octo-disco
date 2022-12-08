package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.FlashcardStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.Quiz;
import pl.put.backendoctodisco.exceptions.TokenExpiredException;
import pl.put.backendoctodisco.exceptions.TokenNotFoundException;
import pl.put.backendoctodisco.exceptions.TokenUnauthorizedException;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.StatisticsService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.List;


@RestController
@RequestMapping("/stats")
@CrossOrigin
public class StatisticsController {

    private final FlashcardService flashcardService;
    private final StatisticsService statisticsService;
    private final UserService userService;


    public StatisticsController(FlashcardService flashcardService, StatisticsService statisticsService, UserService userService) {
        this.flashcardService = flashcardService;
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get quiz of flashcards",
            notes = "Returns quiz")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found flashcards list"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @PostMapping("/quiz")
    private ResponseEntity<FlashcardStatistics> sendStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, QuizResultRequest quizResult) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        statisticsService.updateStatistics(quizResult, foundUser);

        List<FlashcardResponse> flashcards = flashcardService.getFlashcardsFromList(list_id);
        Quiz quiz = quizService.createQuizForCards(flashcards);

        //TODO no question type determined in response
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }
}