package pl.put.backendoctodisco.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;

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
    @ApiModelProperty(notes = "Flashcard ID", example = "1", required = false, hidden = true)
    @Column(name = "id", nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Language of the word", allowableValues = "pl, en", example = "pl", required = true)
    @Column(name = "language", nullable = false)
    private String language;

    @ApiModelProperty(notes = "Word to learn", example = "Poland", required = true)
    @Column(name = "word", nullable = false)
    private String word;

    @ApiModelProperty(notes = "Translation of the word to polish", example = "Polska", required = true)
    @Column(name = "translation", nullable = false)
    private String translation;

    @ApiModelProperty(notes = "Indicates if flashcard is globally available for users", example = "0", required = true)
    @Column(name = "is_global", nullable = false)
    private Boolean isGlobal;

    @ApiModelProperty(notes = "ID of the user who created the flashcard", example = "1", required = true)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Flashcard(User user, FlashcardRequest request){
        language = request.getLanguage();
        word = request.getWord();
        translation = request.getTranslation();
        isGlobal = false;
        userId = user.getId();
    }

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
