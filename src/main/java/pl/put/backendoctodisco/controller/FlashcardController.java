package pl.put.backendoctodisco.controller;

<<<<<<< HEAD
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
=======

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
>>>>>>> feature/#48-creating-flashcards
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.service.FlashcardService;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;


    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @ApiOperation(value = "Add a new flashcard to database", notes = "Returns a flashcard, which was added")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
    @PostMapping("/create")
    private ResponseEntity<Flashcard> createFlashcard(@RequestBody Flashcard flashcard){
        Flashcard createdFlashcard = flashcardService.createFlashcard(flashcard);
        return new  ResponseEntity<>(createdFlashcard,HttpStatus.CREATED);
=======
import org.springframework.web.bind.annotation.RestController;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.UserEmailAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserLoginAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserNotFoundException;
import pl.put.backendoctodisco.exceptions.WrongPasswordException;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.service.impl.FlashcardServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class FlashcardController {

    private final FlashcardServiceImpl service;

    public FlashcardController(FlashcardServiceImpl service) {
        this.service = service;
    }

    @PostMapping("card/create")
    public ResponseEntity<Flashcard> createFlashcard(@RequestBody Flashcard flashcard){
        Flashcard createdFlashcard = service.createFlashcard(flashcard);
        return new ResponseEntity<>(createdFlashcard, HttpStatus.CREATED);
>>>>>>> feature/#48-creating-flashcards
    }

}
