package pl.put.backendoctodisco.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.put.backendoctodisco.entity.ApiError;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.UserNotFoundException;
import pl.put.backendoctodisco.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class UserController  {

    private final UserService userService;
    @Autowired

    public UserController(UserService userService) {
        this.userService=userService;
    }

    //TODO Example of ENDPOINT
    @PostMapping("user/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
       User createdUser = userService.createUser(user);

       return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("user/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) throws UserNotFoundException {
        Boolean userFound = userService.findByLogin(user);

        if(!userFound){
            throw new UserNotFoundException();
        }

        Long now = System.currentTimeMillis();
        String key;
        try {
            key = Files.readAllLines(Paths.get("authorization.key")).get(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String authToken = Jwts.builder()
                    .setSubject(user.getLogin()) // 1
                    .claim("roles", "user") // 2
                    .setIssuedAt(new Date(now)) // 3
                    .setExpiration(new Date(now + 10000)) // 4
                    .signWith(SignatureAlgorithm.HS256, key).compact(); // 5

        return new ResponseEntity<>(authToken, HttpStatus.CREATED);
    }

}
