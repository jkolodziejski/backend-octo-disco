package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import pl.put.backendoctodisco.entity.Flashcard;

import java.util.List;

@ToString
@Getter
public class FlashcardResponse {

    @ApiModelProperty(notes = "Flashcard ID", example = "1", required = false, hidden = true)
    private final Long id;

    @ApiModelProperty(notes = "Language of the word", allowableValues = "pl, en", example = "pl", required = true)
    private final String language;

    @ApiModelProperty(notes = "Word to learn", example = "Poland", required = true)
    private final String word;


    @ApiModelProperty(notes = "Indicates if flashcard is globally available for users", example = "0", required = true)
    private final Boolean isGlobal;

    @ApiModelProperty(notes = "ID of the user who created the flashcard", example = "1", required = true)
    private final Long userId;

    private final List<String> alias;


    public FlashcardResponse(Flashcard flashcard, List<String> alias) {
        this.id = flashcard.getId();
        this.language = flashcard.getLanguage();
        this.word = flashcard.getWord();
        this.isGlobal = flashcard.getIsGlobal();
        this.userId = flashcard.getUserId();
        this.alias = alias;
    }
}