package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.entity.responses.GetFlashcardsResponse;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/flashcard")
@CrossOrigin
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final UserService userService;

    public FlashcardController(FlashcardService flashcardService, UserService userService) {
        this.flashcardService = flashcardService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new flashcard to database",
                    notes = "Returns the created flashcard")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard already exists or nonexistent language.")
    })
    @PostMapping("/create")
    private ResponseEntity<Flashcard> createFlashcard(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody FlashcardRequest flashcardRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, FlashcardAlreadyExistsException, NonexistentLanguageException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(!Language.contains(flashcardRequest.language)){
            throw new NonexistentLanguageException();
        }

        List <Flashcard> foundFlashcards = flashcardService.findByWord(flashcardRequest.word);
        List <Flashcard> filteredFlashcards = foundFlashcards
                .stream().filter(card -> card.getIsGlobal() || Objects.equals(card.getUserId(), foundUser.getId())) //users dictionary
                .filter(card ->
                        card.getWord().equals(flashcardRequest.word)
                        && card.getLanguage().equals(flashcardRequest.language)
                        && card.getTranslation().equals(flashcardRequest.translation)  //same flashcards
                ).toList();

        if(!filteredFlashcards.isEmpty()){
            throw new FlashcardAlreadyExistsException();
        }

        Flashcard createdFlashcard = flashcardService.createFlashcard(new Flashcard(foundUser, flashcardRequest));
        return new ResponseEntity<>(createdFlashcard, HttpStatus.CREATED);
    }



    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from database by page",
            notes = "Returns list of flashcard and size")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The resource has been fetched and transmitted in the message body"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/pages")
    private ResponseEntity<Object> getFlashcards(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Choice choice, @PageableDefault(value = 25) Pageable pageable ) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        System.out.println(choice);
        List<Flashcard> flashcardList ;
        if(choice.equals(Choice.Global)){
            flashcardList =  flashcardService.getAllFlashcardsGlobal(pageable);
        }
        else if(choice.equals(Choice.Local)) {
            flashcardList = flashcardService.getFlashcardsUser(foundUser.getId(),pageable);
        }
        else if(choice.equals(Choice.Both)){
            flashcardList = flashcardService.getAllFlashcards(pageable);
        }
        else {
            flashcardList = new ArrayList<>();
        }
        return  new ResponseEntity<>(new GetFlashcardsResponse(flashcardList).generateResponse(), HttpStatus.OK);
    }
}