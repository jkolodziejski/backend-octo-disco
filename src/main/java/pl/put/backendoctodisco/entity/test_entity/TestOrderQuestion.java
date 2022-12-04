package pl.put.backendoctodisco.entity.test_entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class TestOrderQuestion {
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
}
