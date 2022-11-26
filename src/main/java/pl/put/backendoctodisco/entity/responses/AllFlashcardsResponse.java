package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import pl.put.backendoctodisco.entity.Flashcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@ToString
public class AllFlashcardsResponse {

    @ApiModelProperty(notes = "Metadata of Response ")
    private final Metadata metadata;

    @ApiModelProperty(notes = "List of flashcards with Alias")
    private final List<FlashcardResponse> flashcards;


    public AllFlashcardsResponse(List<FlashcardResponse> flashcards) {
        this.metadata = new Metadata(flashcards.size(), 1);
        this.flashcards = flashcards;

    }

    public AllFlashcardsResponse(List<FlashcardResponse> flashcards, Page<Flashcard> page) {
        this.metadata = new Metadata(page.getNumberOfElements(), page.getTotalPages());
        this.flashcards = flashcards;


    }


    public Map<String, Object> generateResponse() {
        Map<String, Object> map = new HashMap<>();
        map.put("flashcards", flashcards);
        map.put("Metadate", metadata);
        return map;
    }


}
