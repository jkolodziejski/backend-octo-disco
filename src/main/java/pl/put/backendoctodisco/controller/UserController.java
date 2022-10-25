package pl.put.backendoctodisco.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.service.UserService;

@RestController
public class UserController  {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService=userService;
    }

    //TODO Example of ENDPOINT
    @PostMapping("createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){
       User createdUser = userService.createUser(user);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }




}
