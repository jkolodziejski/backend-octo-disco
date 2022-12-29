package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.utils.test.TestQuestion;

import javax.persistence.Column;
import java.util.List;

@ToString
@Getter
public class TestLevel {
    @ApiModelProperty(notes = "Difficulty level ID", example = "1")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Name of the test level", example = "Basics", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(notes = "Difficulty (level) of the test", example = "1", required = true)
    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @ApiModelProperty(notes = "Exp needed to start tests from this level", example = "1250", required = true)
    @Column(name = "exp_needed", nullable = false)
    private Integer exp_needed;

    public TestLevel(DifficultyLevel level){
        this.id = level.getId();
        this.name = level.getName();
        this.difficulty = level.getDifficulty();
        this.exp_needed = level.getExp_needed();
    }
}
