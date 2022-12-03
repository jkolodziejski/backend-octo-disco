package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.utils.test.TestQuestion;

import javax.persistence.Column;
import java.util.List;

@ToString
@Getter
public class TestResponse {
    @ApiModelProperty(notes = "List of questions", example="not available", required = true)
    @Column(name = "questions", nullable = false)
    private List<TestQuestion> questions;

    public TestResponse(List<TestQuestion> questions){
        this.questions = questions;
    }
}
