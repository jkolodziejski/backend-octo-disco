package pl.put.backendoctodisco.entity;

<<<<<<< HEAD
import lombok.*;
=======

import lombok.Data;
>>>>>>> feature/#48-creating-flashcards
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

<<<<<<< HEAD

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
=======
@Entity
@Data
>>>>>>> feature/#48-creating-flashcards
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

<<<<<<< HEAD
    @Column(name="word")
    private String word;

    @Column(name = "language")
    private String language;

    @Column(name = "translation")
=======
    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "translation", nullable = false)
>>>>>>> feature/#48-creating-flashcards
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
