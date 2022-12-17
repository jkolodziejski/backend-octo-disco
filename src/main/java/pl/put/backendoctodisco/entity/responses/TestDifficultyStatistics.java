package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;

@ToString
@Getter
public class TestDifficultyStatistics {

    @ApiModelProperty(notes = "Difficulty of the test", example="1",  required = false)
    @Column(name = "difficulty", nullable = true)
    private final int difficulty;

    @ApiModelProperty(notes = "Number of flashcard learned", example="3",  required = true)
    @Column(name = "learned", nullable = false)
    private final int learned;

    @ApiModelProperty(notes = "Number of flashcard not learned", example="4", required = true)
    @Column(name = "learned", nullable = false)
    private final int not_learned;

    @ApiModelProperty(notes = "Number of flashcard not attempted to learn in quiz", example="6", required = true)
    @Column(name = "learned", nullable = false)
    private final int not_attempted;

    public TestDifficultyStatistics(int difficulty, int learned, int not_learned, int not_attempted){
        this.difficulty = difficulty;
        this.learned = learned;
        this.not_learned = not_learned;
        this.not_attempted = not_attempted;
    }
}
