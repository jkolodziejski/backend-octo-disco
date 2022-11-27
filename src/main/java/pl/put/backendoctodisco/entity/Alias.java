package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class Alias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Flashcard alias ID", example = "1", required = false, hidden = true)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Flashcard alias", example = "Hello")
    @Column(name = "alias")
    private String alias;

    //TODO change to flashcard_id
    @ApiModelProperty(notes = "Flashcard id", example = "1")
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private Long wordId;


    public Alias(String alias, Long wordId) {
        this.alias = alias;
        this.wordId = wordId;
    }
}

