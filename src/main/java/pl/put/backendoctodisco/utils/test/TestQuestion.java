package pl.put.backendoctodisco.utils.test;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;

@ToString
@Getter
public abstract class TestQuestion {
    @ApiModelProperty(notes = "Type of the question", allowableValues = "type, choose, order", required = true)
    @Column(name = "question_type", nullable = false)
    public String question_type;

    protected TestQuestion(String questionType){
        question_type = questionType;
    }
}
