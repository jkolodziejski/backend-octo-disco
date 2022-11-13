package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.ResponseEntity;
import pl.put.backendoctodisco.entity.Flashcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetFlashcardsResponse {

    @ApiModelProperty(notes = "List of flashcards", example = """
            {
                        "id": 1,
                        "language": "pl",
                        "word": "Poland",
                        "translation": "Polska",
                        "isGlobal": false,
                        "userId": 1
                    },
                    {
                        "id": 50,
                        "language": "en",
                        "word": "polandmountain",
                        "translation": "polskagurom",
                        "isGlobal": false,
                        "userId": 1
                    }""")
    private final List<Flashcard> flashcards;

    @ApiModelProperty(notes = "Size of flashcard list", example = "2")
    private final Integer flashcardsSize;


    public GetFlashcardsResponse(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.flashcardsSize = flashcards.size();
    }

    public Map<String, Object>  generateResponse(){
        Map<String, Object> map  = new HashMap<>();
        map.put("flashcards",flashcards);
        map.put("size",flashcardsSize);

        return  map;
    }

}
