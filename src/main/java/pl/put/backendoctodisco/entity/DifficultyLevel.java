package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.put.backendoctodisco.utils.QuestionId;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class DifficultyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Statistic ID")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Language of the tests and quizzes", allowableValues = "en", example = "en", required = true)
    @Column(name = "language", nullable = false)
    private String language;

    @ApiModelProperty(notes = "Name of the level", example = "Basics")
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(notes = "Number that indicates the order of tests and quizzes", example = "1")
    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @ApiModelProperty(notes = "Exp needed to unlock level", required = true)
    @Column(name = "exp_needed", nullable = false)
    private Integer exp_needed;
}
