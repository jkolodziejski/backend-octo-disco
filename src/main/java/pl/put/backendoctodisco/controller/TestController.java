package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.responses.Test;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestOrderAnswer;
import pl.put.backendoctodisco.entity.test_entity.TestOrderQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;
import pl.put.backendoctodisco.exceptions.ParameterIsMissingException;
import pl.put.backendoctodisco.exceptions.TokenExpiredException;
import pl.put.backendoctodisco.exceptions.TokenNotFoundException;
import pl.put.backendoctodisco.exceptions.TokenUnauthorizedException;
import pl.put.backendoctodisco.service.TestService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.test.TestOrderQuestionResponse;
import pl.put.backendoctodisco.utils.test.TestQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
            @ApiResponse(code = 200, message = "Successfully created test"),
            @ApiResponse(code = 400, message = "Difficulty missing in request"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping
    private ResponseEntity<Test> getTest(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestParam(name = "difficulty", required = true) Integer difficulty, @RequestParam(name = "size", required = false) Integer size) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(difficulty == null){
            throw new ParameterIsMissingException("difficulty");
        }
        if(size == null){
            size = 10;
        }

        return new ResponseEntity<>(testService.createTest(difficulty, size), HttpStatus.OK);
    }
}