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
import pl.put.backendoctodisco.exceptions.ParameterIsMissingException;
import pl.put.backendoctodisco.exceptions.TokenExpiredException;
import pl.put.backendoctodisco.exceptions.TokenNotFoundException;
import pl.put.backendoctodisco.exceptions.TokenUnauthorizedException;
import pl.put.backendoctodisco.service.LevelService;
import pl.put.backendoctodisco.service.TestService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.ArrayList;


@RestController
@RequestMapping("/level")
@CrossOrigin
public class LevelController {

    private final LevelService levelService;
    private final UserService userService;

    public LevelController(LevelService levelService, UserService userService) {
        this.levelService = levelService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get all tests and quizes in form of levels for given language",
            notes = "Returns all levels in given language")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found all levels"),
            @ApiResponse(code = 400, message = "Language missing in request"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping
    private ResponseEntity<ArrayList<DifficultyLevel>> getAllLevels(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, String language) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(language == null){
            throw new ParameterIsMissingException("language");
        }

        return new ResponseEntity<>(levelService.getAllLevels(language), HttpStatus.OK);
    }
}