package pl.put.backendoctodisco.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.service.FlashcardService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
    private ResponseEntity<Flashcard> createFlashcard(@RequestBody(description = "Flashcard info") Flashcard flashcard) {
        Flashcard createdFlashcard = flashcardService.createFlashcard(flashcard);
        return new ResponseEntity<>(createdFlashcard, HttpStatus.CREATED);
    }
}