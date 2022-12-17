package pl.put.backendoctodisco.utils.test;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;
import pl.put.backendoctodisco.utils.TestQuestionType;

import javax.persistence.Column;

@ToString
@Getter
public class TestTypeQuestionResponse extends TestQuestion {
    @ApiModelProperty(notes = "Full sentence in known language with a blank", required = true)
    @Column(name = "sentence", nullable = false)
    private String sentence;

    @ApiModelProperty(notes = "Sentence within full sentence to be translated", required = true)
    @Column(name = "sentenceWord", nullable = false)
    private String sentenceWord;

    @ApiModelProperty(notes = "Full sentence translated with a blank, excluding part to be translated", required = true)
    @Column(name = "translation", nullable = false)
    private String translation;

    @ApiModelProperty(notes = "Answer for the blank", required = true)
    @Column(name = "translationWord", nullable = false)
    private String translationWord;

    public TestTypeQuestionResponse(TestTypeQuestion question){
        super(question.getId(), TestQuestionType.TYPE.name().toLowerCase());
        this.sentence = question.getSentence();
        this.sentenceWord = question.getSentence_word();
        this.translation = question.getTranslation();
        this.translationWord = question.getTranslation_word();
    }
}
