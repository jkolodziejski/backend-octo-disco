package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.QuizQuestion;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.ListRequest;
import pl.put.backendoctodisco.entity.responses.AllFlashcardsResponse;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.QuizChooseQuestion;
import pl.put.backendoctodisco.utils.QuizTypeQuestion;

import java.util.*;


@RestController
@RequestMapping("/quiz")
@CrossOrigin
public class QuizController {

    private final FlashcardService flashcardService;
    private final UserService userService;

    public QuizController(FlashcardService flashcardService, UserService userService) {
        this.flashcardService = flashcardService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from list",
            notes = "Returns flashcards from list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found flashcards"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping
    private ResponseEntity<List<QuizQuestion>> getQuiz(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody ListRequest listRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        //this does not work yet, only a prototype
        List<FlashcardResponse> flashcards = flashcardService.getFlashcardsFromList(listRequest.list_id);
//        Collections.shuffle(flashcards);
        QuizTypeQuestion q1 = new QuizTypeQuestion(108L);
        QuizChooseQuestion q2 = new QuizChooseQuestion(flashcards.get(0));
        List<QuizQuestion> questions = Arrays.asList(q1, q2);

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }
}