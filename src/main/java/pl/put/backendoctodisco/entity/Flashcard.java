package pl.put.backendoctodisco.entity;


import lombok.Data;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "word", nullable = false)
    private String word;

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
