package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.Alias;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.entity.responses.AllFlashcardsResponse;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.AliasService;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/flashcard")
@CrossOrigin
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final UserService userService;
    private final AliasService aliasService;

    public FlashcardController(FlashcardService flashcardService, UserService userService, AliasService aliasService) {
        this.flashcardService = flashcardService;
        this.userService = userService;
        this.aliasService = aliasService;
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
    private ResponseEntity<FlashcardResponse> createFlashcard(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody FlashcardRequest flashcardRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, NonexistentLanguageException, AliasAlreadyExistsException {
        User foundUser = userService.findUserByAuthToken(authToken);
        FlashcardResponse flashcardResponse;
        AuthToken.validateToken(foundUser);
        if(!Language.contains(flashcardRequest.language)){
            throw new NonexistentLanguageException();
        }

        List <Flashcard> foundFlashcards = flashcardService.findByWord(flashcardRequest.word);
        List <Flashcard> filteredFlashcards = foundFlashcards
                .stream().filter(card -> card.getIsGlobal() || Objects.equals(card.getUserId(), foundUser.getId())) //users dictionary
                .filter(card ->
                        card.getWord().equals(flashcardRequest.word)
                        && card.getLanguage().equals(flashcardRequest.language) //same flashcards 
                ).toList();
        if(filteredFlashcards.size() == 1){
            aliasService.checkAndcreateAlias(filteredFlashcards.get(0).getId(),flashcardRequest.translation);
            flashcardResponse = aliasService.getFlashcardWithAlias(filteredFlashcards.get(0));
        }
        else{
            Flashcard createdFlashcard = flashcardService.createFlashcard(new Flashcard(foundUser, flashcardRequest));
            for (String aliasRest : flashcardRequest.translation){
                aliasService.createAlias(new Alias(aliasRest, createdFlashcard.getId()));
            }
            flashcardResponse = aliasService.getFlashcardWithAlias(createdFlashcard);
        }
        return new ResponseEntity<>(flashcardResponse , HttpStatus.CREATED);
    }



    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from database with all alias paged",
            notes = "Returns list of flashcard, alias and size")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The resource has been fetched and transmitted in the message body"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/all")
    private ResponseEntity<Map<String, Object> > getFlashcards(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Choice choice, @PageableDefault(value = 25) Pageable pageable , String language) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        if(language == null || choice == null){
            throw new ParameterIsMissingException();
        }
        Page<Flashcard> flashcardList ;
        if(choice.equals(Choice.Global)){
            flashcardList =  flashcardService.getAllFlashcardsGlobal(pageable,language);
        }
        else if(choice.equals(Choice.Local)) {
            flashcardList = flashcardService.getFlashcardsUser(foundUser.getId(),pageable,language);
        }
        else {
            flashcardList = flashcardService.getAllFlashcards(pageable);
        }

        return  new ResponseEntity<>(getListFlashcardsWithAlias(flashcardList), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from database by keyword with all alias paged",
            notes = "Returns list of flashcard, alias and size")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard already exists or nonexistent language.")
    })
    @GetMapping("/keyword")
    private ResponseEntity<Map<String,Object>> getFlashcardsByKeyword(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @PageableDefault(value = 1) Pageable pageable , String keyword,String language) throws TokenNotFoundException, TokenUnauthorizedException, TokenExpiredException, ParameterIsMissingException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        if(language == null || keyword == null){
            throw new ParameterIsMissingException();
        }
        return new ResponseEntity<>(getListFlashcardsWithAlias(flashcardService.getFlashcardsByKyeword(pageable,keyword,language)), HttpStatus.OK);
    }

    private Map<String, Object> getListFlashcardsWithAlias(Page<Flashcard> flashcards){
        List<FlashcardResponse> flashcardListWithAlias = new ArrayList<>();
        List<Flashcard> listFlashcards = flashcards.getContent();
        for ( Flashcard flashcard : listFlashcards ) {
            List<String> foundedAlias = aliasService.findAliasbyWordId(flashcard.getId());
            FlashcardResponse flashcardResponse = new FlashcardResponse(flashcard,foundedAlias);
            flashcardListWithAlias.add(flashcardResponse);
        }

        return new AllFlashcardsResponse(flashcardListWithAlias,flashcards).generateResponse();
    }
}