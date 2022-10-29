package pl.put.backendoctodisco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.service.FlashcardService;

@RestController
public class FlashcardController {

    private final FlashcardService flashcardService;


    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }


    @PostMapping("flashcard/create")
    private ResponseEntity<Flashcard> createFlashcard(@RequestBody Flashcard flashcard){
        Flashcard createdFlashcard = flashcardService.createFlashcard(flashcard);
        return new  ResponseEntity<>(createdFlashcard,HttpStatus.CREATED);
    }

}
