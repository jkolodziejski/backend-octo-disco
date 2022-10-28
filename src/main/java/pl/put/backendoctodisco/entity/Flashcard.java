package pl.put.backendoctodisco.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "word", nullable = false)
    private String word;

    @Column(name = "translation", nullable = false)
    private String translation;
}
