package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Data
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Flashcard ID", example = "1", required = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Language of the word used (possible options: pl, en)", example = "pl", required = true)
    @Column(name = "language", nullable = false)
    private String language;

    @ApiModelProperty(notes = "Word to learn", example = "Poland", required = true)
    @Column(name = "word", nullable = false)
    private String word;

    @ApiModelProperty(notes = "Translation of the word to polish", example = "Polska", required = true)
    @Column(name = "translation", nullable = false)
    private String translation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Flashcard flashcard = (Flashcard) o;
        return id != null && Objects.equals(id, flashcard.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
