package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;
import pl.put.backendoctodisco.utils.QuestionId;

import javax.persistence.Column;
import java.util.ArrayList;

public class TestResultRequest {
    @ApiModelProperty(notes = "Difficulty ID of the tests level", example="1",  required = false)
    @Column(name = "difficulty_id", nullable = true)
    public Long difficulty_id;

    @ApiModelProperty(notes = "IDs of flashcards answered correctly in quiz")
    @Column(name = "correct_id", nullable = false)
    public ArrayList<QuestionId> correct_id;

    @ApiModelProperty(notes = "IDs of flashcards answered incorrectly in quiz")
    @Column(name = "incorrect_id", nullable = false)
    public ArrayList<QuestionId> incorrect_id;
}
