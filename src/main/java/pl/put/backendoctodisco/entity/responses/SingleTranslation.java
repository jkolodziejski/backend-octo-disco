package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.utils.CompositeKey;

import javax.persistence.*;

@Entity
@IdClass(CompositeKey.class)
@Data
@NoArgsConstructor
public class SingleTranslation {

    @ApiModelProperty(notes = "Number of users who used this translation", example = "5", required = true)
    @Column(name = "popularity", nullable = false)
    private Integer popularity;

    @Id
    @ApiModelProperty(notes = "Word in unknown language", example = "Poland", required = true)
    @Column(name = "word", nullable = false)
    private String word;

    @Id
    @ApiModelProperty(notes = "Translation of the word", example = "Polska", required = true)
    @Column(name = "translation", nullable = false)
    private String translation;


    public SingleTranslation(Integer popularity, String word, String translation){
        this.popularity = popularity;
        this.word = word;
        this.translation = translation;
    }
}
