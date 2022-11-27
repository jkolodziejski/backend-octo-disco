package pl.put.backendoctodisco.utils;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.QuizQuestion;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class QuizTypeQuestion extends QuizQuestion {
    @Column(name = "flashcard_id", nullable = false)
    private Long flashcard_id;

    public QuizTypeQuestion(Long id){
        super.setQuestion_type(QuizQuestionType.TYPE.name());
        this.flashcard_id = id;
    }

}
