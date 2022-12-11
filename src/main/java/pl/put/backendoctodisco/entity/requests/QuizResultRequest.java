package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class QuizResultRequest {
    @ApiModelProperty(notes = "ID of flashcard list", example = "17", required = true)
    @Column(name = "list_id", nullable = false)
    public Long list_id;

    @ApiModelProperty(notes = "IDs of flashcards answered correctly in quiz", example = "[21, 23, 24]", required = false)
    @Column(name = "correct_id", nullable = false)
    public ArrayList<Long> correct_id;

    @ApiModelProperty(notes = "IDs of flashcards answered incorrectly in quiz", example = "[22, 25]", required = false)
    @Column(name = "incorrect_id", nullable = false)
    public ArrayList<Long> incorrect_id;
}
