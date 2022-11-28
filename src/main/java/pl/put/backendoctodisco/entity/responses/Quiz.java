package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.QuizQuestion;

import javax.persistence.Column;
import java.util.Arrays;
import java.util.List;

@ToString
@Getter
public class Quiz {
    @ApiModelProperty(notes = "List of questions", example="not available", required = true)
    @Column(name = "questions", nullable = false)
    private List<QuizQuestion> questions;

    public Quiz(List<QuizQuestion> questions){
        this.questions = questions;
    }
}
