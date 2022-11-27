package pl.put.backendoctodisco.utils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.QuizQuestion;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;

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
