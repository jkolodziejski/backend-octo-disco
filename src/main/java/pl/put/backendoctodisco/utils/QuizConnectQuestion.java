package pl.put.backendoctodisco.utils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.QuizQuestion;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;

import javax.persistence.Column;
import java.util.List;

@ToString
@Getter
public class QuizConnectQuestion extends QuizQuestion {

    @ApiModelProperty(notes = "Flashcard words with one of the correct answers to be connected", required = true)
    @Column(name = "answers", nullable = false)
    private List<QuizAnswer> answers;

    public QuizConnectQuestion(List<FlashcardResponse> flashcards){
        super(QuizQuestionType.CONNECT.name());
        this.answers = flashcards.stream().map(card -> new QuizAnswer(card)).toList();
    }

}
