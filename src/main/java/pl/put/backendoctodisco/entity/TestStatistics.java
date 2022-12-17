package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import pl.put.backendoctodisco.utils.QuestionId;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class TestStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Statistic ID")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "ID of the question", example = "1")
    @Column(name = "question_id", nullable = false)
    private Long question_id;

    @ApiModelProperty(notes = "Type of the question", example = "choose")
    @Column(name = "question_type", nullable = false)
    private String question_type;

    @ApiModelProperty(notes = "ID of the user who took the quiz", example = "1", required = true)
    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @ApiModelProperty(notes = "Tells if user learned the flashcard", required = true)
    @Column(name = "learned", nullable = false)
    private Boolean learned;

    public TestStatistics(User user, QuestionId qid, Boolean learned) {
        this.question_id=qid.id;
        this.question_type=qid.type;
        this.learned=learned;
        this.user_id=user.getId();
    }
}
