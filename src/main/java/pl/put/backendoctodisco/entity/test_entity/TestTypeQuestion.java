package pl.put.backendoctodisco.entity.test_entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.Hibernate;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;

import javax.persistence.*;
import java.util.Objects;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class TestTypeQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "difficulty", nullable = false)
    private Integer difficulty;

    @Column(name = "sentence", nullable = false)
    private String sentence;

    @Column(name = "sentence_word", nullable = false)
    private String sentence_word;

    @Column(name = "translation", nullable = false)
    private String translation;

    @Column(name = "translation_word", nullable = false)
    private String translation_word;

}
