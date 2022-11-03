package pl.put.backendoctodisco.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.AuthToken;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.UserEmailAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserLoginAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserNotFoundException;
import pl.put.backendoctodisco.exceptions.WrongPasswordException;
import pl.put.backendoctodisco.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController  {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService=userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registers new user to database",
                    notes = "Returns the created user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 409, message = "User with such login or email already exists (error specified in the message)")
    })
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception | UserLoginAlreadyExistsException | UserEmailAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }


    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Login to webserver",
                    notes = "Returns authorization token for user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully logged"),
            @ApiResponse(code = 403, message = "Wrong password"),
            @ApiResponse(code = 404, message = "The user was not found")
    })
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user) throws UserNotFoundException, WrongPasswordException {
        User foundUser = userService.findByLogin(user);
        foundUser.setAuthToken(new AuthToken(foundUser).toString());

        if(foundUser == null){
            throw new UserNotFoundException();
        }
        if(!foundUser.getPassword().equals(user.getPassword())){
            throw new WrongPasswordException();
        }

        return new ResponseEntity<>(foundUser, HttpStatus.CREATED);
    }


}
