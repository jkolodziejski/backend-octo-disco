package pl.put.backendoctodisco.entity.responses;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;

import javax.persistence.*;

@ToString
@Getter
public class FlashcardListsResponse {

    @ApiModelProperty(notes = "Flashcard list ID", example = "1")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Flashcard list name", example = "Monday exam", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(notes = "Flashcard list name", example = "Food and drinks", required = true)
    @Column(name = "description")
    private String description;

    @ApiModelProperty(notes = "Number of flashcards in the list", example = "12", required = true)
    @Column(name = "size", nullable = false)
    private int size;

    public FlashcardListsResponse(FlashcardListInfo listInfo, int size) {
        this.id = listInfo.getId();
        this.name = listInfo.getName();
        this.description = listInfo.getDescription();
        this.size = size;
    }
}
