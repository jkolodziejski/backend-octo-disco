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

    @ApiModelProperty(notes = "ID of the flashcard used in question", required = true)
    @Column(name = "flashcard_id", nullable = false)
    private Long flashcard_id;

    @ApiModelProperty(notes = "Word to be translated", required = true)
    @Column(name = "word", nullable = false)
    private String word;

    @ApiModelProperty(notes = "Correct translation", example = "type", allowableValues = "type, choose, connect", required = true)
    @Column(name = "translation", nullable = false)
    private String translation;

    public QuizAnswer(FlashcardResponse flashcard){
        this.flashcard_id = flashcard.getId();
        this.word = flashcard.getWord();
        List<String> alias = flashcard.getAlias();
        this.translation = alias.get(new Random().nextInt(alias.size()));
    }

}
