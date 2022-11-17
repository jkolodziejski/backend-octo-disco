package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import pl.put.backendoctodisco.entity.Flashcard;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllFlashcardsResponse  {

    @ApiModelProperty(notes = "List of flashcards", example = """
            "size": 1,
                "flashcards": [
                    {
                        "id": 1,
                        "language": "pl",
                        "word": "Poland",
                        "isGlobal": false,
                        "userId": 1,
                        "alias": [
                            "test",
                            "test1"
                        ]
                    }
               ]
            }""")
    private final List<FlashcardResponse> flashcards;

    @ApiModelProperty(notes = "Size of flashcard list", example = "2")
    private final Integer flashcardsSize;


    public AllFlashcardsResponse(List<FlashcardResponse> flashcards) {
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
