package pl.put.backendoctodisco.utils.test;


import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.test_entity.TestOrderAnswer;
import pl.put.backendoctodisco.entity.test_entity.TestOrderQuestion;
import pl.put.backendoctodisco.utils.TestQuestionType;

import javax.persistence.Column;
import java.util.Comparator;
import java.util.List;

@ToString
@Getter
public class TestOrderQuestionResponse extends TestQuestion {
    @Column(name = "sentence", nullable = false)
    private String sentence;

    @Column(name = "answers", nullable = false)
    private List<String> answers;

    public TestOrderQuestionResponse(TestOrderQuestion question, List<TestOrderAnswer> answers){
        super(TestQuestionType.ORDER.name());
        this.sentence = question.getSentence();

        answers.sort(Comparator.comparing(TestOrderAnswer::getOrder_nr));
        this.answers = answers.stream().map(TestOrderAnswer::getSentence).toList();
    }
}
