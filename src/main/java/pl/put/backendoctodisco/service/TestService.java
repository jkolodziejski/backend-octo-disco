package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.responses.Test;
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
import pl.put.backendoctodisco.utils.test.TestQuestion;
import pl.put.backendoctodisco.utils.test.TestTypeQuestionResponse;

import java.util.*;


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

    public Test createTest(Integer difficulty, Integer size){
        List<TestQuestion> questions = new ArrayList<>();
        Integer typeSize = size/3;
        questions.addAll(getTypeQuestions(difficulty, typeSize));
        questions.addAll(getChooseQuestions(difficulty, typeSize));
        questions.addAll(getOrderQuestions(difficulty, size - 2*typeSize));

        Collections.shuffle(questions);

        return new Test(questions);
    }

    public List<TestQuestion> getTypeQuestions(Integer difficulty, Integer size){
        List<TestQuestion> questions = new java.util.ArrayList<>(testTypeRepository.findByDifficulty(difficulty).stream().map(TestTypeQuestionResponse::new).toList());
        return shuffled(questions, size);
    }

    public List<TestQuestion> getChooseQuestions(Integer difficulty, Integer size){
        List<TestQuestion> questions = new java.util.ArrayList<>(testChooseRepository.findByDifficulty(difficulty).stream().map(TestChooseQuestionResponse::new).toList());
        return shuffled(questions, size);
    }

    public List<TestQuestion> getOrderQuestions(Integer difficulty, Integer size){
        List<TestQuestion> questions = new java.util.ArrayList<>(testOrderRepository.findByDifficulty(difficulty)
                .stream().map(question -> new TestOrderQuestionResponse(question, getOrderAnswers(question.getId()))).toList());
        return shuffled(questions, size);
    }

    private List<TestQuestion> shuffled(List<TestQuestion> questions, Integer size){
        Collections.shuffle(questions);
        return questions.subList(0, Math.min(size, questions.size()));
    }

    public List<TestOrderAnswer> getOrderAnswers(Long question_id){
        return testOrderAnswerRepository.findByQuestionId(question_id);
    }
}