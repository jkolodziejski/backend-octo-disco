package pl.put.backendoctodisco.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.responses.FlashcardRequest;
import pl.put.backendoctodisco.service.FlashcardService;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new flashcard to database",
                    notes = "Returns the created flashcard")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created")
    })
    @PostMapping("/create")
    private ResponseEntity<Flashcard> createFlashcard(@RequestBody FlashcardRequest flashcardRequest) {
        Flashcard createdFlashcard = flashcardService.createFlashcard(new Flashcard((flashcardRequest)));
        return new ResponseEntity<>(createdFlashcard, HttpStatus.CREATED);
    }
}