package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FlashcardStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Statistic ID",)
    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID of the flashcard", example = "1")
    private Long flashcard_id;

    @ApiModelProperty(notes = "ID of the user who took the quiz", example = "1", required = true)
    private Long userId;

    @ApiModelProperty(notes = "Tells if user learned the flashcard", required = true)
    private Boolean learned;

    public FlashcardStatistics(User user, Long flashcard_id, Boolean learned) {
        this.flashcard_id=flashcard_id;
        this.learned=learned;
        this.userId=user.getId();
    }
}
