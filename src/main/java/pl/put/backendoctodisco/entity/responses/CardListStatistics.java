package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.utils.quiz.QuizQuestion;

import javax.persistence.Column;
import java.util.List;

@ToString
@Getter
public class CardListStatistics {

    @ApiModelProperty(notes = "Number of flashcard learned", required = true)
    @Column(name = "learned", nullable = false)
    private final int learned;

    @ApiModelProperty(notes = "Number of flashcard learned", required = true)
    @Column(name = "learned", nullable = false)
    private final int not_learned;

    @ApiModelProperty(notes = "Number of flashcard learned", required = true)
    @Column(name = "learned", nullable = false)
    private final int not_attempted;

    public CardListStatistics(int learned, int not_learned, int not_attempted){
        this.learned = learned;
        this.not_learned = not_learned;
        this.not_attempted = not_attempted;
    }
}
