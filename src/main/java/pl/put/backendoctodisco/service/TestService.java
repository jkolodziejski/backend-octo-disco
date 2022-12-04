package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestOrderAnswer;
import pl.put.backendoctodisco.entity.test_entity.TestOrderQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;
import pl.put.backendoctodisco.repository.test_repository.TestChooseRepository;
import pl.put.backendoctodisco.repository.test_repository.TestOrderAnswerRepository;
import pl.put.backendoctodisco.repository.test_repository.TestOrderQuestionRepository;
import pl.put.backendoctodisco.repository.test_repository.TestTypeRepository;
import pl.put.backendoctodisco.utils.test.TestChooseQuestionResponse;
import pl.put.backendoctodisco.utils.test.TestOrderQuestionResponse;
import pl.put.backendoctodisco.utils.test.TestTypeQuestionResponse;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class TestService {

    private final TestTypeRepository testTypeRepository;
    private final TestChooseRepository testChooseRepository;
    private final TestOrderQuestionRepository testOrderRepository;
    private final TestOrderAnswerRepository testOrderAnswerRepository;

    public TestService(TestTypeRepository testTypeRepository, TestChooseRepository testChooseRepository, TestOrderQuestionRepository testOrderRepository, TestOrderAnswerRepository testOrderAnswerRepository) {
        this.testTypeRepository = testTypeRepository;
        this.testChooseRepository = testChooseRepository;
        this.testOrderRepository = testOrderRepository;
        this.testOrderAnswerRepository = testOrderAnswerRepository;
    }

    public List<TestTypeQuestionResponse> getTypeQuestions(Integer difficulty){
        return testTypeRepository.findByDifficulty(difficulty).stream().map(TestTypeQuestionResponse::new).toList();
    }

    public List<TestChooseQuestionResponse> getChooseQuestions(Integer difficulty){
        return testChooseRepository.findByDifficulty(difficulty).stream().map(TestChooseQuestionResponse::new).toList();
    }

    public List<TestOrderQuestionResponse> getOrderQuestions(Integer difficulty){
        List<TestOrderQuestion> questions = testOrderRepository.findByDifficulty(difficulty);
        return questions.stream().map(question -> new TestOrderQuestionResponse(question, getOrderAnswers(question.getId()))).toList();
    }

    public List<TestOrderAnswer> getOrderAnswers(Long question_id){
        return testOrderAnswerRepository.findByQuestionId(question_id);
    }
}
