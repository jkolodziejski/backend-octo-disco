package pl.put.backendoctodisco.utils.quiz;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;

import javax.persistence.Column;
import java.util.List;
import java.util.Random;

@ToString
@Getter
public class QuizAnswer {

    @ApiModelProperty(notes = "Flashcard with possible translations", required = true)
    @Column(name = "flashcard", nullable = false)
    private String word;

    @ApiModelProperty(notes = "False options to choose from beside alias of the flashcard", example = "type", allowableValues = "type, choose, connect", required = true)
    @Column(name = "false_options", nullable = false)
    private String translation;

    public QuizAnswer(FlashcardResponse flashcard){
        this.word = flashcard.getWord();
        List<String> alias = flashcard.getAlias();
        this.translation = alias.get(new Random().nextInt(alias.size()));
    }

}
