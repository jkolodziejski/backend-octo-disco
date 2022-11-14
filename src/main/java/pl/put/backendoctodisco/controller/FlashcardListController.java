package pl.put.backendoctodisco.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.FlashcardListContent;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.AddToFlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.FlashcardListService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.Optional;

@RestController
@RequestMapping("/cardlist")
@CrossOrigin
public class FlashcardListController {

    private final FlashcardListService flashcardListService;
    private final UserService userService;

    public FlashcardListController(FlashcardListService flashcardListService, UserService userService) {
        this.flashcardListService = flashcardListService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new flashcard list to database",
                    notes = "Returns the created flashcard list")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard list already exists.")
    })
    @PostMapping("/create")
    private ResponseEntity<FlashcardListInfo> createFlashcardList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody FlashcardListRequest flashcardListRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, FlashcardListAlreadyExistsException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        Optional<FlashcardListInfo> foundFlashcardList = flashcardListService.findByName(foundUser.getId(), flashcardListRequest.name);

        if(foundFlashcardList.isPresent()){
            throw new FlashcardListAlreadyExistsException();
        }

        FlashcardListInfo createdCardList = flashcardListService.createFlashcardList(new FlashcardListInfo(foundUser, flashcardListRequest));
        return new ResponseEntity<>(createdCardList, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new flashcard to the list in the database",
            notes = "Returns ID's of both flashcard and list (and the connection entity ID as well)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard is already present in the list.")
    })
    @PostMapping("/add_card")
    private ResponseEntity<FlashcardListContent> addFlashcardToList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody AddToFlashcardListRequest addToFlashcardListRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, FlashcardAlreadyInListException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        FlashcardListContent flashcardListContent = new FlashcardListContent(addToFlashcardListRequest);
        Optional<FlashcardListContent> foundFlashcardListContent = flashcardListService.findCardInList(flashcardListContent);

        if(foundFlashcardListContent.isPresent()){
            throw new FlashcardAlreadyInListException();
        }

        FlashcardListContent createdListContent = flashcardListService.addToFlashcardList(flashcardListContent);
        return new ResponseEntity<>(createdListContent, HttpStatus.CREATED);
    }
}