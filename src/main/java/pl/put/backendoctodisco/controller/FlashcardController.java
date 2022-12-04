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
            @ApiResponse(code = 400, message = "Nonexistent language"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Alias already exists")
    })
    @PostMapping("/create")
    private ResponseEntity<FlashcardResponse> createFlashcard(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody FlashcardRequest flashcardRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, NonexistentLanguageException, FlashcardAlreadyExistsException {
        User foundUser = userService.findUserByAuthToken(authToken);
        FlashcardResponse flashcardResponse;
        AuthToken.validateToken(foundUser);
        if (!Language.contains(flashcardRequest.language)) {
            throw new NonexistentLanguageException();
        }

        List<Flashcard> filteredFlashcards = flashcardService.findInUsersDictionary(foundUser, flashcardRequest);
        if (!filteredFlashcards.isEmpty()) {
            aliasService.checkAndcreateAlias(filteredFlashcards.get(0).getId(), flashcardRequest.translation);
            flashcardResponse = flashcardService.getFlashcardWithAlias(filteredFlashcards.get(0));
        } else {
            Flashcard createdFlashcard = flashcardService.createFlashcard(new Flashcard(foundUser, flashcardRequest));
            for (String aliasRest : flashcardRequest.translation) {
                aliasService.createAlias(new Alias(aliasRest, createdFlashcard.getId()));
            }
            flashcardResponse = flashcardService.getFlashcardWithAlias(createdFlashcard);
        }
        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    /*
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from database with all alias paged",
            notes = "Returns list of flashcard, alias and metadata")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The resource has been fetched and transmitted in the message body"),
            @ApiResponse(code = 400, message = "Wrong request"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/all")
    private ResponseEntity<AllFlashcardsResponse> getFlashcards(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, String choice, @PageableDefault(value = 25) Pageable pageable, String language) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, ParameterIsMissingException, NonexistentLanguageException, NonexistentChoiceException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        if (language == null || choice == null) {
            throw new ParameterIsMissingException();
        }
        if (!Choice.contains(choice)) {
            throw new NonexistentChoiceException();
        }
        if (!Language.contains(language)) {
            throw new NonexistentLanguageException();
        }

        Page<Flashcard> flashcardList;
        if (Choice.valueOf(choice.toUpperCase()).equals(Choice.GLOBAL)) {
            flashcardList = flashcardService.getAllFlashcardsGlobal(pageable, language);
        } else if (Choice.valueOf(choice.toUpperCase()).equals(Choice.LOCAL)) {
            flashcardList = flashcardService.getFlashcardsUser(foundUser.getId(), pageable, language);
        } else {
            flashcardList = flashcardService.getAllFlashcards(pageable);
        }

        return new ResponseEntity<>(getListFlashcardsWithAlias(flashcardList), HttpStatus.OK);
    }


     */
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
                                                                         @ApiParam(name = "language", allowableValues = "en", required = true) String language) throws TokenNotFoundException, TokenUnauthorizedException, TokenExpiredException, ParameterIsMissingException, NonexistentLanguageException, NonexistentDictionaryException, PageIndexOutOfRangeException {
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
}