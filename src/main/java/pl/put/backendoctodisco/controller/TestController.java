package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.responses.Test;
import pl.put.backendoctodisco.entity.responses.TestLevel;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestOrderAnswer;
import pl.put.backendoctodisco.entity.test_entity.TestOrderQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.TestService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.Language;
import pl.put.backendoctodisco.utils.test.TestOrderQuestionResponse;
import pl.put.backendoctodisco.utils.test.TestQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    private final TestService testService;
    private final UserService userService;

    public TestController(TestService testService, UserService userService) {
        this.testService = testService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get grammar test",
            notes = "Returns test")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found test"),
            @ApiResponse(code = 400, message = "Difficulty ID missing in request or not existing in database, possibly not enough exp"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping
    private ResponseEntity<Test> getTest(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long difficulty_id, @RequestParam(name = "size", required = false) Integer size) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException, EntityDoesNotExistException, EntityNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(difficulty_id == null){
            throw new ParameterIsMissingException("difficulty_id");
        }
        if(size == null){
            size = 10;
        }
        Optional<DifficultyLevel> level = testService.getLevel(difficulty_id);
        if(level.isEmpty()){
            throw new EntityDoesNotExistException("Difficulty level");
        }
        if(level.get().getExp_needed() > foundUser.getExp()){
            throw new EntityNotAvailableException("difficulty level");
        }

        return new ResponseEntity<>(testService.createTest(foundUser, difficulty_id, size), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all test levels available for given language",
            notes = "Returns list of difficulty levels")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found test levels"),
            @ApiResponse(code = 400, message = "Language missing in request or is not existing"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/levels")
    private ResponseEntity<ArrayList<TestLevel>> getLevels(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, String language) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException, NonexistentLanguageException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(language == null){
            throw new ParameterIsMissingException("language");
        }
        if (!Language.contains(language)) {
            throw new NonexistentLanguageException();
        }

        ArrayList<TestLevel> difficultyLevels = new ArrayList<>( testService.getLevels(language).stream().map(TestLevel::new).toList() );

        return new ResponseEntity<>(difficultyLevels, HttpStatus.OK);
    }
}