package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.TestStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;
import pl.put.backendoctodisco.entity.requests.TestResultRequest;
import pl.put.backendoctodisco.entity.responses.CardListStatistics;
import pl.put.backendoctodisco.entity.responses.FlashcardListsResponse;
import pl.put.backendoctodisco.entity.responses.TestDifficultyStatistics;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.FlashcardListService;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.StatisticsService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.ArrayList;


@RestController
@RequestMapping("/stats")
@CrossOrigin
public class StatisticsController {

    private final FlashcardService flashcardService;
    private final FlashcardListService flashcardListService;
    private final StatisticsService statisticsService;
    private final UserService userService;


    public StatisticsController(FlashcardService flashcardService, FlashcardListService flashcardListService, StatisticsService statisticsService, UserService userService) {
        this.flashcardService = flashcardService;
        this.flashcardListService = flashcardListService;
        this.statisticsService = statisticsService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update statistics of flashcards",
            notes = "Returns list statistics, take into account that endpoint does not check if flashcard is in flashcard list.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated statistics"),
            @ApiResponse(code = 400, message = "List ID not passed"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @PostMapping("/quiz")
    private ResponseEntity<CardListStatistics> sendQuizStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody QuizResultRequest quizResult) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        statisticsService.updateStatistics(quizResult, foundUser);
        if(quizResult.list_id == null){
            throw new ParameterIsMissingException("list_id");
        }

        return new ResponseEntity<>(statisticsService.findFlashcardListStatistics(foundUser, null, quizResult.list_id), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Gets statistics of flashcards",
            notes = "Returns list statistics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found statistics"),
            @ApiResponse(code = 400, message = "List ID not passed"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/list")
    private ResponseEntity<CardListStatistics> getCardsStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long list_id) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(list_id == null){
            throw new ParameterIsMissingException("list_id");
        }

        return new ResponseEntity<>(statisticsService.findFlashcardListStatistics(foundUser,null,  list_id), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Gets statistics of all flashcard lists",
            notes = "Returns statistics of all flashcard lists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found statistics"),
            @ApiResponse(code = 400, message = "List ID not passed"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/lists")
    private ResponseEntity<ArrayList<CardListStatistics>> getListsStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        ArrayList<FlashcardListInfo> lists = new ArrayList<>(flashcardListService.findUsersLists(foundUser));
        ArrayList<CardListStatistics> response = new ArrayList<>(lists.stream().map(info -> {
            return statisticsService.findFlashcardListStatistics(foundUser, info.getName(), info.getId());
        }).toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update statistics of test",
            notes = "Returns statistics for given difficulty level.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated statistics"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @PostMapping("/test")
    private ResponseEntity<TestDifficultyStatistics> sendTestStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody TestResultRequest testResult) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        statisticsService.updateStatistics(testResult, foundUser);

        if(testResult.difficulty_id == null){
            throw new ParameterIsMissingException("difficulty_id", "No statistics to find, but data updated.");
        }

        TestDifficultyStatistics statistics = statisticsService.findTestStatistics(foundUser, testResult.difficulty_id);


        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Gets statistics for given difficulty level",
            notes = "Returns statistics for given difficulty level.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found statistics"),
            @ApiResponse(code = 400, message = "Difficulty parameter not passed"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/test")
    private ResponseEntity<TestDifficultyStatistics> getTestStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long difficulty_id) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(difficulty_id == null){
            throw new ParameterIsMissingException("difficulty_id");
        }

        return new ResponseEntity<>(statisticsService.findTestStatistics(foundUser, difficulty_id), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Gets statistics of flashcards",
            notes = "Returns list statistics")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted statistics"),
            @ApiResponse(code = 400, message = "List ID not passed"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Server error")
    })
    @DeleteMapping("/list")
    private ResponseEntity<HttpStatus> deleteCardsStatistics(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long list_id) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException, ServerErrorException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(list_id == null){
            throw new ParameterIsMissingException("list_id");
        }
        if(!statisticsService.deleteFlashcardListStatistics(foundUser, list_id)){
            throw new ServerErrorException("Problem deleting statistics, but some might have been deleted.");
        }

        return new ResponseEntity<>(HttpStatus.OK, HttpStatus.OK);
    }
}