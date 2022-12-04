package pl.put.backendoctodisco.utils.test;


import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;
import pl.put.backendoctodisco.utils.TestQuestionType;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
public class TestChooseQuestionResponse extends TestQuestion {
    @Column(name = "sentence", nullable = false)
    private String sentence;

    @Column(name = "correctAnswer", nullable = false)
    private String correctAnswer;

    @Column(name = "wrongAnswers", nullable = false)
    private List<String> wrongAnswers;

    public TestChooseQuestionResponse(TestChooseQuestion question){
        super(TestQuestionType.CHOOSE.name());
        this.sentence = question.getSentence();
        this.correctAnswer = question.getAnswer();
        wrongAnswers = new ArrayList<>();
        wrongAnswers.add(question.getFalse_option_1());
        if(question.getFalse_option_2() != null) wrongAnswers.add(question.getFalse_option_2());
        if(question.getFalse_option_3() != null) wrongAnswers.add(question.getFalse_option_3());
        if(question.getFalse_option_4() != null) wrongAnswers.add(question.getFalse_option_4());
    }
}
