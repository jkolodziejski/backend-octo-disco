package pl.put.backendoctodisco.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.put.backendoctodisco.entity.requests.RegisterRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;

import javax.persistence.*;

@ToString
@Getter
public abstract class QuizQuestion {
    @ApiModelProperty(notes = "Type of the question", allowableValues = "type, choose, connect", required = true)
    @Column(name = "question_type", nullable = false)
    public String question_type;

    protected QuizQuestion(String questionType){
        question_type = questionType;
    }
}
