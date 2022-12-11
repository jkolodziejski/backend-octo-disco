package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.put.backendoctodisco.entity.requests.FlashcardListRequest;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class FlashcardStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Statistic ID")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "ID of the flashcard", example = "1")
    @Column(name = "flashcard_id", nullable = false)
    private Long flashcard_id;

    @ApiModelProperty(notes = "ID of the user who took the quiz", example = "1", required = true)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @ApiModelProperty(notes = "Tells if user learned the flashcard", required = true)
    @Column(name = "learned", nullable = false)
    private Boolean learned;

    public FlashcardStatistics(User user, Long flashcard_id, Boolean learned) {
        this.flashcard_id=flashcard_id;
        this.learned=learned;
        this.user_id=user.getId();
    }
}
