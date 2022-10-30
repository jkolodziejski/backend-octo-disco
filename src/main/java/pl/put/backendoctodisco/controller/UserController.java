package pl.put.backendoctodisco.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @ApiOperation(value = "Add new user to database", notes = "Returns a use, which was created")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved")
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


    @ApiOperation(value = "Login to webserver", notes = "authToken")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The user was not found")
    })
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) throws UserNotFoundException, WrongPasswordException {
        User foundUser = userService.findByLogin(user);

        if(foundUser == null){
            throw new UserNotFoundException();
        }
        if(!foundUser.getPassword().equals(user.getPassword())){
            throw new WrongPasswordException();
        }

        long now = System.currentTimeMillis();
        String key;
        try {
            key = Files.readAllLines(Paths.get("authorization.key")).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String authToken = Jwts.builder()
                    .setSubject(user.getLogin())
                    .claim("roles", "user")
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(now + 10000))
                    .signWith(SignatureAlgorithm.HS256, key).compact();

        return new ResponseEntity<>(authToken, HttpStatus.ACCEPTED);
    }


}
