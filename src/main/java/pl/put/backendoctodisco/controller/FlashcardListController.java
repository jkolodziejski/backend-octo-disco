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
import pl.put.backendoctodisco.entity.requests.AddListToFlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.AddToFlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.FlashcardListService;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/cardlist")
@CrossOrigin
public class FlashcardListController {

    private final FlashcardListService flashcardListService;
    private final FlashcardService flashcardService;
    private final UserService userService;

    public FlashcardListController(FlashcardListService flashcardListService, FlashcardService flashcardService, UserService userService) {
        this.flashcardListService = flashcardListService;
        this.flashcardService = flashcardService;
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

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Add a flashcard to the list in the database",
            notes = "Returns ID's of both flashcard and list (and the connection entity ID as well).")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully added to the list"),
            @ApiResponse(code = 403, message = "Token not found, token expired or user not authorized for the operation (error specified in the message)"),
            @ApiResponse(code = 404, message = "Flashcard or list not found (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard is already present in the list.")
    })
    @PostMapping("/add_card")
    private ResponseEntity<FlashcardListContent> addFlashcardToList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody AddToFlashcardListRequest addToFlashcardListRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, FlashcardAlreadyInListException, FlashcardListDoesNotExistException, FlashcardDoesNotExistException, FlashcardNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(flashcardListService.findListById(addToFlashcardListRequest.list_id).isEmpty()){
            throw new FlashcardListDoesNotExistException();
        }
        Optional<Flashcard> foundFlashcard = flashcardService.findById(addToFlashcardListRequest.flashcard_id);
        if(foundFlashcard.isEmpty()){
            throw new FlashcardDoesNotExistException();
        }else{
            if(!foundFlashcard.get().getIsGlobal() && !Objects.equals(foundFlashcard.get().getUserId(), foundUser.getId())){
                throw new FlashcardNotAvailableException();
            }
        }

        FlashcardListContent flashcardListContent = new FlashcardListContent(addToFlashcardListRequest);
        Optional<FlashcardListContent> foundFlashcardListContent = flashcardListService.findCardInList(flashcardListContent);

        if(foundFlashcardListContent.isPresent()){
            throw new FlashcardAlreadyInListException();
        }

        FlashcardListContent createdListContent = flashcardListService.addToFlashcardList(flashcardListContent);
        return new ResponseEntity<>(createdListContent, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Add a list of flashcards to the list in the database",
            notes = "Returns ID's of both flashcard and list (and the connection entity ID as well) of every request that was approved.")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully added to the list"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 404, message = "Flashcard or list not found (error specified in the message)"),
    })
    @PostMapping("/add_cards")
    private ResponseEntity<ArrayList<FlashcardListContent>> addFlashcardsToList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody AddListToFlashcardListRequest addToFlashcardListRequest) throws TokenNotFoundException, TokenExpiredException, TokenUnauthorizedException, FlashcardAlreadyInListException, FlashcardListDoesNotExistException, FlashcardDoesNotExistException, FlashcardNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        ArrayList<FlashcardListContent> response = new ArrayList<>();

        for(AddToFlashcardListRequest req : addToFlashcardListRequest.flashcards_requests){

            FlashcardListContent flashcardListContent = new FlashcardListContent(req);
            Optional<FlashcardListContent> foundFlashcardListContent = flashcardListService.findCardInList(flashcardListContent);

            //TODO not sure about the exceptions there
            if(flashcardListService.findListById(req.list_id).isEmpty()){
                throw new FlashcardListDoesNotExistException();
            }
            Optional<Flashcard> foundFlashcard = flashcardService.findById(req.flashcard_id);
            if(foundFlashcard.isEmpty()){
                throw new FlashcardDoesNotExistException();
            }else{
                if(!foundFlashcard.get().getIsGlobal() && !Objects.equals(foundFlashcard.get().getUserId(), foundUser.getId())){
                    throw new FlashcardNotAvailableException();
                }
            }

            if(foundFlashcardListContent.isEmpty()){
                response.add(flashcardListService.addToFlashcardList(flashcardListContent));
            }
        }

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}