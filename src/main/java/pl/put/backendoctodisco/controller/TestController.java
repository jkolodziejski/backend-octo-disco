package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.TestResponse;
import pl.put.backendoctodisco.exceptions.TokenExpiredException;
import pl.put.backendoctodisco.exceptions.TokenNotFoundException;
import pl.put.backendoctodisco.exceptions.TokenUnauthorizedException;
import pl.put.backendoctodisco.service.TestService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

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
            @ApiResponse(code = 200, message = "Successfully found flashcards"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping
    private ResponseEntity<TestResponse> getTest(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        List<FlashcardResponse> flashcards = TestService.
//        Quiz quiz = .createQuizForCards(flashcards);

        //TODO no question type determined in response
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }
}