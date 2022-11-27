package pl.put.backendoctodisco.utils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.QuizQuestion;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class QuizChooseQuestion extends QuizQuestion {

    @Column(name = "flashcard_lang", nullable = false)
    private String flashcard_lang;

    @Column(name = "flashcard_quest", nullable = false)
    private String flashcard_quest;

    public QuizChooseQuestion(FlashcardResponse flashcard){
        super.setQuestion_type(QuizQuestionType.CHOOSE.name());
        this.flashcard_lang = flashcard.getLanguage();
        this.flashcard_quest = "test";
    }

}
