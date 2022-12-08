package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

public class QuizResultRequest {
    @ApiModelProperty(notes = "ID of flashcards answered correctly in quiz", example = "Monday exam - phrases", required = true)
    @Column(name = "name", nullable = false)
    public ArrayList<Long> correct_id;

    @ApiModelProperty(notes = "ID of flashcards answered incorrectly in quiz", example = "Monday exam - phrases", required = true)
    @Column(name = "name", nullable = false)
    public ArrayList<Long> incorrect_id;
}
