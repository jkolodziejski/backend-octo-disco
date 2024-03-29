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
import pl.put.backendoctodisco.entity.requests.UpdateFlashcardsInListRequest;
import pl.put.backendoctodisco.entity.requests.AddToFlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;
import pl.put.backendoctodisco.entity.responses.AllFlashcardsResponse;
import pl.put.backendoctodisco.entity.responses.FlashcardListsResponse;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.exceptions.*;
import pl.put.backendoctodisco.service.AliasService;
import pl.put.backendoctodisco.service.FlashcardListService;
import pl.put.backendoctodisco.service.FlashcardService;
import pl.put.backendoctodisco.service.UserService;
import pl.put.backendoctodisco.utils.AuthToken;

import java.util.*;

@RestController
@RequestMapping("/cardlist")
@CrossOrigin
public class FlashcardListController {

    private final FlashcardListService flashcardListService;
    private final FlashcardService flashcardService;
    private final UserService userService;
    private final AliasService aliasService;

    public FlashcardListController(FlashcardListService flashcardListService, FlashcardService flashcardService, UserService userService, AliasService aliasService) {
        this.flashcardListService = flashcardListService;
        this.flashcardService = flashcardService;
        this.userService = userService;
        this.aliasService = aliasService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add a new flashcard list to database",
            notes = "Returns the created flashcard list")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard list already exists.")
    })
    @PostMapping
    private ResponseEntity<FlashcardListInfo> createFlashcardList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody FlashcardListRequest flashcardListRequest) throws AuthorizationExceptionResponse, FlashcardListAlreadyExistsException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        Optional<FlashcardListInfo> foundFlashcardList = flashcardListService.findByName(foundUser.getId(), flashcardListRequest.name);

        if (foundFlashcardList.isPresent()) {
            throw new FlashcardListAlreadyExistsException();
        }

        FlashcardListInfo createdCardList = flashcardListService.createFlashcardList(new FlashcardListInfo(foundUser, flashcardListRequest));
        return new ResponseEntity<>(createdCardList, HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Update flashcard list in the database",
            notes = "Returns status")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "List ID missing in params or list does not exists"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message) or list not available"),
            @ApiResponse(code = 500, message = "Flashcard not in database.")
    })
    @PutMapping
    private ResponseEntity<HttpStatus> updateFlashcardList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long list_id, @RequestBody FlashcardListRequest flashcardListRequest) throws AuthorizationExceptionResponse, ParameterIsMissingException, FlashcardListDoesNotExistException, EntityNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if(list_id == null){
            throw new ParameterIsMissingException("list_id");
        }

        Optional<FlashcardListInfo> foundFlashcardList = flashcardListService.findListById(list_id);
        if (foundFlashcardList.isEmpty()) {
            throw new FlashcardListDoesNotExistException();
        }
        if(!Objects.equals(foundFlashcardList.get().getUserId(), foundUser.getId())){
            throw new EntityNotAvailableException("flashcard list");
        }
        flashcardListService.updateFlashcardList(list_id, new FlashcardListInfo(foundUser, flashcardListRequest));

        return new ResponseEntity<>(HttpStatus.ACCEPTED, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get users lists of flashcards",
            notes = "Returns info about users flashcard lists")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found users lists"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/lists")
    private ResponseEntity<ArrayList<FlashcardListsResponse>> getFlashcardLists(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken) throws AuthorizationExceptionResponse {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        ArrayList<FlashcardListInfo> lists = new ArrayList<>(flashcardListService.findUsersLists(foundUser));
        ArrayList<FlashcardListsResponse> response = new ArrayList<>(lists.stream().map(info -> {
            return new FlashcardListsResponse(info, flashcardListService.countByListId(info.getId()));
        }).toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete flashcard with all its data from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 400, message = "Nonexistent flashcard list"),
            @ApiResponse(code = 403, message = "Token not found, token expired (error specified in the message) or flashcard list not available"),
            @ApiResponse(code = 409, message = "Server error")
    })
    @DeleteMapping
    private ResponseEntity<HttpStatus> deleteFlashcardList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long listId) throws AuthorizationExceptionResponse, ServerErrorException, ParameterIsMissingException, FlashcardListDoesNotExistException, FlashcardListNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);
        if (listId == null) {
            throw new ParameterIsMissingException("listId");
        }
        Optional<FlashcardListInfo> found = flashcardListService.findListById(listId);
        if(found.isEmpty()){
            throw new FlashcardListDoesNotExistException();
        }
        if(!Objects.equals(found.get().getUserId(), foundUser.getId())){
            throw new FlashcardListNotAvailableException();
        }
        if(!flashcardListService.deleteList(listId)){
            throw new ServerErrorException("Problem deleting flashcard list. No entity was deleted.");
        }

        return new ResponseEntity<>(HttpStatus.OK, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Add a flashcard to the list in the database",
            notes = "Returns ID's of both flashcard and list (and the connection entity ID as well).")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully added to the list"),
            @ApiResponse(code = 400, message = "Flashcard or list not found (error specified in the message)"),
            @ApiResponse(code = 403, message = "Token not found, token expired or user not authorized for the operation (error specified in the message)"),
            @ApiResponse(code = 409, message = "Flashcard is already present in the list.")
    })
    @PostMapping("/card")
    private ResponseEntity<FlashcardListContent> addFlashcardToList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody AddToFlashcardListRequest addToFlashcardListRequest) throws AuthorizationExceptionResponse, FlashcardAlreadyInListException, FlashcardListDoesNotExistException, FlashcardDoesNotExistException, EntityNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        if (flashcardListService.findListById(addToFlashcardListRequest.list_id).isEmpty()) {
            throw new FlashcardListDoesNotExistException();
        }
        Optional<Flashcard> foundFlashcard = flashcardService.findById(addToFlashcardListRequest.flashcard_id);
        if (foundFlashcard.isEmpty()) {
            throw new FlashcardDoesNotExistException();
        } else {
            if (!foundFlashcard.get().getIsGlobal() && !Objects.equals(foundFlashcard.get().getUserId(), foundUser.getId())) {
                throw new EntityNotAvailableException("flashcard");
            }
        }

        FlashcardListContent flashcardListContent = new FlashcardListContent(addToFlashcardListRequest);
        Optional<FlashcardListContent> foundFlashcardListContent = flashcardListService.findCardInList(flashcardListContent);

        if (foundFlashcardListContent.isPresent()) {
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
            @ApiResponse(code = 400, message = "Flashcard or list not found (error specified in the message)"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @PostMapping("/cards")
    private ResponseEntity<ArrayList<FlashcardListContent>> addFlashcardsToList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody UpdateFlashcardsInListRequest request) throws AuthorizationExceptionResponse, FlashcardListDoesNotExistException, FlashcardDoesNotExistException, EntityNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        ArrayList<FlashcardListContent> response = new ArrayList<>();

        if (flashcardListService.findListById(request.list_id).isEmpty()) {
            throw new FlashcardListDoesNotExistException();
        }

        for (Long req : request.flashcard_ids) {

            FlashcardListContent flashcardListContent = new FlashcardListContent(request.list_id, req);
            Optional<FlashcardListContent> foundFlashcardListContent = flashcardListService.findCardInList(flashcardListContent);

            Optional<Flashcard> foundFlashcard = flashcardService.findById(req);
            if (foundFlashcard.isEmpty()) {
                throw new FlashcardDoesNotExistException();
            } else {
                if (!foundFlashcard.get().getIsGlobal() && !Objects.equals(foundFlashcard.get().getUserId(), foundUser.getId())) {
                    throw new EntityNotAvailableException("flashcard");
                }
            }

            if (foundFlashcardListContent.isEmpty()) {
                response.add(flashcardListService.addToFlashcardList(flashcardListContent));
            }
        }

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete flashcard with all its data from database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 400, message = "Nonexistent flashcard list"),
            @ApiResponse(code = 403, message = "Token not found, token expired (error specified in the message) or flashcard list not available"),
            @ApiResponse(code = 409, message = "Server error")
    })
    @DeleteMapping("/cards")
    private ResponseEntity<HttpStatus> deleteFlashcardList(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, @RequestBody UpdateFlashcardsInListRequest request) throws AuthorizationExceptionResponse, ServerErrorException, ParameterIsMissingException, FlashcardListDoesNotExistException, FlashcardListNotAvailableException {
        User foundUser = userService.findUserByAuthToken(authToken);
        AuthToken.validateToken(foundUser);

        ArrayList<FlashcardListContent> response = new ArrayList<>();

        if (flashcardListService.findListById(request.list_id).isEmpty()) {
            throw new FlashcardListDoesNotExistException();
        }

        for (Long req : request.flashcard_ids) {

            FlashcardListContent flashcardListContent = new FlashcardListContent(request.list_id, req);
            Optional<FlashcardListContent> foundFlashcardListContent = flashcardListService.findCardInList(flashcardListContent);

            if(!flashcardListService.deleteFromList(foundFlashcardListContent.get().getId())){
                throw new ServerErrorException("Problem deleting flashcard list, but some might have been deleted.");
            }
        }

        return new ResponseEntity<>(HttpStatus.OK, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get flashcards from list",
            notes = "Returns flashcards from list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully found flashcards"),
            @ApiResponse(code = 403, message = "Token not found or token expired (error specified in the message)")
    })
    @GetMapping("/cards")
    private AllFlashcardsResponse getFlashcards(@RequestHeader(name = HttpHeaders.AUTHORIZATION, defaultValue = "") String authToken, Long list_id) throws AuthorizationExceptionResponse {
        User foundUser = userService.findUserByAuthToken(authToken);

        AuthToken.validateToken(foundUser);

        List<FlashcardResponse> flashcards = flashcardService.getFlashcardsFromList(list_id);
        AllFlashcardsResponse response = new AllFlashcardsResponse(flashcards);

        return response;
    }
}