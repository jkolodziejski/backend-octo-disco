package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.LoginRequest;
import pl.put.backendoctodisco.entity.requests.RegisterRequest;
import pl.put.backendoctodisco.entity.responses.LoginResponse;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.Hash256;
import pl.put.backendoctodisco.utils.PropertiesReader;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Registers new user to database",
            notes = "Returns the authorization token of created user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 409, message = "User with such login or email already exists (error specified in the message)")
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> createUser(@RequestBody RegisterRequest registerRequest) throws PropertiesNotAvailableException, UserEmailAlreadyExistsException, UserLoginAlreadyExistsException, SomethingWentWrongException {
        User createdUser = userService.createUser(new User(registerRequest));
        AuthToken token = userService.authorizeUser(createdUser);
        return new ResponseEntity<>(new LoginResponse(createdUser.getPermissions(), token.toString()), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Login to webserver",
            notes = "Returns authorization token for logged user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully logged"),
            @ApiResponse(code = 403, message = "Wrong password"),
            @ApiResponse(code = 404, message = "The user was not found")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) throws UserNotFoundException, WrongPasswordException, SomethingWentWrongException, PropertiesNotAvailableException {
        Optional<User> userToCheck = userService.findByLogin(loginRequest.login);
        if (userToCheck.isEmpty()) {
            throw new UserNotFoundException();
        }

        final boolean passwordHash = PropertiesReader.read("password.hash").equals("true");

        User foundUser = userToCheck.get();
        String password = loginRequest.password;
        if(passwordHash){
            password = Hash256.password(password);
        }
        if (!foundUser.getPassword().equals(password)) {
            throw new WrongPasswordException();
        }

        AuthToken token = userService.authorizeUser(foundUser);
        return new ResponseEntity<>(new LoginResponse(foundUser.getPermissions(), token.toString()), HttpStatus.CREATED);
    }

}
