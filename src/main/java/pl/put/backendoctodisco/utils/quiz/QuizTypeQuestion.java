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

    @ApiModelProperty(notes = "Flashcard with possible translations", required = true)
    @Column(name = "flashcard", nullable = false)
    private FlashcardResponse flashcard;

    public QuizTypeQuestion(FlashcardResponse flashcard){
        super(QuizQuestionType.TYPE.name());
        this.flashcard = flashcard;
    }
}
