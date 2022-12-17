package pl.put.backendoctodisco.utils.quiz;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.utils.QuizQuestionType;

import javax.persistence.*;

@ToString
@Getter
public class QuizTypeQuestion extends QuizQuestion {

    @ApiModelProperty(notes = "ID of the flashcard used in question", required = true)
    @Column(name = "flashcard_id", nullable = false)
    private Long flashcard_id;

    @ApiModelProperty(notes = "Flashcard with possible translations", required = true)
    @Column(name = "flashcard", nullable = false)
    private FlashcardResponse flashcard;

    public QuizTypeQuestion(FlashcardResponse flashcard){
        super(QuizQuestionType.TYPE.name().toLowerCase());
        this.flashcard_id = flashcard.getId();
        this.flashcard = flashcard;
    }
}
