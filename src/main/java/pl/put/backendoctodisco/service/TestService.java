package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.entity.TestStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.responses.Test;
import pl.put.backendoctodisco.entity.test_entity.TestOrderAnswer;
import pl.put.backendoctodisco.repository.DifficultyLevelRepository;
import pl.put.backendoctodisco.repository.StatsTestRepository;
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

    private final DifficultyLevelRepository levelRepository;
    private final TestTypeRepository testTypeRepository;
    private final TestChooseRepository testChooseRepository;
    private final TestOrderQuestionRepository testOrderRepository;
    private final TestOrderAnswerRepository testOrderAnswerRepository;
    private final StatsTestRepository statsRepository;

    public TestService(DifficultyLevelRepository levelRepository, TestTypeRepository testTypeRepository, TestChooseRepository testChooseRepository, TestOrderQuestionRepository testOrderRepository, TestOrderAnswerRepository testOrderAnswerRepository, StatsTestRepository statsRepository) {
        this.levelRepository = levelRepository;
        this.testTypeRepository = testTypeRepository;
        this.testChooseRepository = testChooseRepository;
        this.testOrderRepository = testOrderRepository;
        this.testOrderAnswerRepository = testOrderAnswerRepository;
        this.statsRepository = statsRepository;
    }

    public ArrayList<DifficultyLevel> getLevels(String language){
        return new ArrayList<>(levelRepository.findByLanguage(language));
    }

    public Optional<DifficultyLevel> getLevel(Long id){
        return levelRepository.findById(id);
    }

//    public Test createTest(Integer difficulty, Integer size){
//        List<TestQuestion> questions = new ArrayList<>();
//        Integer typeSize = size/3;
//        questions.addAll(getTypeQuestions(difficulty, typeSize));
//        questions.addAll(getChooseQuestions(difficulty, typeSize));
//        questions.addAll(getOrderQuestions(difficulty, size - 2*typeSize));
//
//        Collections.shuffle(questions);
//
//        return new Test(questions);
//    }

    public Test createTest(User user, Long difficultyId, Integer size){
        List<TestQuestion> questions = new ArrayList<>();
        Integer typeSize = size/3;
        questions.addAll(findQuestionsForUser(user, getTypeQuestions(difficultyId), typeSize));
        questions.addAll(findQuestionsForUser(user, getChooseQuestions(difficultyId), typeSize));
        questions.addAll(findQuestionsForUser(user, getOrderQuestions(difficultyId), size - 2*typeSize));

        Collections.shuffle(questions);

        return new Test(questions);
    }

    public List<TestQuestion> getTypeQuestions(Long difficulty){
        List<TestQuestion> questions = new java.util.ArrayList<>(testTypeRepository.findByDifficultyId(difficulty).stream().map(TestTypeQuestionResponse::new).toList());
        return shuffled(questions);
    }

    public List<TestQuestion> getChooseQuestions(Long difficulty){
        List<TestQuestion> questions = new java.util.ArrayList<>(testChooseRepository.findByDifficultyId(difficulty).stream().map(TestChooseQuestionResponse::new).toList());
        return shuffled(questions);
    }

    public List<TestQuestion> getOrderQuestions(Long difficulty){
        List<TestQuestion> questions = new java.util.ArrayList<>(testOrderRepository.findByDifficultyId(difficulty)
                .stream().map(question -> new TestOrderQuestionResponse(question, getOrderAnswers(question.getId()))).toList());
        return shuffled(questions);
    }

    private List<TestQuestion> findQuestionsForUser(User user, List<TestQuestion> questions, int size){
        List<TestQuestion> found = new ArrayList<>();
        found.addAll(filterQuestions(user, questions, false));
        if(found.size() < size){
            found.addAll(filterQuestions(user, questions, true));
        }
        return trimmed(found, size);
    }

    private List<TestQuestion> filterQuestions(User user, List<TestQuestion> questions, Boolean learned){
        List<TestQuestion> filtered = questions.stream().filter(question -> {
            Optional<TestStatistics> stat = statsRepository.findByUserIdAndQuestionId(user.getId(), question.question_id, question.question_type);
            if(stat.isEmpty()) return !learned;
            if(!stat.get().getLearned()) return !learned;
            return learned;
        }).toList();
        return filtered;
    }
    private List<TestQuestion> shuffled(List<TestQuestion> questions){
        Collections.shuffle(questions);
        return questions;
    }

    private List<TestQuestion> trimmed(List<TestQuestion> questions, int size){
        return questions.subList(0, Math.min(size, questions.size()));
    }

    public List<TestOrderAnswer> getOrderAnswers(Long question_id){
        return testOrderAnswerRepository.findByQuestionId(question_id);
    }
}
