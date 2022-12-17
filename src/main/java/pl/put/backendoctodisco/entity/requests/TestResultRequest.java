package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;
import pl.put.backendoctodisco.utils.QuestionId;

import javax.persistence.Column;
import java.util.ArrayList;

public class TestResultRequest {
    @ApiModelProperty(notes = "Difficulty of the test", example="1",  required = false)
    @Column(name = "difficulty", nullable = true)
    public Integer difficulty;

    @ApiModelProperty(notes = "IDs of flashcards answered correctly in quiz", example = "[21, 23, 24]", required = false)
    @Column(name = "correct_id", nullable = false)
    public ArrayList<QuestionId> correct_id;

    @ApiModelProperty(notes = "IDs of flashcards answered incorrectly in quiz", example = "[22, 25]", required = false)
    @Column(name = "incorrect_id", nullable = false)
    public ArrayList<QuestionId> incorrect_id;
}
