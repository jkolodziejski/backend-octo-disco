package pl.put.backendoctodisco.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.requests.RegisterRequest;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class DifficultyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Difficulty level ID", example = "1")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Language of the test level", allowableValues = "en", example = "en", required = true)
    @Column(name = "language", nullable = false)
    private String language;

    @ApiModelProperty(notes = "Name of the test level", example = "Basics", required = true)
    @Column(name = "name", nullable = false)
    private String name;

    @ApiModelProperty(notes = "Difficulty (level) of the test", example = "1", required = true)
    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @ApiModelProperty(notes = "Exp needed to start tests from this level", example = "1250", required = true)
    @Column(name = "exp_needed", nullable = false)
    private Integer exp_needed;
}
