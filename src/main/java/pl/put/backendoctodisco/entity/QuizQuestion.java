package pl.put.backendoctodisco.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.put.backendoctodisco.entity.requests.RegisterRequest;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public abstract class QuizQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Question ID", example = "1")
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Type of the question", example = "type", allowableValues = "type, choose, connect", required = true)
    @Column(name = "question_type", nullable = false)
    private String question_type;

}
