package pl.put.backendoctodisco.utils.quiz;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.utils.QuizQuestionType;

import javax.persistence.*;
import java.util.List;

@ToString
@Getter
public class QuizChooseQuestion extends QuizQuestion {

    @ApiModelProperty(notes = "ID of the flashcard used in question", required = true)
    @Column(name = "flashcard_id", nullable = false)
    private Long flashcard_id;

    @ApiModelProperty(notes = "Flashcard word with one of the correct answers", required = true)
    @Column(name = "answer", nullable = false)
    private QuizAnswer answer;

    @ApiModelProperty(notes = "False options to choose from beside alias of the flashcard", example = "type", allowableValues = "type, choose, connect", required = true)
    @Column(name = "false_options", nullable = false)
    private List<String> false_options;

    public QuizChooseQuestion(FlashcardResponse flashcard, List<String> falseOptions){
        super(QuizQuestionType.CHOOSE.name().toLowerCase());
        this.flashcard_id = flashcard.getId();
        this.answer = new QuizAnswer(flashcard);
        this.false_options = falseOptions;
    }

}
