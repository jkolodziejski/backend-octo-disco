package pl.put.backendoctodisco.utils;

import io.swagger.annotations.ApiModelProperty;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QuestionId {
    @ApiModelProperty(notes = "Id of the question", example = "1", required = true)
    @Column(name = "id", nullable = false)
    public Long id;

    @ApiModelProperty(notes = "Type of the question", example = "choose", required = true)
    @Column(name = "type", nullable = false)
    public String type;

    public QuestionId(Long id, String type){
        this.id = id;
        this.type = type;
    }
}
