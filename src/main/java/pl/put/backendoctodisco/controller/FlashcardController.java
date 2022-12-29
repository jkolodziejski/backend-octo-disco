package pl.put.backendoctodisco.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.AddAliasRequest;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.entity.responses.AllFlashcardsResponse;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.SingleTranslation;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.AliasService;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;
import pl.put.backendoctodisco.utils.Language;

import java.util.*;


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
            @ApiResponse(code = 400, message = "Nonexistent language or wrong translations request"),
            @ApiResponse(code = 403, message = "Token not found, token expired (error specified in the message) or permission missing"),
            @ApiResponse(code = 409, message = "Flashcard already exists")
    })
    @PostMapping
    private ResponseEntity<FlashcardResponse> createFlashcard(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody FlashcardRequest flashcardRequest) throws AuthorizationExceptionResponse, NonexistentLanguageException, FlashcardAlreadyExistsException, IncorrectFlashcardRequestException, NoPermissionsException {
        User foundUser = userService.findUserByAuthToken(authToken);
        FlashcardResponse flashcardResponse;
        AuthToken.validateToken(foundUser);
        if (!Language.contains(flashcardRequest.language)) {
            throw new NonexistentLanguageException();
        }
        if(flashcardRequest.is_global == null){
            flashcardRequest.is_global = false;
        }
        if(flashcardRequest.is_global && !foundUser.getPermissions().equals("admin")){
            throw new NoPermissionsException();
        }

        List<String> aliasLowercase = flashcardRequest.translation.stream().map(String::toLowerCase).toList();
        for(String alias: aliasLowercase){
            if(Collections.frequency(aliasLowercase, alias)>1){
                throw new IncorrectFlashcardRequestException();
            }
        }

        List<Flashcard> filteredFlashcards = flashcardService.findInUsersDictionary(foundUser, flashcardRequest);
        for(Flashcard card: filteredFlashcards){
            aliasService.checkAlias(card.getId(), flashcardRequest.translation);
        }
        Flashcard createdFlashcard = flashcardService.createFlashcard(new Flashcard(foundUser, flashcardRequest));
        for (String aliasRest : flashcardRequest.translation) {
            aliasService.createAlias(new Alias(aliasRest, createdFlashcard.getId()));
        }
        flashcardResponse = flashcardService.getFlashcardWithAlias(createdFlashcard);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete flashcard with all its data from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 400, message = "Nonexistent flashcard"),
            @ApiResponse(code = 403, message = "Token not found, token expired (error specified in the message) or flashcard not available"),
            @ApiResponse(code = 409, message = "Server error")
    })
    @DeleteMapping
    private ResponseEntity<HttpStatus> deleteFlashcard(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long flashcardId) throws AuthorizationExceptionResponse, FlashcardDoesNotExistException, ServerErrorException, ParameterIsMissingException, EntityNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        if (flashcardId == null) {
            throw new ParameterIsMissingException("flashcardId");
        }
        Optional<Flashcard> found = flashcardService.findById(flashcardId);
        if(found.isEmpty()){
            throw new FlashcardDoesNotExistException();
        }
        //TODO admins can delete global cards
        if(found.get().getIsGlobal() || !Objects.equals(found.get().getUserId(), foundUser.getId())){
            throw new EntityNotAvailableException("flashcard");
        }
        //TODO needs to be cascading
        if(!flashcardService.deleteFlashcard(flashcardId)){
            throw new ServerErrorException("Problem deleting flashcard. No entity was deleted.");
        }

        return new ResponseEntity<>(HttpStatus.OK, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from database by keyword with all alias paged",
            notes = "Returns pages of flashcard, alias and metadata")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully searched"),
            @ApiResponse(code = 400, message = "Wrong request"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/search")
    private ResponseEntity<AllFlashcardsResponse> getFlashcardsByKeyword(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken,
                                                                         @PageableDefault(value = 25) Pageable pageable,
                                                                         @ApiParam(name = "dictionary", allowableValues = "global, local, both", required = true) String dictionary,
                                                                         String keyword,
                                                                         @ApiParam(name = "language", allowableValues = "en", required = true) String language) throws AuthorizationExceptionResponse, ParameterIsMissingException, NonexistentLanguageException, NonexistentDictionaryException, PageIndexOutOfRangeException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);

        if (language == null) {
            throw new ParameterIsMissingException("language");
        }
        if(dictionary == null){
            throw new ParameterIsMissingException("dictionary");
        }
        if (!Language.contains(language)) {
            throw new NonexistentLanguageException();
        }
        if(keyword == null){
            keyword = "";
        }
        Page<Flashcard> foundFlashcards = flashcardService.getFlashcardsByKeyword(pageable, keyword, language, dictionary, foundUser.getId());
        if(foundFlashcards == null){
            throw new NonexistentDictionaryException();
        }
        if(pageable.getPageNumber() > foundFlashcards.getTotalPages() || pageable.getPageNumber() < 0){
            throw new PageIndexOutOfRangeException();
        }
        return new ResponseEntity<>(getListFlashcardsWithAlias(foundFlashcards), HttpStatus.OK);
    }

    private AllFlashcardsResponse getListFlashcardsWithAlias(Page<Flashcard> flashcards) {
        List<FlashcardResponse> flashcardListWithAlias = new ArrayList<>();
        List<Flashcard> listFlashcards = flashcards.getContent();
        for (Flashcard flashcard : listFlashcards) {
            List<String> foundedAlias = flashcardService.findAliasByWordId(flashcard.getId());
            FlashcardResponse flashcardResponse = new FlashcardResponse(flashcard, foundedAlias);
            flashcardListWithAlias.add(flashcardResponse);
        }

        return new AllFlashcardsResponse(flashcardListWithAlias, flashcards);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new aliases to the flashcard",
            notes = "Returns the updated flashcard")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 400, message = "Nonexistent flashcard"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message), flashcard not available for user"),
            @ApiResponse(code = 409, message = "Flashcard already exists")
    })
    @PutMapping("/translations")
    private ResponseEntity<FlashcardResponse> addAliasFlashcard(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody AddAliasRequest alias_request) throws AuthorizationExceptionResponse, NonexistentLanguageException, FlashcardAlreadyExistsException, FlashcardDoesNotExistException, EntityNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        aliasService.checkAlias(alias_request.flashcard_id, alias_request.translation);

        Optional<Flashcard> createdFlashcard = flashcardService.findById(alias_request.flashcard_id);
        if(createdFlashcard.isEmpty()){
            throw new FlashcardDoesNotExistException();
        }
        if(createdFlashcard.get().getIsGlobal()){
            if(!Objects.equals(foundUser.getPermissions(), "admin")){
                throw new EntityNotAvailableException("global flashcard");
            }
        }else{
            if(!Objects.equals(createdFlashcard.get().getUserId(), foundUser.getId())){
                throw new EntityNotAvailableException("flashcard");
            }
        }
        for (String aliasRest : alias_request.translation) {
            aliasService.createAlias(new Alias(aliasRest, createdFlashcard.get().getId()));
        }
        FlashcardResponse flashcardResponse = flashcardService.getFlashcardWithAlias(createdFlashcard.get());

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Search local flashcards that occur as often as given popularity and in given language",
            notes = "Returns list of every translation (1 word - 1 alias)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/popular")
    private ResponseEntity<List<SingleTranslation>> searchForPopular(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestParam(name = "language", required = false, defaultValue = "en") String language, @RequestParam(name = "min_popularity", required = false, defaultValue = "2") Integer min_popularity) throws AuthorizationExceptionResponse {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        if(language == null){
            language = "en";
        }
        if(min_popularity == null){
            min_popularity = 2;
        }

        return new ResponseEntity<>(flashcardService.findPopular(language, min_popularity), HttpStatus.OK);
    }
}