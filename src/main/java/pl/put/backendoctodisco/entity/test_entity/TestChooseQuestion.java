package pl.put.backendoctodisco.entity.test_entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@RequiredArgsConstructor
@Data
public class TestChooseQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "difficulty_id", nullable = false)
    private Long difficulty_id;

    @Column(name = "sentence", nullable = false)
    private String sentence;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "false_option_1", nullable = false)
    private String false_option_1;

    @Column(name = "false_option_2")
    private String false_option_2;

    @Column(name = "false_option_3")
    private String false_option_3;

    @Column(name = "false_option_4")
    private String false_option_4;
}
