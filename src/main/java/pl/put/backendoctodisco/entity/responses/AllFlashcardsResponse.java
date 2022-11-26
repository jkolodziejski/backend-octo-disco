package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final long size;
    private final Integer totalPages;

    public AllFlashcardsResponse(List<FlashcardResponse> flashcards){
        this.flashcards = flashcards;
        this.size = flashcards.size();
        totalPages = 1;
    }

    public AllFlashcardsResponse(List<FlashcardResponse> flashcards, Page<Flashcard> page) {
        this.flashcards = flashcards;
        this.size = page.getTotalElements();
        this.totalPages = page.getTotalPages();

    }

    public Map<String, Object>  generateResponse(){
        Map<String, Object> map  = new HashMap<>();
        map.put("flashcards",flashcards);
        map.put("size",size);
        map.put("Number of pages", totalPages);
        return  map;
    }

}
