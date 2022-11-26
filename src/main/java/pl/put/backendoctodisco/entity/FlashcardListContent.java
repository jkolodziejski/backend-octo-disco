package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.requests.AddToFlashcardListRequest;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FlashcardListContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "ID of the flashcard", example = "1", required = true)
    @Column(name = "flashcard_id", nullable = true)
    private Long flashcardId;

    @ApiModelProperty(notes = "ID of the flashcard list", example = "1", required = true)
    @Column(name = "list_id", nullable = true)
    private Long listId;

    public FlashcardListContent(AddToFlashcardListRequest request) {
        flashcardId = request.flashcard_id;
        listId = request.list_id;
    }
}
