package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FlashcardListInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Flashcard list ID", example = "1")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "ID of the user who created the flashcard list", example = "1", required = true)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ApiModelProperty(notes = "Flashcard list name", example = "Monday exam - phrases", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    public FlashcardListInfo(User user, FlashcardListRequest flashcardListRequest){
        this.userId = user.getId();
        this.name = flashcardListRequest.name;
    }
}
