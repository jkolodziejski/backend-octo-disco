package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.FlashcardListContent;
import pl.put.backendoctodisco.entity.FlashcardStatistics;
import pl.put.backendoctodisco.entity.TestStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;
import pl.put.backendoctodisco.entity.requests.TestResultRequest;
import pl.put.backendoctodisco.entity.responses.CardListStatistics;
import pl.put.backendoctodisco.entity.responses.TestDifficultyStatistics;
import pl.put.backendoctodisco.repository.FlashcardListContentRepository;
import pl.put.backendoctodisco.repository.StatsQuizRepository;
import pl.put.backendoctodisco.repository.StatsTestRepository;
import pl.put.backendoctodisco.repository.test_repository.TestChooseRepository;
import pl.put.backendoctodisco.repository.test_repository.TestOrderQuestionRepository;
import pl.put.backendoctodisco.repository.test_repository.TestTypeRepository;
import pl.put.backendoctodisco.utils.QuestionId;
import pl.put.backendoctodisco.utils.test.TestQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class StatisticsService {

    private final StatsQuizRepository quizRepository;
    private final StatsTestRepository statsTestRepository;
    private final TestTypeRepository testTypeRepository;
    private final TestChooseRepository testChooseRepository;
    private final TestOrderQuestionRepository testOrderRepository;
    private final FlashcardListContentRepository listRepository;

    public StatisticsService(StatsQuizRepository quizRepository, StatsTestRepository statsTestRepository, TestTypeRepository testTypeRepository, TestChooseRepository testChooseRepository, TestOrderQuestionRepository testOrderRepository, FlashcardListContentRepository listRepository) {
        this.quizRepository = quizRepository;
        this.statsTestRepository = statsTestRepository;
        this.testTypeRepository = testTypeRepository;
        this.testChooseRepository = testChooseRepository;
        this.testOrderRepository = testOrderRepository;
        this.listRepository = listRepository;
    }

    public void updateStatistics(QuizResultRequest quizResult, User user){
        if(quizResult.correct_id != null) {
            quizResult.correct_id.forEach( id -> {
                if (quizRepository.updateCard(user.getId(), id, true)<1) {
                    quizRepository.save(new FlashcardStatistics(user, id, true));
                }
            });
        }
        if(quizResult.incorrect_id != null) {
            quizResult.incorrect_id.forEach( id -> {
                if (quizRepository.findByUserIdAndFlashcardId(user.getId(), id).isEmpty()) {
                    quizRepository.save(new FlashcardStatistics(user, id, false));
                }
            });
        }
    }

    public void updateStatistics(TestResultRequest testResult, User user){
        if(testResult.correct_id != null) {
            testResult.correct_id.forEach( qid -> {
                if (statsTestRepository.updateTest(user.getId(), qid.id, qid.type, true)<1) {
                    statsTestRepository.save(new TestStatistics(user, qid, true));
                }
            });
        }
        if(testResult.incorrect_id != null) {
            testResult.incorrect_id.forEach( qid -> {
                if (statsTestRepository.findByUserIdAndQuestionId(user.getId(), qid.id, qid.type).isEmpty()) {
                    statsTestRepository.save(new TestStatistics(user, qid, false));
                }
            });
        }
    }

    public CardListStatistics findFlashcardListStatistics(User user, String listName, Long list_id){
        List<FlashcardListContent> listContent = listRepository.findByListId(list_id);
        List<Long> learned= new ArrayList<>();
        List<Long> notLearned= new ArrayList<>();
        List<Long> notAttempted= new ArrayList<>();
        listContent.forEach(card -> {
            Optional<FlashcardStatistics> stat = quizRepository.findByUserIdAndFlashcardId(user.getId(), card.getFlashcardId());
            if(stat.isEmpty()){
                notAttempted.add(card.getFlashcardId());
            }else{
                if(stat.get().getLearned()){
                    learned.add(card.getFlashcardId());
                }else{
                    notLearned.add(card.getFlashcardId());
                }
            }
        });
        return new CardListStatistics(listName, learned.size(), notLearned.size(), notAttempted.size());
    }

    public boolean deleteFlashcardListStatistics(User user, Long list_id){
        List<FlashcardListContent> listContent = listRepository.findByListId(list_id);
        List<FlashcardStatistics> stats = listContent.stream()
                .map(card -> quizRepository.findByUserIdAndFlashcardId(user.getId(), card.getFlashcardId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        boolean allDeleted = true;
        for (FlashcardStatistics stat : stats) {
            if (quizRepository.deleteById(stat.getId()) == 0) {
                allDeleted = false;
            }
        }
        return allDeleted;
    }

    public TestDifficultyStatistics findTestStatistics(User user, Integer difficulty){
        List<QuestionId> questions = new ArrayList<>();
        testTypeRepository.findByDifficulty(difficulty).forEach(question -> questions.add(new QuestionId(question.getId(), "type")));
        testChooseRepository.findByDifficulty(difficulty).forEach(question -> questions.add(new QuestionId(question.getId(), "choose")));
        testOrderRepository.findByDifficulty(difficulty).forEach(question -> questions.add(new QuestionId(question.getId(), "order")));

        List<Long> learned= new ArrayList<>();
        List<Long> notLearned= new ArrayList<>();
        List<Long> notAttempted= new ArrayList<>();
        questions.forEach(questionId -> {
            Optional<TestStatistics> stat = statsTestRepository.findByUserIdAndQuestionId(user.getId(), questionId.id, questionId.type);
            if(stat.isEmpty()){
                notAttempted.add(questionId.id);
            }else{
                if(stat.get().getLearned()){
                    learned.add(questionId.id);
                }else{
                    notLearned.add(questionId.id);
                }
            }
        });
        return new TestDifficultyStatistics(difficulty, learned.size(), notLearned.size(), notAttempted.size());
    }
}
