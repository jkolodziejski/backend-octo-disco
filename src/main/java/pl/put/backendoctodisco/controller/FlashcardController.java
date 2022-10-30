package pl.put.backendoctodisco.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.service.impl.FlashcardServiceImpl;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    private final FlashcardServiceImpl flashcardService;

    public FlashcardController(FlashcardServiceImpl flashcardService) {
        this.flashcardService = flashcardService;
    }

    @ApiOperation(value = "Add a new flashcard to database", notes = "Returns a flashcard, which was added")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved")
    })
    @PostMapping("/create")
    private ResponseEntity<Flashcard> createFlashcard(@RequestBody Flashcard flashcard) {
        Flashcard createdFlashcard = flashcardService.createFlashcard(flashcard);
        return new ResponseEntity<>(createdFlashcard, HttpStatus.CREATED);
    }
}