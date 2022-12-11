package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;
import pl.put.backendoctodisco.entity.Flashcard;

import java.util.List;

@Getter
@ToString
public class AllFlashcardsResponse {

    @ApiModelProperty(notes = "Metadata of the response ")
    private final PagingMetadata metadata;

    @ApiModelProperty(notes = "List of flashcards")
    private final List<FlashcardResponse> flashcards;


    public AllFlashcardsResponse(List<FlashcardResponse> flashcards) {
        this.metadata = new PagingMetadata((long) flashcards.size(), 1);
        this.flashcards = flashcards;

    }

    public AllFlashcardsResponse(List<FlashcardResponse> flashcards, Page<Flashcard> page) {
        this.metadata = new PagingMetadata(page.getTotalElements(), page.getTotalPages());
        this.flashcards = flashcards;
    }
}
